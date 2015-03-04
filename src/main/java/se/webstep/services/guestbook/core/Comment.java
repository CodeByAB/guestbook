package se.webstep.services.guestbook.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class Comment {
    @JsonProperty("id")
    public final long id;
    @JsonProperty("message")
    public final String message;
    public final long postId;
    public final LocalDateTime createdAt;

    public Comment(long id, String message, long postId, LocalDateTime createdAt) {
        this.id = id;
        this.message = message;
        this.postId = postId;
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
}
