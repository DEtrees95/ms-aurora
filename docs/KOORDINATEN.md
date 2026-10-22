# Koordinaten und Farblegende

Alle Endpunkte sind inklusive. Ein Quader wird als `(X,Y,Z) → (X,Y,Z)` angegeben. Materialien sind Betonblöcke mit Namensraum `minecraft:`. Bug zeigt nach +X, Heck nach −X. Beim Blick zum Bug liegt Backbord bei −Z, Steuerbord bei +Z.

## Vollständiger Vergleich

| Nr. | Farbe / Material | Bedeutung | Bisheriger 450×100-Entwurf | Neuer 270×60-Plan |
|---|---|---|---|---|
| 1 | Weiß / white_concrete | Grundfläche, eine Lage | (−224,64,−49) → (225,64,50) | (−135,64,−30) → (134,64,29) |
| 2 | Hellblau / light_blue_concrete | Doppelte Mittellinie | (−224,65,0) → (225,65,1) | (−135,65,−1) → (134,65,0) |
| 3 | Grau / gray_concrete | Heckkante | (−224,65,−49) → (−224,65,50) | (−135,65,−30) → (−135,65,29) |
| 4 | Rot / red_concrete | Bugspitze | (225,65,0) → (225,65,1) | (134,65,−1) → (134,65,0) |
| 5 | Orange / orange_concrete | Beginn der Bugverjüngung | (160,65,−49) → (160,65,50) | (96,65,−30) → (96,65,29) |
| 6a | Gelb / yellow_concrete | Hauptkörper, Backbord | (−170,65,−49) → (160,65,−49) | (−135,65,−30) → (95,65,−30) |
| 6b | Gelb / yellow_concrete | Hauptkörper, Steuerbord | (−170,65,50) → (160,65,50) | (−135,65,29) → (95,65,29) |
| 7 | Schwarz / black_concrete | Doppelte Kielhilfe | (−210,52,0) → (200,52,1) | (−135,52,−1) → (134,52,0) |

Quelle des alten Entwurfs: konkrete WorldEdit-Befehle im Chat „Installation auf Windows Server“, Antwort zum 450×100-Plan. Eine nachträgliche Weltvermessung liegt nicht vor. Der neue Plan entspricht dem bestehenden `craftscripts/aurora.js`.

## Maße, Mittelpunkt und Designentscheidungen

- Alt: 225 − (−224) + 1 = 450; 50 − (−49) + 1 = 100; Grundfläche 45.000 Blöcke.
- Neu: 134 − (−135) + 1 = 270; 29 − (−30) + 1 = 60; Grundfläche 16.200 Blöcke.
- Beide horizontalen Maße sind um 40 % reduziert. Die Fläche sinkt um 64 %. Die Weltgröße selbst ändert sich nicht.
- Alte doppelte Mittellinie Z0/1, neue Z−1/0. Mittelpunkt und Rundung verschieben sich; dies ist kein blindes Skalieren um den alten Mittelpunkt.
- Die neue gelbe Linie beginnt am Heck, die alte erst bei X−170. Der neue Kiel reicht über die volle neue Länge. Diese Werte sind bewusste Änderungen des vorhandenen neuen Plans, keine exakt proportional verkleinerten alten Abschnitte.
- Y64, Y65 und Kiel Y52 bleiben erhalten; die Höhe wurde nicht skaliert. Oberste angenommene Wasserschicht: Blockindex Y63, obere Kante bei Y64. Tatsächlichen Wasserstand vor Ort prüfen.

## Schnittpunkte und Reihenfolge

Alter Chatablauf: Weiß → Hellblau → Grau → Rot → Orange → Gelb (beide Seiten) → Schwarz. Orange überschreibt Hellblau bei X160; Gelb überschreibt anschließend Orange bei Z−49 und Z50.

Neues Skript: Weiß → Hellblau → Gelb → Orange → Grau → Rot → Schwarz. Gelb endet bei X95; Orange bei X96 bleibt vollständig sichtbar. Grau gewinnt am Heck auch gegenüber Gelb. Hellblau wird an Heck, Orange und Bug unterbrochen.

Die Markierungen beschreiben Bauhilfen. Das weiße Rechteck ist noch keine fertige Rumpfform; die orange Linie schneidet den Bug nicht automatisch zu.

## Einzelne WorldEdit-Auswahl verstehen

Auf freiem, geprüftem Testgelände lässt sich eine Tabellenzeile so setzen (Beispiel neue Bugspitze):

```text
//sel cuboid
//pos1 134,65,-1
//pos2 134,65,0
//set minecraft:red_concrete
```

`//set` ersetzt vorhandene Blöcke ohne die Bestandsprüfung des Skripts. Dieses Beispiel ist deshalb keine Migrationsanleitung. Zum vollständigen Ersetzen aller sieben Markierungen einschließlich alter Reste die [Sicherheitsanleitung](SICHERHEIT.md#alle-sieben-markierungen-gemeinsam-ersetzen) verwenden.
