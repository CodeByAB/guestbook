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
    @JsonProperty("name")
    public final String name;
    @JsonProperty("message")
    public final String message;

    @JsonProperty("guestbook_id")
    public final long guestbook_id;

    public Entry(long id, long guestbook_id,String name, String message) {
        this.id = id;
        this.guestbook_id = guestbook_id;
        this.name = name;
        this.message = message;
        //this.createdAt = createdAt;
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
