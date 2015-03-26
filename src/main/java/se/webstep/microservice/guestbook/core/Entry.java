package se.webstep.microservice.guestbook.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class Entry {
    @JsonProperty("id")
    public final long id;
    @JsonProperty("name")
    public final String name;
    @JsonProperty("email")
    public final String email;
    @JsonProperty("message")
    public final String message;
    @JsonProperty("createdAt")
    public final LocalDateTime createdAt;
    @JsonProperty("status")
    public final Status status;

    public Entry(long id, String name, String email, String message, LocalDateTime createdAt, Status status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.message = message;
        this.createdAt = createdAt;
        this.status = status;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public enum Status {
        READABLE, UN_READABLE
    }


}
