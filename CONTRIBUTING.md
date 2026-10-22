# Mitbauen

Bitte README, docs/SICHERHEIT.md und docs/TECHNIK.md lesen.

Vor einem Bauabschnitt ein Issue mit X/Y/Z-Bereich, geplantem Zweck und Screenshot anlegen. Keine Bestandsbauten ohne Abstimmung entfernen. Bauplanänderungen in separaten Pull Requests vorlegen, Koordinatentabelle und Skript gemeinsam aktualisieren.

Skripte zuerst mit `node tests/safety.cjs` prüfen. Danach Integrationstest in einer Weltkopie: Minecraft-, Fabric-, Java- und WorldEdit-Version, Rhino-Installation, Datum, Vorschauergebnis, Sichtprüfung und Undo-Ergebnis dokumentieren. Ein bestandenes Mock-Testpaket reicht für eine Produktionsfreigabe nicht.

Keine Weltdateien oder Servergeheimnisse im Pull Request. Neue Assets nur mit belegbaren Nutzungsrechten einreichen.
