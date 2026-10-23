# Verteiltes Chat-System mit gRPC

Client-Server-Chat in Java, bei dem Server und Clients ueber gRPC kommunizieren. Die Schnittstellen und Nachrichtenformate sind in eigenen `.proto`-Dateien definiert, die Java-Stubs werden mit `protoc` erzeugt. Der Server bearbeitet mehrere Clients parallel und ist thread-sicher.

Entstanden im Rahmen der Lehrveranstaltung Anwendungssysteme (TU Berlin).

## Aufbau

| Teil | Beschreibung |
|---|---|
| `src/main/proto/chat.proto` | `ChatService` mit `SendMessage` und `GetMessage`, Nachrichtentypen `ChatMessage`, `SendRequest`, `MessageList`, `Ack`, `Empty` |
| `src/main/proto/admin.proto` | `AdminService` mit `DeleteMessage` |
| `ChatServer` | startet beide Services auf Port `50051` |
| `ChatStore` | gemeinsamer Nachrichtenspeicher, alle Zugriffe sind `synchronized` |
| `UserClient` | sendet eine Nachricht und liest alle Nachrichten aus |
| `AdminClient` | loescht eine Nachricht anhand ihrer ID |

Es gibt zwei Arten von Clients (Nutzer und Admin), die auf denselben Server zugreifen.

## Test

`ClientServerTest` startet einen Server und laesst 50 Threads Nachrichten senden, waehrend 50 weitere Threads parallel Loeschanfragen schicken. Am Ende muss genau die erwartete Anzahl an Nachrichten im Speicher stehen. Damit wird der Mehrbenutzerbetrieb abgesichert.

## Ausfuehren

Voraussetzungen: JDK 17 oder neuer, Maven.

```bash
mvn test                                   # baut, erzeugt die gRPC-Stubs, fuehrt den JUnit-Test aus
mvn compile exec:java -Dexec.mainClass=de.mcc.server.ChatServer   # Server starten
```

Danach in einem zweiten Terminal einen Client starten, zum Beispiel `de.mcc.client.UserClient`.

Hinweis fuer Apple Silicon: Das gRPC-Codegen-Plugin ist eine x86_64-Binary, daher wird Rosetta 2 benoetigt.

## Technik

Java 17, Maven, gRPC 1.54, Protocol Buffers (proto3), JUnit 5
