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
* Börja med att clona repot `https://github.com/WebstepSweden/guestbook.git`
* Öppna projectet i valfri IDE
* Bygg projektet i din IDE eller via maven `mvn clean install`

Lättast är att starta tjänsten från IDE'n genom att köra klassen

`se.webstep.microservice.guestbook.MigrateAndRunService`

## Dokumentation
Hittas via micro-tjänsten på följande URI;

`http://localhost:8080/doc` eller `http://localhost:8080/swagger`

## Statestik
Hittas under följande URI:

`http://localhost:8081`

## Status på bygget
Status: [![Build Status](https://magnum.travis-ci.com/WebstepSweden/guestbook.svg?token=TREsfe1aR4iNYUvrPB2R&branch=master)](https://magnum.travis-ci.com/WebstepSweden/guestbook)


