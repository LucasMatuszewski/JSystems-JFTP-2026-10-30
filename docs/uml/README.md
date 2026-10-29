# JFTP UML views — current application

**Baseline:** application source `14e62ceba4e371c2a0b955604b10f065f46f4f7d`, examined 2026-10-29.

These selective views describe the existing Java desktop application. They are
source-derived, not a proposed redesign or proof of runtime success. Class diagrams
show selected members and relationships, not every declaration. The session state
diagram is an explanatory abstraction: the source uses client/busy/abort fields,
not a formal state-machine implementation. See the [PRD](../PRD.md),
[architecture ADR](../ADR/000-main-architecture.md), [verification gate](../ADR/001-baseline-verification.md)
and [source evidence](../current-state-evidence.md) for behavior, limitations and provenance.

GitHub renders the Mermaid blocks below. Open this file in a Markdown preview with
Mermaid support, or use the SVG links for viewers without Mermaid. The generated
SVGs reproduce these blocks; they do not require JavaScript or an internet connection.
The component/deployment view uses Mermaid flowchart notation because Mermaid has
no dedicated UML deployment grammar.

## 1. Core classes

[Open SVG](rendered/01-core-classes.svg). Evidence: CS-01–03, CS-07, CS-09–11, CS-26;
original declarations in `Favorite`, `LocalFile` and `TransferObject`.

```mermaid
classDiagram
    direction LR
    class JFTPApplication {
        +main(String[] args)$
    }
    class JFTP {
        +JFTPPreferences prefs$
        +newSession()
        +closeSession()
        +exit()
    }
    class FTPSession {
        -FTPClient client
        -RemoteHost remoteHost
        -LocalFile lwd
        -RemoteFile rwd
        -boolean abort
        -boolean autoDetect
        +connect(RemoteHost remoteHost)
        +reconnect()
        +disconnect()
        +download()
        +upload()
        +getTransferType(String ext) int
    }
    class RemoteHost {
        #String hostName
        #int port
        #String user
        #String password
        #boolean passive
        #String[] commands
        #int sslUsage
    }
    class Favorite
    class JFTPPreferences
    class LocalPane
    class RemotePane
    class LocalFile
    class FTPClient {
        <<interface>>
    }
    class ListParser {
        <<interface>>
    }
    class RemoteFile
    JFTPApplication --> JFTP : creates
    JFTP "1" *-- "0..*" FTPSession : tabs
    FTPSession --> JFTP : frame reference
    JFTP --> JFTPPreferences : global preferences
    FTPSession *-- LocalPane
    FTPSession *-- RemotePane
    FTPSession --> RemoteHost : retained connection settings
    RemoteHost <|-- Favorite
    FTPSession --> FTPClient : external protocol boundary
    FTPSession --> ListParser : external listing parser
    FTPSession --> LocalFile : local working directory
    FTPSession --> RemoteFile : remote working directory
    LocalPane --> FTPSession
    RemotePane --> FTPSession
```

`FTPClient`, `ListParser` and `RemoteFile` belong to the external FTPAPI dependency.
Reverse references deliberately show the current UI/session coupling. Reflection
and mutable global preferences are explained in ADR-000; no clean service layer is implied.

## 2. Persistence, TLS and temporary edits

[Open SVG](rendered/02-persistence-and-tls.svg). Evidence: CS-18–21, CS-23, CS-28, CS-30.

```mermaid
classDiagram
    direction LR
    class FavoritesManager {
        +getFavorites() List$
        +saveFavorites(List favorites)$
    }
    class Favorite
    class JFTPPreferences
    class JFTP {
        +loadPreferences() JFTPPreferences$
        +savePreferences(JFTPPreferences prefs)$
    }
    class JFTPSSLContext
    class JFTPTrustManager {
        +checkServerTrusted(X509Certificate[] chain, String authType)
        +checkClientTrusted(X509Certificate[] chain, String authType)
    }
    class KeyStoreManager
    class SecurityWarningDlg
    class TransferObject {
        -int direction
        -LocalFile localFile
        -RemoteFile remoteFile
    }
    class FileChangeMonitor
    class FTPSession
    FavoritesManager --> Favorite : serialized list entries
    JFTP --> JFTPPreferences : serialized object
    JFTPSSLContext --> JFTPTrustManager : installs trust manager
    JFTPTrustManager --> KeyStoreManager : configured server store
    JFTPTrustManager --> SecurityWarningDlg : operator override
    FTPSession --> JFTPSSLContext : encrypted connection
    FTPSession --> FileChangeMonitor : temporary edit file events
    FTPSession --> TransferObject : monitored local-to-remote mapping
```

Favorites and preferences are object streams, not database entities. Favorites'
embedded-key cipher and legacy plaintext compatibility are observed mechanisms,
not a security endorsement. The custom server-trust method does not delegate PKIX
validation. The diagram must be read alongside ADR-000's trust/storage limitations.

## 3. Connection sequence

[Open SVG](rendered/03-connection-sequence.svg). Evidence: CS-05–08, CS-23, CS-27, CS-30.

