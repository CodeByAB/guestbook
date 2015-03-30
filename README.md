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


This resource handles creating, deleting, fetching guestbooks.


## Create
`curl -X "POST" "http://localhost:8080/guestbook" -H "Content-Type: application/json" -d $'{"name":"Test"}'`

## List all
`curl -X "GET" "http://localhost:8080/guestbook/list"`

## List all Open
`curl -X "GET" "http://localhost:8080/guestbook/list/open"`

## Fetch a single
`curl -X "GET" "http://localhost:8080/guestbook/[ID]"`

## Open
`curl -X "PUT" "http://localhost:8080/guestbook/[ID]/open"`

## Close
`curl -X "PUT" "http://localhost:8080/guestbook/[ID]/close"`