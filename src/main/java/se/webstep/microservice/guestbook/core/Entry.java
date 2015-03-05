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

    @JsonProperty("guestbookId")
    public final long guestbookId;

    @JsonProperty("message")
    public final String message;

    @JsonIgnore
    public final LocalDateTime createdAt;

    public Entry(long id, long guestbookId, String message, LocalDateTime createdAt) {
        this.id = id;
        this.guestbookId = guestbookId;
        this.message = message;
        this.createdAt = createdAt;
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
