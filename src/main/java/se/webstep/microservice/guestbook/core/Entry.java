package se.webstep.microservice.guestbook.core;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.time.LocalDateTime;

public class Entry {

    @JsonProperty("id")
    public final long id;

    @JsonProperty("title")
    public final String title;

    @JsonProperty("message")
    public final String message;

    @JsonProperty("guestbookId")
    public final long guestbookId;


    @JsonIgnore
    public final LocalDateTime createdAt;

    public Entry(long id, String title, String message, LocalDateTime createdAt, long guestbookId) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.guestbookId = guestbookId;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
