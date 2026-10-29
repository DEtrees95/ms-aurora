# MS Aurora – Bauplanung für Minecraft

Deutsches Mitbauer-Repository für Minecraft Java **1.21.1 / Fabric**. Der genaue installierte WorldEdit-Build ist noch zu erfassen.

**Wir bauen gemeinsam auf dem vom Projektinhaber eingerichteten MS-Aurora-Server. Mitbauer sollen dort ausprobieren und mitbauen. Eine eigene Welt ist dafür nicht erforderlich.** [Server, Bauhilfen und Importbefehle](docs/SERVER-UND-IMPORT.md).
Enthalten sind Bauplan, Farblegende und ein vorsichtiges CraftScript. **Keine fertige Yacht und kein Welt-Download.** Die Yacht wird in **Aurora-Meer** mit dem Zielmaß **270 × 60 Blöcke** gebaut. Der vorhandene Maschinenraum und Unterwasserrumpf sind Bestand; das CraftScript ist ein älterer Markierungsplan und keine Anleitung zum erneuten Überbauen der Yacht.

## Aktueller Stand – 30.10.2026

- netcup Windows Server 2022; The Harpy Express: Extended **4.0.0-beta.1-h1.4**.
- Maschinenraum: **X −100…−55, Z −14…15**, Boden Y55; zwei Motorgehäuse, drei Aggregate, trocken, dreiblöckige Quarztreppe und Deckenbeleuchtung.
- Unterwasserrumpf: mehrlagige weiße Stufen, hervorstehender Maschinenraum und schwarze Kielhilfe. Ob die Unterseite vollständig geschlossen ist, bleibt offen.
- WorldEdit und LuckPerms funktionieren laut Nutzer; WorldGuard ist wegen eines Absturzes deaktiviert.
- WATHE Ready Area sowie Scenery-/Spectator-Teleporte eingerichtet.
- Axiom und Litematica lassen sich im Client öffnen. Beide werden vom Nutzer auch als serverseitig vorhanden gemeldet; serverseitige Funktion nicht unabhängig geprüft. MaLiLib laut Nutzer nicht serverseitig vorhanden.

[Baudokumentation und nächster kleiner Schritt](docs/BAUSTAND.md) · [Status und offene Prüfungen](docs/STATUS.md) · [Befehlsverifikation](docs/BEFEHLSVERIFIKATION.md).

## Offener Koordinatenkonflikt – vor Weiterbau vermessen

| Quelle | X inklusive | Z inklusive |
|---|---|---|
| Alter Chat zur 270×60-Yacht | −134 … 135 | −29 … 30 |
| Bestehendes GitHub-CraftScript | −135 … 134 | −30 … 29 |

Beide Varianten ergeben 270×60 Blöcke, sind aber um einen Block versetzt. Keine Variante ist als vermessener Weltbestand bestätigt. Keine automatische Verschiebung, Neumarkierung oder Migration durchführen. Erst die tatsächlichen äußeren Blockkoordinaten und Mittellinien in Aurora-Meer ablesen, mit Screenshots dokumentieren und gemeinsam entscheiden. Alle nachfolgenden Skripttabellen beschreiben ausschließlich den Skriptplan.

## Historischer Skriptplan: Maße und Orientierung

| Eigenschaft | Alter Entwurf | Neuer Plan |
|---|---|---|
| Länge × Breite | 450 × 100 | **270 × 60** |
| X, einschließlich | −224 … 225 | **−135 … 134** |
| Z, einschließlich | −49 … 50 | **−30 … 29** |
| Weiße Bauebene | Y=64 | Y=64 |
| Farbige Linien oben | Y=65 | Y=65 |
| Wasseroberfläche | im alten Text ungenau | oberste Wasserblöcke Y=63 |
| Kielhilfe | Y=52, X −210…200, Z0…1 | Y=52, Z=−1 und 0 |

Bug: **+X**, Heck: **−X**, Breite: Z. Länge immer `Maximum − Minimum + 1`.
270/450 = 60 %, 60/100 = 60 %: **40 % kürzer und schmaler**, aber 64 % weniger Grundfläche (16.200 statt 45.000 Blöcke). Die gesamte Minecraft-Welt wird dadurch nicht kleiner. Ob 270 m für eure Yacht passend sind, ist eine Designentscheidung, keine aus einem Vorbild abgeleitete Maßstabsbehauptung.

Bei geraden Abmessungen liegt die Mitte zwischen Blockreihen. Die neuen Blockzentren haben den Mittelwert −0,5; ihre tatsächliche Baufläche erstreckt sich von X −135 bis 135 und Z −30 bis 30 und ist geometrisch um den Weltursprung zentriert. Die alte Auswahl hatte Blockindex-Mittelwerte +0,5 und eine geometrische Mitte bei +1. Die neue Auswahl ist deshalb nicht nur eine Skalierung um die alte Mitte.

Y=63 bezeichnet den Blockindex der obersten Wasserschicht; deren obere Kante liegt geometrisch bei Y=64. Die weiße Fläche ist **eine Lage dick**, kein Deckstapel und kein fertiger Schiffsboden. Die Kielhilfe liegt 12 Blockindizes unter der Bauebene; die vertikale Planung wird nicht automatisch um 40 % verkleinert.

## Sieben Markierungen

Alle Bereiche einschließlich Endpunkten, alle Materialien `minecraft:*_concrete`.

