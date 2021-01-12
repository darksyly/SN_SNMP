# SN_SNMP

1. Meilenstein
Die ersten grundlegenden Funktionen des Programms funktionieren, das heißt es gibt eine Benutzereingabe über die Konsole wo der Benutzer eine IP eingeben kann. Über diese IP werden dann mittels SNMP einige Grundlegende Informationen erhalten. Des weiteren kann der Benutzer auch eigene OIDs eingeben um weitere Informationen über das Gerät zu erhalten.
Ich habe das Programm an meinem eigenen PC und am Router im SN Netz getestet, beides hat problemlos funktioniert.
Ich werde noch weitere Funktionen hinzufügen und auch die Benutzereingabe wird noch erweitert werden.

Unter dem Pfad SN_SNMP/SNMP_SN/out/artifacts kann die ausführbare .jar datei und eine .bat datei zum ausführen der .jar datei heruntergeladen werden. Ist Java installiert kann diese einfach ausgeführt werden um das Programm zu starten.

2. Meilenstein
Das Programm wurde überarbeitet und einige Funktionen wurden hinzugefügt. Auch das User Interface wurde erweitert sodass der Benutzer nun über die Kommandozeile zwischen mehreren verschiedenen Befehlen aussuchen kann.

Dem Benutzer stehen folgende Funktionen des Programms zur Verfügung:
/help: zum ausgeben aller verfügbaren Befehle
/set ip: um die Ip zu ändern auf welche die SNMP anfragen ausgeführt werden sollen (standardmäßig 127.0.0.1)
/scan: um Ein Netzwerk zu scannen, das heißt es werden dem Benutzer aktive Ips angezeigt und ob diese Mit SNMP erreichbar sind
/getOID: Der Benutzer kann eine OID eingeben welche an die festgelegten IP gesendet wird
/get Infos: Der Benutzer erhält generelle Informationen über die festgelegte IP mittels SNMP

next Steps:
Mib Files

Im Ordner SN_SNMP gibt es nun einen Ordner Screenshots in dem zwei Screenshots der Konsole/UI abgespeichert sind.

Unter dem Pfad SN_SNMP/SNMP_SN/out/artifacts kann die ausführbare .jar datei und eine .bat datei zum ausführen der .jar datei heruntergeladen werden. Ist Java installiert kann diese einfach ausgeführt werden um das Programm zu starten.

3.Meilenstein
Das Programm ist voll funktionsfähig. Es wurden seit dem 2. Meilenstein einige neue Funktionen hinzugefügt, wie das Ändern des Community Strings, Erhalten von Informationen mithilfe von Mib Files und das Laden von Mib files. Ebenso wurde das UserInterface über die Kommandozeile nochmals leicht verbessert.

Dem Benutzer stehen folgende Funktionen des Programms zur Verfügung:
/help: zum ausgeben aller verfügbaren Befehle
/set ip: um die Ip zu ändern auf welche die SNMP anfragen ausgeführt werden sollen (standardmäßig 127.0.0.1)
/scan: um Ein Netzwerk zu scannen, das heißt es werden dem Benutzer aktive Ips angezeigt und ob diese Mit SNMP erreichbar sind
/getOID: Der Benutzer kann eine OID eingeben welche an die festgelegten IP gesendet wird (jetzt neu: Der benutzer kann nun ebenfalls den namen eingeben (z.B. sysUpTime) falls das entsprechende Mib File geladen ist. Es werden standardmäßig 2 Mibfiles bei Programmstart geladen)
/get Infos: Der Benutzer erhält generelle Informationen über die festgelegte IP mittels SNMP
/loadMib: zum laden eines weiteren Mib-Files
/changeCom: Der Benutzer hat die Möglichkeit den CommunityString zu ändern

Next Steps: (Nächste Schritte/Verbesserungen welche ich vornehmen würde wenn das Projekt noch länger dauern würde)
Traps und Informs empfangen und senden
verbesserung der scan Network methode da diese seeeeeeehr zeitaufwändig ist und nicht besonders performant

Im Ordner SN_SNMP, im Ordner Screenshots sind einige Screenshots der Konsole/UI abgespeichert.

Unter dem Pfad SN_SNMP/SNMP_SN/out/artifacts kann die ausführbare .jar datei und eine .bat datei zum ausführen der .jar datei heruntergeladen werden. Ist Java installiert kann diese einfach ausgeführt werden um das Programm zu starten.
