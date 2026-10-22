# GitHub und Veröffentlichung

Bestehendes öffentliches Repository: [DEtrees95/ms-aurora](https://github.com/DEtrees95/ms-aurora).
Beschreibung: `Minecraft MS Aurora: deutscher Bauplan, WorldEdit-Farblegende und geschützte 270×60-Markierungen für Mitbauer.`

Enthalten sind ausschließlich Dokumentation, CraftScript und Tests. Eine Welt-/Map-Datei ist nicht enthalten. Eine öffentliche Veröffentlichung stellt diese Dateien allen bereit; nur geprüfte Projektdateien hochladen.

## Bestehendes Repository pflegen

Vor Änderungen den aktuellen Stand einschließlich Unterordnern lesen. Vorhandene Skripte, Tests und Bauentscheidungen erhalten; Ergänzungen gezielt einarbeiten. Keine neue Repository-Initialisierung und keinen Force-Push verwenden. Änderungen mit dem neuesten Branchstand vergleichen, Sicherheitsprüfungen ausführen und nach Veröffentlichung die Dateien auf GitHub kontrollieren.

Die derzeitigen Tests lassen sich lokal mit `node tests/safety.cjs` ausführen. Ein Actions-Workflow ist aktuell nicht enthalten; deshalb keinen erfolgreichen GitHub-Actions-Lauf voraussetzen.

## Historischer Weg für ein neues Repository

Das Repository auf GitHub unter dem gewünschten Konto erstellen, anschließend den **Inhalt** dieses Projektordners hochladen. README.md muss im Repository-Hauptverzeichnis liegen. Vorhandene Unterordner `craftscripts`, `docs` und `tests` erhalten. Die `.gitignore` ebenfalls übernehmen. Die Veröffentlichung sollte alle Dateien gemeinsam in einem initialen Commit enthalten.

Alternativ mit angemeldetem GitHub CLI im Projektordner:

```powershell
git init -b main
git add .
git commit -m "MS Aurora: Bauplan und geschuetzte WorldEdit-Markierungen"
gh repo create ms-aurora --public --source . --remote origin --push
```

Für ein privates Mitbauer-Repository `--private` statt `--public` verwenden und die gewünschten Mitbauer separat hinzufügen. Die hier gezeigten Befehle veröffentlichen nichts, solange sie nicht ausgeführt werden. Bei bereits vorhandenem Repository zuerst dessen Stand prüfen, nicht blind neu initialisieren oder überschreiben.

Nach dem Upload in GitHub prüfen: README angezeigt, alle sieben Markierungen dokumentiert, Sicherheitsdateien verlinkt und lokaler Testlauf erfolgreich. Die Tests benötigen keine Zugangsdaten. Integrationstest in einer Weltkopie separat dokumentieren.

## Nicht hochladen

Serverkonfiguration mit Zugangsdaten, Welt-Backups, Spielerinformationen, Mod-JARs und fremde Assets ohne Nutzungsrechte gehören nicht in dieses Repository. Die .gitignore ist eine zusätzliche Hilfe, keine vollständige Datenprüfung.
