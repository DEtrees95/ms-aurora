# Testprotokoll

Datum: 22.10.2026.

## Automatisch lokal bestanden

`node tests/safety.cjs`: **13 Prüfungen bestanden**.

Geometrie: 16.200 weiße Blöcke auf Y64. Endgültige Farben nach Überschneidungen: 534 hellblau, 460 gelb, 60 orange, 60 grau, 2 rot auf Y65 und 540 schwarz auf Y52. Zusammen 17.856 Markierungsblöcke. Das ist kein Rumpfvolumen.

Geprüft: schreibfreie Vorschau, exakte Grenzen und Farbreihenfolge, vollständiger Konfliktabbruch vor EditSession, Schutz vorhandenen Betons im fresh-Modus, Migrationssperre, Backup-Bestätigung, Weltbindung, Änderungslimit, Beschränkung auf alten Plan plus neue Markierungen, fremde Blöcke im Altplan, fehlende alte Markierungen, Wasser an freigegebenen alten Kielpositionen, ungültige Altpläne und sicherer Abbruch bei Wiederholung.

## Noch vor Produktionsnutzung erforderlich

Kein echter Server-/Rhino-Test durchgeführt. Folgendes in einer gesicherten Weltkopie ergänzen:

- Minecraft / Fabric Loader / Java / WorldEdit / Rhino-Versionen:
- Startprotokoll ohne relevante Fehler:
- `/cs aurora preview fresh` verfügbar:
- Weltkennung und geprüfte Dimension:
- Alle sieben Markierungen nach apply visuell korrekt:
- `//undo` stellt vorherigen Zustand wieder her:
- Vollständigkeit der vermessenen alten Markierungen 1–7:
- `preview migrate` / `apply migrate` / Undo in Weltkopie geprüft:
- Wasser-/Mod-Physik, Schutzplugins, Masken und Nachbarbauten geprüft:
- Vollständiges Backup erfolgreich wiederhergestellt:

Mock-Tests simulieren nur die benötigten API-Methoden. Keine Aussage über Ladefähigkeit des Rhino-Motors, Serverleistung oder fehlerfreies Rollback bei Laufzeitfehlern.
