# Reproduce the pre-modernization Windows source baseline without editing pom.xml.
[CmdletBinding()]
param()
$ErrorActionPreference = 'Stop'
$repoRoot = Split-Path -Parent $PSScriptRoot
$dependencyCache = Join-Path $env:USERPROFILE '.jftp-build-cache'
$runDirectory = Join-Path $repoRoot ('.local\baseline-runs\' + [guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $dependencyCache, $runDirectory -Force | Out-Null
$dependencies = @(
    @{ File = 'legacy-ftpapi-3.0.0\ftpapi-3.0.0.jar'; Url = 'https://www.jmethods.com/mvn-repo/com/myjavaworld/ftpapi/3.0.0/ftpapi-3.0.0.jar'; Hash = '496782add046e211b5f7a686c483522cca8879048f12286249aa50c8f86b505c' },
    @{ File = 'javahelp-2.0.05.jar'; Url = 'https://repo.maven.apache.org/maven2/javax/help/javahelp/2.0.05/javahelp-2.0.05.jar'; Hash = 'fcf4922d38ff85184f1d2328317bb60826e14da948abd606ee3d5b8c6a70debd' },
    @{ File = 'junit-3.8.1.jar'; Url = 'https://repo.maven.apache.org/maven2/junit/junit/3.8.1/junit-3.8.1.jar'; Hash = 'b58e459509e190bed737f3592bc1950485322846cf10e78ded1d065153012d70' }
)
$jars = foreach ($dependency in $dependencies) {
    $file = Join-Path $dependencyCache $dependency.File
    if (-not (Test-Path -LiteralPath $file)) {
        New-Item -ItemType Directory -Path (Split-Path -Parent $file) -Force | Out-Null
        Invoke-WebRequest -Uri $dependency.Url -OutFile $file
    }
    if ((Get-FileHash -LiteralPath $file -Algorithm SHA256).Hash.ToLowerInvariant() -ne $dependency.Hash) {
        throw "Dependency checksum mismatch: $file"
    }
    $file
}
$classes = Join-Path $runDirectory 'classes'
$profile = Join-Path $runDirectory 'profile'
New-Item -ItemType Directory -Path $classes, $profile | Out-Null
$sources = @(Get-ChildItem -LiteralPath (Join-Path $repoRoot 'src\main\java') -Filter '*.java' -Recurse |
    Where-Object Name -ne 'OSXAdapterOld.java')
# The original unused adapter directly imports removed, Apple-only APIs. The active
# OSXAdapter uses reflection. This exclusion establishes a Windows baseline only.
$testSources = @(Get-ChildItem -LiteralPath (Join-Path $repoRoot 'src\test\java') -Filter '*Test.java' -Recurse)
if ($testSources.Count -eq 0) { throw 'No characterization test classes discovered' }
$sourceArguments = Join-Path $runDirectory 'sources.txt'
@($sources.FullName + $testSources.FullName) | ForEach-Object {
    '"' + $_.Replace('\', '/') + '"'
} | Set-Content -LiteralPath $sourceArguments -Encoding utf8NoBOM
$classpath = $jars -join [IO.Path]::PathSeparator
& javac --release 8 -encoding UTF-8 -cp $classpath -d $classes "@$sourceArguments" 2>&1 |
    Tee-Object -FilePath (Join-Path $runDirectory 'compile.log')
if ($LASTEXITCODE -ne 0) { throw 'Baseline compilation failed; see compile.log' }
foreach ($resource in @('resources', 'resources_de', 'resources_zh_TW', 'images', 'help')) {
    Copy-Item -Path (Join-Path $repoRoot "src\main\$resource\*") -Destination $classes -Recurse -Force
}
$classpath = $classes + [IO.Path]::PathSeparator + $classpath
$results = @()
foreach ($source in $testSources) {
    $package = [regex]::Match((Get-Content -LiteralPath $source.FullName -Raw), 'package\s+([\w.]+);').Groups[1].Value
    if (-not $package) { throw "Missing test package: $($source.Name)" }
    $testClass = "$package.$($source.BaseName)"
    $output = & java "-Duser.home=$profile" '-Duser.language=en' '-Duser.country=GB' '-Djava.awt.headless=true' "-Djava.io.tmpdir=$runDirectory" -cp $classpath junit.textui.TestRunner $testClass 2>&1
    $exitCode = $LASTEXITCODE
    $output | Tee-Object -FilePath (Join-Path $runDirectory "$($source.BaseName).log")
    if ($exitCode -ne 0) { throw "Failed: $testClass (logs in $runDirectory)" }
    $count = [regex]::Match(($output -join "`n"), 'OK \((\d+) tests?\)')
    if (-not $count.Success) { throw "No successful JUnit discovery count: $testClass" }
    $results += @{ Class = $testClass; Tests = [int]$count.Groups[1].Value; Skips = 0; ExitCode = $exitCode }
}
@{ Date = (Get-Date).ToUniversalTime().ToString('o'); ProductionSources = $sources.Count;
   Excluded = @('OSXAdapterOld.java'); TestClasses = $results; RunDirectory = $runDirectory } |
    ConvertTo-Json -Depth 5 | Set-Content -LiteralPath (Join-Path $runDirectory 'result.json') -Encoding utf8NoBOM
Write-Output "Baseline checks passed: $($testSources.Count) classes, $(($results | Measure-Object -Property Tests -Sum).Sum) tests, 0 skips. Evidence: $runDirectory"
