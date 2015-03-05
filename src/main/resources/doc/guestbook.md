This resource handles creating, deleting, fetching guestbooks.


## Create
`curl -X "POST" "http://localhost:8080/guestbook" -H "Content-Type: application/json" -d $'{"name":"Test"}'`

### Consumes

```
{
    "name":"Test"
}

```

### Produces


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


## Create entry
`curl -X "POST" "http://localhost:8080/guestbook/[ID]/entry" -H "Content-Type: application/json" -d $'{"message":"test"}'`

## Get all entries in guestbook
`curl -X "GET" "http://localhost:8080/guestbook/[ID]/entries"`

## Delete entry
`curl -X "DELETE" "http://localhost:8080/guestbook/[ID]/entry/[ID]"`

