# guestbook
Workshop about micro-services




## Guestbook

### To Create a guestbook

```
curl -X "POST" "http://localhost:8080/guestbook" -H "Content-Type: application/json" -d $'{"name":"Test"}'

```

### To list all guestbook's

```
curl -X "GET" "http://localhost:8080/guestbook/list"

```

### To fetch a single guestbook

```
http://localhost:8080/guestbook/1

```


## Entity


