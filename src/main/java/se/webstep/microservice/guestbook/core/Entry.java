package se.webstep.microservice.guestbook.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Entry {

    @JsonProperty("id")
    public final long id;
    @JsonProperty("guestbook_id")
    public final long guestbookId;
    @JsonProperty("text")
    public final String text;
    @JsonProperty("author")
    public final String author;

    public Entry(long id, long guestbookId, String text, String author) {
        this.id = id;
        this.guestbookId = guestbookId;
        this.text = text;
        this.author = author;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
