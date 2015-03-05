package se.webstep.microservice.guestbook.core;


public class Entry {

    public final long id;
    public final long guestbook_id;
    public final String text;
    public final String author;

    public Entry(long id, long guestbook_id, String text, String author) {
        this.id = id;
        this.guestbook_id = guestbook_id;
        this.text = text;
        this.author = author;
    }
}
