# Gemeinsam auf dem MS-Aurora-Server bauen

Die MS Aurora entsteht gemeinsam auf dem vom Projektinhaber eingerichteten Minecraft-Server. Mitbauer sollen dort beitreten, ausprobieren und an den abgestimmten Bauabschnitten mitarbeiten. Das Repository dokumentiert diesen gemeinsamen Bauplatz. Eine eigene Welt ist zum Mitbauen nicht erforderlich.

Serveradresse, Zugang und Baurechte werden vom Betreiber bekanntgegeben; bisher ist hier keine bestätigte öffentliche Adresse hinterlegt. Das dokumentierte technische Ziel ist Minecraft Java 1.21.1 / Fabric. Die tatsächlich installierte vollständige Modliste ist noch nicht in diesem Repository dokumentiert.

## Gerüst importieren

Es gibt zwei Möglichkeiten:
- **Echte Gerüstblöcke auf dem Server:** Eine WorldEdit-Datei `.schem` wird vom Betreiber serverseitig geladen und eingefügt. Alle Spieler sehen die Blöcke und können daran weiterbauen. Für diesen Weg ist keine zusätzliche Litematica-Installation der Mitbauer erforderlich.
- **Durchsichtige Bauvorlage:** Litematica mit passender MaLiLib-Abhängigkeit auf dem PC des jeweiligen Mitbauers. Das Hologramm ist eine lokale Anzeige; eine Platzierung wird dadurch nicht automatisch für alle geteilt.

WorldEdit ist der vorgesehene Importweg für ein gemeinsames Gerüst aus echten Blöcken. Litematica ist eine mögliche ergänzende Bauhilfe; dies ist keine Behauptung, dass sie bereits installiert oder früher vereinbart wurde. Beide Wege benötigen eine tatsächlich bereitgestellte, geprüfte Gerüstdatei. Der bisherige CraftScript-Plan setzt nur die sieben Markierungen und liefert noch kein räumliches Rumpfgerüst.

## Befehle für eine bereitgestellte WorldEdit-Datei

Noch keine Gerüstdatei im Repository vorhanden. Der folgende Dateiname ist der vorgesehene Name einer künftigen Lieferung und aktuell kein vorhandener Download.

1. Betreiber legt `aurora-geruest-270x60.schem` im konfigurierten WorldEdit-Schematic-Ordner **des Servers** ab; bei Fabric üblicherweise `config/WorldEdit/schematics/`. Konfiguration hat Vorrang.
2. Als Spieler mit den erforderlichen WorldEdit-Rechten auf dem gemeinsamen Server:

```text
//schem list
//schem load aurora-geruest-270x60
//paste -n
```

Laden ändert keine Weltblöcke. `//paste -n` wählt den Zielbereich ohne Einfügen aus. Lage und Abmessungen prüfen. Der gespeicherte Ursprung/Versatz bestimmt die Lage relativ zum Spieler; deshalb muss die Gerüstlieferung einen genauen Einfügepunkt nennen. Ohne diesen nicht an der Yacht einfügen.

3. Nach Sicherung und Prüfung des freien Zielbereichs:

```text
//paste -a
```

`-a` lässt Luft aus der Datei aus. Feste Gerüstblöcke können trotzdem bestehende Blöcke überschreiben. Dieser Import entfernt außerdem keine alten 450×100-Markierungen. Dafür das getrennte [Migrationsverfahren](SICHERHEIT.md) verwenden.

4. Direkt rückgängig als derselbe Spieler:

```text
//undo
```

Zuerst technisch in einer vom Betreiber verwalteten Server-Weltkopie prüfen; anschließend wird gemeinsam auf dem eigentlichen Server gebaut. Diese technische Vorprüfung bedeutet nicht, dass Mitbauer eigene Welten betreiben sollen.

## Bereits verfügbare Markierungsbefehle

Nach Installation und Konfiguration des vorhandenen CraftScripts:

```text
/cs aurora preview migrate
/cs aurora apply migrate BACKUP
```

Migration verlangt den vervollständigten Altplan, `oldReviewed: true`, passende `CFG.worldId` und ein echtes Backup; Details in [SICHERHEIT.md](SICHERHEIT.md). Für eine freie neue Fläche lautet der Modus `fresh`. Nicht auf den vorhandenen Beton anwenden.

## Mods und Zuständigkeit

| Komponente | Ort / Aufgabe | Stand |
|---|---|---|
| Fabric + passendes WorldEdit | Server: Bauwerkzeuge, .schem-Import, Undo | Dokumentierter Zielaufbau; installierte Builds nicht ausgelesen |
| Rhino | Zusatzvoraussetzung nur für das CraftScript | Auf dem Server noch nicht verifiziert; für normalen .schem-Import nicht erforderlich |
| Litematica + MaLiLib | Optional auf PCs: durchsichtige Bauvorlage | Keine installierte Version bestätigt |

Eine vollständige frühere Modliste ist im verfügbaren Gesprächsexport nicht enthalten. Keine zusätzlichen Bau-, Dekorations- oder Performance-Mods ungeprüft als bestehendes Serverpaket ausgeben. Vor einem verbindlichen Modpaket müssen tatsächliche Versionen und Abhängigkeiten feststehen.

## Quellen

- [EngineHub: Clipboard und Schematic-Import](https://worldedit.enginehub.org/en/latest/usage/clipboard/)
- [Litematica-Projekt](https://github.com/sakura-ryoko/litematica)
- [MaLiLib-Projekt und Abhängigkeiten](https://modrinth.com/mod/malilib)

Die aktuelle WorldEdit-Dokumentation kann eine neuere Version zeigen; Befehle am installierten Serverbuild prüfen.
