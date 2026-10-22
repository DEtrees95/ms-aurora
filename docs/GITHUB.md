# GitHub und Veröffentlichung

Vorgeschlagener Repository-Name: **ms-aurora**.
Beschreibung: `Minecraft MS Aurora: deutscher Bauplan, WorldEdit-Farblegende und geschützte 270×60-Markierungen für Mitbauer.`

Enthalten sind ausschließlich Dokumentation, CraftScript und Tests. Eine Welt-/Map-Datei ist nicht enthalten. Eine öffentliche Veröffentlichung stellt diese Dateien allen bereit; nur geprüfte Projektdateien hochladen.

## Erster Upload

Das Repository auf GitHub unter dem gewünschten Konto erstellen, anschließend den **Inhalt** dieses Projektordners hochladen. README.md muss im Repository-Hauptverzeichnis liegen. Unterordner `craftscripts`, `docs`, `tests` und `.github` erhalten. Die `.gitignore` ebenfalls übernehmen. Die Veröffentlichung sollte alle Dateien gemeinsam in einem initialen Commit enthalten.

Alternativ mit angemeldetem GitHub CLI im Projektordner:

```powershell
git init -b main
git add .
git commit -m "MS Aurora: Bauplan und geschuetzte WorldEdit-Markierungen"
gh repo create ms-aurora --public --source . --remote origin --push
```

Für ein privates Mitbauer-Repository `--private` statt `--public` verwenden und die gewünschten Mitbauer separat hinzufügen. Die hier gezeigten Befehle veröffentlichen nichts, solange sie nicht ausgeführt werden. Bei bereits vorhandenem Repository zuerst dessen Stand prüfen, nicht blind neu initialisieren oder überschreiben.

Nach dem Upload in GitHub prüfen: README angezeigt, alle sieben Markierungen dokumentiert, Sicherheitsdateien verlinkt und Testlauf unter Actions erfolgreich. Die Tests benötigen keine Zugangsdaten. Integrationstest in einer Weltkopie separat dokumentieren.

## Nicht hochladen

Serverkonfiguration mit Zugangsdaten, Welt-Backups, Spielerinformationen, Mod-JARs und fremde Assets ohne Nutzungsrechte gehören nicht in dieses Repository. Die .gitignore ist eine zusätzliche Hilfe, keine vollständige Datenprüfung.
