package se.webstep.microservice.guestbook.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entry {

    @JsonProperty("id")
    public final long id;

    @JsonProperty("message")
    public final String message;

    @JsonProperty("guestbookId")
    public final long guestbookId;

    public Entry(long id, String message, long guestbookId) {
        this.id = id;
        this.message = message;
        this.guestbookId = guestbookId;
    }
}
