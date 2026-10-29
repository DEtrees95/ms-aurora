# Befehlsverifikation

Stand: 30.10.2026. Hier werden Chatnachweise und offene Tests getrennt. Bei dieser Aktualisierung wurden keine Minecraft-Befehle ausgeführt. Ein funktionierendes Werkzeug bestätigt nicht automatisch jeden seiner Befehle.

| Werkzeug / Befehl | Nachweis aus dem Chat | Einordnung |
|---|---|---|
| WorldEdit | Nutzer meldet funktionierende Nutzung | Werkzeug bestätigt; einzelne Befehle und genauer Build nicht vollständig protokolliert |
| LuckPerms | Nutzer meldet funktionierende Nutzung | Keine konkrete Befehlsfolge überliefert |
| WorldGuard | Nach Absturz deaktiviert | Nicht als funktionierend führen; Ursache offen |
| WATHE Ready Area | Als eingerichtet gemeldet | Wortlaut und Konfigurationswerte fehlen |
| Scenery-/Spectator-Teleporte | Als eingerichtet gemeldet | Keine konkreten Befehle/Koordinaten aus dieser Zusammenfassung ableiten |
| Axiom öffnen | Im Client erfolgreich laut Nutzer | Auswahl-, Füll- und Undo-Ablauf noch nicht nachgewiesen |
| Litematica öffnen | Im Client erfolgreich laut Nutzer | Kein vollständiger Lade-/Platzierungsnachweis |
| Axiom / Litematica auf dem Server | Vom Nutzer als vorhanden gemeldet | Keine unabhängige Datei- oder Funktionsprüfung |
| MaLiLib serverseitig | Laut Nutzer nicht vorhanden | Keine Aussage über Client-Abhängigkeiten |
| `/effect give @s minecraft:night_vision 600 0 true` | Im referenzierten Chat vorgeschlagen; spätere Antwort beschreibt funktionierende Nachtsicht | Indirekter Chatnachweis; kein neuer Test oder Konsolenprotokoll |
| `/tp -40 48 42` | Im referenzierten Chat als Ansicht vorgeschlagen | Exakte Ausführung und sichere Zielposition nicht separat bestätigt; nicht blind wiederholen |
| `/cs aurora preview fresh` / `preview migrate` | Historisch dokumentiert | Kein echter Rhino-/Fabric-Test belegt |
| `/cs aurora apply … BACKUP` | Historisch dokumentiert | Nicht auf der vorhandenen Yacht ausführen; Backup-Wort erstellt keine Sicherung |
| `//schem list`, `//schem load …`, `//paste -n`, `//paste -a` | Historische Importanleitung | Keine Gerüstdatei geliefert, keine Serverausführung belegt |
| `//undo` | Historische Rücksetz-Anleitung | Nicht als am aktuellen Server erfolgreich getestet ausgeben |
| `node tests/safety.cjs` | Testprotokoll vom 22.10. nennt 13 bestandene Prüfungen | Historischer Mock-Test; bei dieser Dokumentationsänderung nicht erneut ausgeführt |

## Künftige Nachweise

Für jeden tatsächlich geprüften Befehl Datum, Welt/Dimension, genaue Version, vollständigen Wortlaut, erwartetes und beobachtetes Ergebnis festhalten. Bei Blockänderungen zusätzlich begrenzten Testbereich, Sicherung, Vorher-/Nachher-Bild und geprüftes Rücksetzen dokumentieren. Keine Zugangsdaten oder Servergeheimnisse veröffentlichen.

Keine fehlenden WATHE-/LuckPerms-Befehle erfinden. Zuerst die tatsächliche Konfiguration beziehungsweise die erfolgreiche Befehlsfolge nachtragen.
