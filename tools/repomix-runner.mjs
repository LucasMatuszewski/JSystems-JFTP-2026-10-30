// Thin adapter for the pinned Repomix library; source files are never rewritten.
import fs from 'node:fs/promises';
import path from 'node:path';
import { pathToFileURL } from 'node:url';

const [packageDirectory, repository, configFile, outputFile, metricsFile] = process.argv.slice(2);
const packageInfo = JSON.parse(await fs.readFile(path.join(packageDirectory, 'package.json'), 'utf8'));
if (packageInfo.name !== 'repomix' || packageInfo.version !== '1.18.1') {
  throw new Error('This course adapter requires Repomix 1.18.1.');
}
const { pack, mergeConfigs } = await import(pathToFileURL(path.join(packageDirectory, 'lib/index.js')).href);
const fileConfig = JSON.parse(await fs.readFile(configFile, 'utf8'));
const config = mergeConfigs(repository, fileConfig, { output: { filePath: outputFile } });
const result = await pack([repository], config);
const metrics = {
  version: packageInfo.version,
  nodeVersion: process.versions.node,
  totalFiles: result.totalFiles,
  totalCharacters: result.totalCharacters,
  totalTokens: result.totalTokens,
  fileTokenCounts: result.fileTokenCounts,
  safeFilePaths: result.safeFilePaths,
  skippedFiles: result.skippedFiles,
  suspiciousFilesResults: result.suspiciousFilesResults,
};
await fs.writeFile(metricsFile, `${JSON.stringify(metrics, null, 2)}\n`, 'utf8');