| Nr. | Farbe / Block | Bedeutung | Neue Koordinaten |
|---|---|---|---|
| 1 | Weiß / `white_concrete` | Rechteckige Planungsfläche | X −135…134, Y64, Z −30…29 |
| 2 | Hellblau / `light_blue_concrete` | Doppelte Längsmittellinie | X −135…134, Y65, Z −1…0 |
| 3 | Grau / `gray_concrete` | Heckkante | X −135, Y65, Z −30…29 |
| 4 | Rot / `red_concrete` | Bugspitze als Referenz | X134, Y65, Z −1…0 |
| 5 | Orange / `orange_concrete` | Beginn der Bugverjüngung | X96, Y65, Z −30…29 |
| 6 | Gelb / `yellow_concrete` | Außenlinien des Hauptkörpers | X −135…95, Y65, Z −30 sowie 29 |
| 7 | Schwarz / `black_concrete` | Doppelte Kiel-Längshilfe | X −135…134, Y52, Z −1…0 |

Die vollständigen Koordinaten beider Varianten und der alte Befehlsstand stehen in [Koordinaten und Farblegende](docs/KOORDINATEN.md). Die alten Befehle belegen den Plan, keine Vermessung des tatsächlichen Weltstands.

X96 ist ein neuer, gerundeter Designwert in Anlehnung an den alten Taper-Beginn X160; keine bestätigte alte Markierung. Der Bugabschnitt X96…134 umfasst 39 Reihen. Noch keine schräge Rumpfkontur: das weiße Rechteck bleibt bis zur späteren Rumpfformung vollständig bestehen.

**Kreuzungen:** Weiß liegt separat auf Y64, Schwarz auf Y52. Auf Y65 gewinnt Orange gegenüber Hellblau, Grau gegenüber Hellblau/Gelb, Rot gegenüber Hellblau. Gelb endet vor Orange. Dadurch ist die hellblaue Linie an Heck, Orange und Bug bewusst unterbrochen. Ein Block kann nur eine Farbe haben.

## Historisches CraftScript nur auf geeignetem Testgelände

**Nicht auf der bestehenden Yacht ausführen.** Die folgende Anleitung gehört zum älteren Markierungsplan. Sie ist kein nächster Bauschritt in Aurora-Meer.

### Sicher starten

1. [Sicherheits- und Backup-Anleitung](docs/SICHERHEIT.md) lesen. Zuerst in einer vollständigen Weltkopie testen.
2. [Technik und Installation](docs/TECHNIK.md) beachten; CraftScripts benötigen einen zusätzlich erreichbaren Rhino-Motor.
3. `craftscripts/aurora.js` in den konfigurierten WorldEdit-CraftScript-Ordner kopieren (üblicherweise `config/WorldEdit/craftscripts/`). Als berechtigter Spieler in der Oberwelt ausführen.
4. Für eine **leere neue Baufläche** zuerst `/cs aurora preview fresh`; die ausgegebene Weltkennung prüfen und exakt in `CFG.worldId` eintragen. Danach nach Backup `/cs aurora apply fresh BACKUP`.
5. Für die **bereits markierte alte Fläche** ist `fresh` absichtlich gesperrt, sobald Beton im Ziel liegt. Erst den alten Plan lokal vervollständigen und prüfen, dann `/cs aurora preview migrate` und `/cs aurora apply migrate BACKUP`. Siehe Sicherheit.

Ein `apply`-Aufruf führt den gesamten freigegebenen Plan aus. Vorschau ist ein zusätzlicher, schreibfreier Schritt. Standard ohne Argumente: Vorschau der leeren Baufläche. **Nicht blind `/.s` benutzen**, weil dies den letzten Aufruf wiederholen kann.

Das Skript ist absichtlich nicht beliebig wiederholbar: vorhandene neue Betonmarkierungen führen bei `fresh` zum Abbruch. Bei einer beschädigten, bereits bebauten Yacht nicht einfach alle sieben Betonfarben freigeben! Stattdessen Weltkopie/Undo wiederherstellen oder einen neuen, einzeln geprüften Bestandsplan erstellen.

## Mitbauen und Änderungen vorschlagen

- In Issues Bauabschnitt, Koordinaten, Minecraft-/Fabric-/WorldEdit-Version und Screenshots angeben.
- Rumpf, Aufbauten, Innenräume und Technik getrennt planen; Bauabschnitte vorab abstimmen.
- Änderungen an Koordinaten und Skript gemeinsam als Pull Request einreichen; Tests und Sichtprüfung beilegen.
- Weltstände und Backups außerhalb von Git sichern. Keine Spielerdateien, Zugangsdaten, Serveradressen oder vollständigen Welten versehentlich hochladen.

Lokale Sicherheitsprüfungen: `node tests/safety.cjs`. Diese simulieren die WorldEdit-Schnittstelle; sie ersetzen keinen Test im Minecraft-Server.

## Veröffentlichung

Das bestehende öffentliche Repository ist [DEtrees95/ms-aurora](https://github.com/DEtrees95/ms-aurora). [Veröffentlichung und Datenschutz](docs/GITHUB.md).

## Validierung und Rechte

Koordinaten, Konfliktabbruch, Schreibgrenzen und Vorschau werden automatisiert im Schnittstellenmodell geprüft. **Kein echter Fabric-/Rhino- oder Minecraft-Lauf erfolgt.** Vor Produktion ist der Integrationstest in der Weltkopie erforderlich. Keine Zusage einer fehlerfreien Transaktion bei Laufzeitfehlern.

Keine Lizenzentscheidung für eure Map vorweggenommen. Vor Weiterverteilung oder Übernahme fremder Assets die Rechte klären und eine passende LICENSE ergänzen. Minecraft und WorldEdit gehören ihren jeweiligen Rechteinhabern; inoffizielles Community-Projekt.
