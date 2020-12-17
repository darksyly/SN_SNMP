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
Traps und Informs empfangen und ausgeben

Unter dem Pfad SN_SNMP/SNMP_SN/out/artifacts kann die ausführbare .jar datei und eine .bat datei zum ausführen der .jar datei heruntergeladen werden. Ist Java installiert kann diese einfach ausgeführt werden um das Programm zu starten.
