# Micro-service workshop

Följande behövs:

* Java 1.8
* Maven
* GIT
* En IDE, exempelvis IntelliJ

## Allmänt
Syftet är att få känna på att bygga REST tänster i Java med hjälp av Dropwizard.


Vi kommer påbörja att bygga en gästbok, under det första tillfället (idag alltså) kommer vi
att bygga ett REST gränssnitt för gästboken.

Tjänsten kommer ha typ tre resurser:

* /guestbook
* /guestbook/{id}/entry
* /guestbook/entries

Den första finns nästan komplett för att kunna tittas på, övriga är fria att utveckla.

## Komma igång
* Börja med att clona repot `ttps://github.com/WebstepSweden/guestbook.git`
* Öppna projectet i valfri IDE

Lättast är att starta tjänsten från IDE'n genom att köra klassen

`se.webstep.microservice.guestbook.MigrateAndRunService`

Den tar upp tjänsten på port 8080, dokumentation hittas under

`http://localhost:8080/doc`