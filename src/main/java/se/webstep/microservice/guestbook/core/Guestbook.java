package se.webstep.microservice.guestbook.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.LocalDateTime;

public class Guestbook {

    public final long id;
    public final String name;
    public final Type status;
    public final LocalDateTime createdAt;

    public Guestbook(long id, String name, Type status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.status = status;
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

    public static enum Type {
        OPEN, CLOSED
    }
}