```mermaid
sequenceDiagram
    actor Operator
    participant Action as ConnectAction
    participant Dialog as ConnectionDlg
    participant Session as FTPSession
    participant Worker as Custom SwingWorker
    participant Client as External FTPClient
    Operator->>Action: Connect
    Action->>Dialog: Populate retained host or preferences
    Operator->>Dialog: Approve or cancel
    alt Cancel or invalid fields
        Dialog-->>Action: No approved host, or retain dialog with error
    else Approved and valid
        Dialog-->>Action: RemoteHost
        Action->>Session: connect(host)
        opt Already connected
            Session-->>Operator: Confirm replacing connection
            Note over Session: Decline returns, accept requests disconnect
        end
        Session->>Worker: Mark busy and start
        Worker->>Client: Reflective construction and configuration
        Worker->>Client: connect then login
        opt Encrypted connection needs trust decision
            Client-->>Operator: Custom certificate warning
            Operator-->>Client: Accept or reject
        end
        alt Connection and subsequent operations succeed
            Worker->>Client: Commands, initial directory and filtered list
            Worker-->>Session: finished on EDT
            Session-->>Operator: Remote data, title and status update
        else Failure
            Worker-->>Session: FTP, connection or generic error path
            Session-->>Operator: Error dialog or status log
            Note over Session: Cleanup varies by failure location
        end
    end
```

The replacement-connection branch is simplified: disconnect is itself asynchronous.
This diagram does not claim the source safely serializes disconnect/reconnect.
Worker completion on the EDT does not make its background UI updates thread safe.

## 4. Transfer activity

[Open SVG](rendered/04-transfer-activity.svg). Evidence: CS-09–14 and D-01/D-02.

```mermaid
flowchart TD
    Start([Upload or download]) --> Valid{Connected and selected entries?}
    Valid -->|No| Return([Return without transfer])
    Valid -->|Yes| Busy[Clear abort flag, mark busy, start worker]
    Busy --> Next{More selected entries?}
    Next -->|No| Refresh[Refresh destination pane]
    Next -->|Yes| Continue{Connected and not aborted?}
    Continue -->|No| Refresh
    Continue -->|Yes| Kind{File or directory?}
    Kind -->|File| Type[Select transfer type]
    Type --> Write[Request non-append transfer]
    Write --> Outcome{Completed?}
    Outcome -->|Yes| Next
    Outcome -->|Error or abort| Error[Report error or stop; partial bytes can remain]
    Error --> Next
    Kind -->|Directory| Special{Dot or dot-dot?}
    Special -->|Yes| Next
    Special -->|No| Traverse[Create destination; enumerate filtered children]
    Traverse --> Recurse[Recursively process children with connection and abort checks]
    Recurse --> Next
    Refresh --> Done([Clear busy on completion])
```

This activity is recursive and sequential within a session; it does not describe a
durable job queue or atomic transfer. Existing destination policy and server
rejections are documented in the PRD. Progress updates and error paths do not prove success.

## 5. Abstract session states

[Open SVG](rendered/05-session-states.svg). Evidence: CS-03, CS-07–10, CS-18 and abort action.

```mermaid
stateDiagram-v2
    [*] --> Disconnected : new tab
    Disconnected --> Connecting : approved host
    Connecting --> Connected : client connected and listing loaded
    Connecting --> ErrorReported : connection or login failure
    ErrorReported --> Disconnected : cleanup when performed
    ErrorReported --> Connected : client can remain connected after later failure
    Connected --> Working : browse, transfer or remote operation
    Working --> Connected : completion and refresh
    Working --> AbortRequested : operator abort
    AbortRequested --> Connected : worker stops and connection survives
    AbortRequested --> ErrorReported : connection fails
    Working --> ErrorReported : operation failure
    Connected --> Disconnecting : disconnect request
    Disconnecting --> Disconnected : worker completion clears remote pane
    Connected --> Connecting : confirmed replacement or reconnect request
    Disconnected --> [*] : close session
    Connected --> [*] : close session
```

These states summarize observable branches. Busy, connection and abort flags are
separate mutable values and can overlap. There is no claim that every depicted
transition is guarded against races or that closing waits for all asynchronous work.

## 6. Deployment and external boundaries

[Open SVG](rendered/06-deployment.svg). Evidence: CS-19–24, CS-29–33.

```mermaid
flowchart LR
    subgraph Machine[Operator workstation]
        Launcher[Batch or shell launcher] --> JVM[Java desktop process]
        Jar[Application JAR and packaged resources] --> JVM
        Lib[FTPAPI and JavaHelp dependency JARs] --> JVM
        JVM --> Profile[User profile: preferences.ser and favorites.ser]
        JVM --> Local[Local files and temporary edits]
        JVM --> Stores[Configured certificate and key stores]
        JVM --> Editor[OS viewer or editor]
    end
    JVM -->|FTP or FTPS| Server[Authorized remote FTP server]
    JVM -->|Optional JVM-wide SOCKS settings| Proxy[SOCKS proxy]
    Proxy --> Server
    JVM -->|HTTP version information| Update[Publisher update endpoint]
```

This is the intended packaging topology implied by source. A full distribution has
not been built/launched yet. The assembly's root JAR location and the launchers'
`lib/` JAR path appear inconsistent; runtime deployment must be verified. No web
backend, database server or LLM service is established by the inspected application.

## Viewing, editing and validation

Edit the Mermaid blocks here and render them before committing. Keep diagram
changes tied to original definitions/callers and the evidence register. Architectural
improvements belong in a new dated ADR, rather than being drawn as existing classes.

The accompanying `validation.json` records the renderer version, diagram IDs,
source hash and SVG hashes for this snapshot. Rendering confirms Mermaid syntax,
not the application's behavior or the semantic completeness of these selective views.

To reproduce using Node.js, Chrome and an isolated ignored dependency directory:

```powershell
npm install --prefix .local/uml-qa --ignore-scripts --no-audit --no-fund mermaid@11.12.1 playwright@1.56.1
node tools/render_uml.cjs --write
node tools/render_uml.cjs --check
```

On other machines set `JFTP_CHROME` to the installed Chrome/Chromium executable.
The browser renders only the local diagram source and local Mermaid bundle.
`--check` validates each block in the browser and checks saved source/SVG hashes;
`--write` replaces generated SVGs, the first-diagram preview and validation receipt.
