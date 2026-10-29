# Technik: Fabric 1.21.1, WorldEdit und Skripte

Recherche: 22.10.2026. Ziel sind Minecraft Java 1.21.1 und ein ausdrücklich dafür veröffentlichtes WorldEdit-Fabric-JAR aus der 7.3-Reihe. Nicht aus einer aktuellen Dokumentationsüberschrift ableiten, dass die neueste WorldEdit-Version auf 1.21.1 läuft. Die tatsächlichen JAR-Versionen in einem Issue protokollieren.

## Ergänzung zum tatsächlichen Projektstand – 30.10.2026

WorldEdit funktioniert laut Nutzer auf dem Fabric-Server. Das belegt keine CraftScript-/Rhino-Funktion. Axiom und Litematica öffnen im Client; serverseitige Präsenz beider Mods ist eine Nutzerangabe, keine Funktionsprüfung. Siehe [Befehlsverifikation](BEFEHLSVERIFIKATION.md) und [Serverstand](SERVER-UND-IMPORT.md).

Die folgenden Installations- und Skripthinweise sind historische technische Anleitungen. Sie wurden bei dieser Dokumentationsaktualisierung nicht am Server ausgeführt. Nicht auf der bestehenden Yacht anwenden; der Koordinatenkonflikt ist offen.

## Welche Automatisierung funktioniert?

| Weg | Einordnung |
|---|---|
| `.mcfunction` als WorldEdit-Skript | **Nein.** Eine Vanilla-Funktion wird über Minecraft-Datenpakete ausgeführt; WorldEdit lädt sie nicht als eigenes Skript. `//pos1`, `//set` usw. nicht einfach in eine Funktion kopieren: dort fehlt der Spieler-/WorldEdit-Sitzungskontext. |
| Vanilla-Datenpaket | Minecraft 1.21.1 kann Vanilla-Befehle automatisieren. Das ist eine Alternative ohne WorldEdit, aber ohne dessen Undo. Dieses Repo liefert bewusst kein zweites ungeschütztes `fill ... air`-Skript. |
| CraftScript `.js` mit `/cs` | WorldEdit dokumentiert JavaScript/Rhino und bietet die API für gespeicherte EditSessions. Das mitgelieferte Skript nutzt diesen Weg. Kein JavaScript für Node oder ein Browserfenster. |
| Chat-Makro / Befehlsblock-Kette | Kein sicherer Ersatz: fehlende Bestandsprüfung, Sitzungs-/Berechtigungsprobleme und mögliche Teiländerungen. |

Die Upstream-Dokumentation beschreibt Rhino als zusätzliche Voraussetzung, `js.jar` und auf Nicht-Bukkit-Plattformen den `mods`-Ordner. Sie nennt `config/WorldEdit` als möglichen WorldEdit-Konfigurationspfad. **Das ist keine Zusage**, dass ein beliebiger Rhino-Download als Fabric-Mod erkannt bzw. von eurer konkreten Loader-/WorldEdit-Kombination geladen wird. Rhino-Varianten, zusätzliche Abhängigkeiten und der Java-Klassenpfad können den Start verhindern. Nur die zum Zielbuild passende Upstream-Anleitung verwenden; keine beliebigen JARs installieren.

## Installation und erster Test

1. Java/Fabric/WorldEdit 1.21.1-konform in einer Testinstanz starten. Den WorldEdit-Build prüfen und notieren.
2. Rhino nach der dazu passenden EngineHub-Anleitung bereitstellen. Server regulär neu starten, Startprotokoll auf Fehler prüfen. Fehlt der Rhino-Motor oder `/cs`, nicht weiter ausführen. CraftScripts sind ohne erreichbaren Motor nicht betriebsbereit.
3. `craftscripts/aurora.js` in den konfigurierten CraftScript-Ordner kopieren. Üblicher Pfad: `config/WorldEdit/craftscripts/aurora.js`; `scripts-dir`/Serverkonfiguration hat Vorrang.
4. Als berechtigter Spieler ausführen. `/cs` benötigt Skriptberechtigungen; Weltänderungsrechte und Grenzwerte bleiben relevant. Keine allgemeine Freigabe aller Skripte für alle Spieler.
5. `/cs aurora preview fresh` auf freiem Bauplatz. Weltkennung prüfen und in `CFG.worldId` übernehmen. Auf dem vorhandenen alten Beton wird `fresh` erwartungsgemäß abbrechen.
6. Nach Backup `/cs aurora apply fresh BACKUP`; alle sieben Farben, Grenzen, Taper und Y-Ebenen visuell prüfen, danach `//undo` ausprobieren. Bei der alten Fläche zunächst das Migrationsverfahren aus SICHERHEIT.md durchführen.

Code basiert auf den 7.3-Schnittstellen `BlockVector3.at`, `World.id`, `getBlock`, `getBlockType().getId()`, `context.getBlock`, `context.remember()` und `EditSession.setBlock`. `argv[0]` enthält den Skriptnamen. Rhino-kompatibler ES5-Stil ohne externe Bibliotheken. Keine Abhängigkeit von einer aktuellen 7.4-only-Funktion.

## Prüfstand

Die automatisierten Tests laufen mit einer simulierten WorldEdit-API unter Node und prüfen Schutzlogik und Geometrie. Sie beweisen keine Rhino-/Fabric-Kompatibilität. Ein echter 1.21.1-Server war für diese Erstellung nicht verfügbar. Bei jedem Lauf sind Zeitlimit, Chunkzugriff, Mods, Masken und Schutzplugins zusätzlich zu prüfen. Eine aktive globale Maske kann Änderungen einschränken: nur in einer leeren Testwelt deren Einfluss gezielt prüfen, nicht Schutzregeln pauschal abschalten.

## Primärquellen

- [EngineHub: Plattformen und Fabric-Installation](https://enginehub.org/worldedit)
- [EngineHub: CraftScripts, Rhino und Ausführung](https://worldedit.enginehub.org/en/latest/usage/other/craftscripts/)
- [WorldEdit 7.3.8: CraftScriptContext](https://github.com/EngineHub/WorldEdit/blob/7.3.8/worldedit-core/src/main/java/com/sk89q/worldedit/scripting/CraftScriptContext.java)
- [WorldEdit 7.3.8: ScriptingCommands](https://github.com/EngineHub/WorldEdit/blob/7.3.8/worldedit-core/src/main/java/com/sk89q/worldedit/command/ScriptingCommands.java)
- [WorldEdit 7.3.8: FabricWorld und Weltkennung](https://github.com/EngineHub/WorldEdit/blob/7.3.8/worldedit-fabric/src/main/java/com/sk89q/worldedit/fabric/FabricWorld.java)

Die getaggten Quelltexte dienen der API-Prüfung; 7.3.8 ist hier kein behaupteter getesteter Installationsstand. Im Testprotokoll den wirklich verwendeten 1.21.1-Build festhalten.
