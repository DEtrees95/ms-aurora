# Sicher arbeiten und zurücksetzen

## Vor jeder Änderung

1. Wartungsfenster: andere Spieler verlassen die Welt, Redstone-/Automatikbauten im Umfeld prüfen. Während Vorschau und Ausführung baut niemand im Bereich.
2. `/save-all flush` speichert nur. Es ist **kein Backup**. Server regulär mit `/stop` beenden und den vollständigen Weltordner einschließlich Dimensionen sowie Server-/Mod-Konfiguration separat kopieren. Sicherung datieren und zunächst ihre Wiederherstellung in einer getrennten Testinstanz prüfen.
3. In dieser Weltkopie Minecraft 1.21.1, Fabric und die tatsächlich installierte WorldEdit-Version dokumentieren. Kein Produktionsserver als erster Skripttest.
4. Die Vorschau gibt die WorldEdit-Weltkennung aus. Nur nach visueller Prüfung von Welt und Dimension diese exakt in `CFG.worldId` eintragen. Diese Kennung enthält ggf. den Weltnamen und ist nicht einfach `minecraft:overworld`.
5. Zielgebiet und Umgebung auf Bauten prüfen. Das Skript prüft nur seine konkreten Schreibpositionen, keine angrenzenden Bauten, Behälter, Entitäten, Redstone oder Wasserphysik. Eine Luftposition kann zu einem Gebäudeinnenraum gehören. Deshalb ist eine freie, unbebaute Bauzone Voraussetzung.

## Was das Skript schützt

`fresh` verändert ausschließlich die neuen Markierungspositionen: oben muss normale Luft vorliegen, auf Y52 Wasser. Festes Material, Höhlenluft, Pflanzen, Lava und andere unerwartete Blöcke führen vor der ersten Änderung zum vollständigen Abbruch. Vorhandene Markierungsfarben werden im fresh-Modus ebenfalls nicht akzeptiert.

`migrate` bildet die Vereinigung aus dem ausdrücklich geprüften alten Plan und dem neuen Plan. Nur dort wird gelesen/geschrieben. Es gibt **keinen** Befehl, der den Quader Y52…65 leer räumt. Zwischen Kiel und Bauebene wird nichts direkt geändert. Außerhalb des neuen Plans werden alte, passende Betonmarkierungen oberhalb Wasser durch Luft, unterhalb durch Wasser ersetzt. Diese Umgebungsannahme muss zum alten Bauplatz passen. Originale Landschaft, die beim damaligen `//set` überschrieben wurde, lässt sich daraus nicht rekonstruieren.

Alle Positionen werden vorab geprüft, der WorldEdit-Änderungsgrenzwert wird kontrolliert. Vorschau öffnet keine EditSession. Der tatsächliche Auftrag wird erst über `context.remember()` in eine gemeinsame WorldEdit-EditSession geschrieben.

**Keine automatische Erkennung der Blockherkunft:** Ein weißer Yachtblock am Ort eines weißen Hilfsblocks ist technisch identisch. Die Freigabe eines alten Plans bedeutet, dass ein Mensch diese Positionen als reine Bauhilfen geprüft hat. Gleichfarbige echte Bauten im alten Plan wären sonst gefährdet. Selbst ein erfolgreicher Materialvergleich beweist keine Herkunft.

## Alten Plan vervollständigen

`CFG.oldReviewed` bleibt standardmäßig `false`; die Migration ist damit gesperrt. Der verfügbare alte Gesprächsauszug bestätigt nur die weiße Fläche und die Koordinaten von hellblauer Linie, grauem Heck und rotem Bug. Orange X160 war vorgeschlagen; genaue alte gelbe Linien und Kiel-X/Z sind nicht bestätigt.

1. In einer Weltkopie **alle sieben** tatsächlichen Markierungen vermessen. Auch prüfen, ob die ursprüngliche 145 × 49-Fläche außerhalb der dokumentierten alten Fläche Reste hinterließ. Nicht belegte Reste werden nicht automatisch gelöscht.
2. In `CFG.old` ausschließlich tatsächliche Hilfspositionen ergänzen, als inklusive Quader `[x1,y1,z1,x2,y2,z2,"material"]`. Keine großen Leerraum-Quader. Spätere Einträge überschreiben frühere an Schnittpunkten; tatsächliche Endfarben abbilden.
3. Nur die sieben erlaubten Betonmaterialien und die alte Fläche X−224…225 / Z−49…50, Y64/65 sowie den bestätigten Kiel Y52 verwenden. Der Code begrenzt den alten Plan darauf. Abweichende echte Markierungen erst separat untersuchen, nicht die Grenzen pauschal erweitern.
4. Vorhandene Yacht-/Fremdbauten aus diesen Positionen herausnehmen. Falls sich die Markierungen nicht eindeutig von Bauten trennen lassen: **keine Migration**, alten Weltstand verwenden oder die neue Planung an einem freien Bauplatz in einer anderen Welt beginnen.
5. Nur nach vollständiger Prüfung `oldReviewed: true` setzen. `/cs aurora preview migrate` ausführen. Die erste Abweichung ist kein Anlass, zusätzliche Baumaterialien pauschal freizugeben.
6. Nach Backup `/cs aurora apply migrate BACKUP` ausführen. Der Text BACKUP ist eine bewusste Bedienbestätigung; das Skript erstellt oder prüft selbst keine Sicherungsdatei.

Ein unvollständiger Altplan kann alte Markierungen außerhalb des neuen Ziels zurücklassen. Das Skript behauptet nicht, sie erkannt zu haben. Darum muss die menschliche Prüfung vor der Freigabe wirklich vollständig sein.

## Undo und Fehler

Direkt nach dem CraftScript als **derselbe Spieler** `//undo` ausführen. Nicht blind `//undo 2` verwenden; dies kann fremde frühere Arbeit derselben Sitzung zurücknehmen. Undo-Historie ist kein dauerhafter Backup-Ersatz und kann bei Neustart/Sessionverlust fehlen.

Bei Timeout, Modfehler oder Schreibfehler kann ein Teil bereits geändert sein. Keine ACID-Transaktion und kein automatisch garantiertes Rollback. Sofort stoppen, sichtbaren Zustand prüfen und Undo in der Testwelt testen; bei Unklarheit vollständiges Backup bei gestopptem Server wiederherstellen. Skript-Zeitlimit und WorldEdit-Limits hängen vom Server ab. Nicht einfach alle Schutzlimits deaktivieren.

Auch Vanilla-/Mod-Physik kann außerhalb der direkt geschriebenen Positionen reagieren. Wirkliche Bestandssicherheit entsteht durch freien Bauplatz, Wartungsfenster und geprüfte Sicherung, nicht durch eine Farbliste allein.
