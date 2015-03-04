package se.webstep.services.guestbook.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

public class Post {

    @JsonProperty("id")
    public final long id;
    @JsonProperty("ip")
    public final String ip;
    @JsonProperty("headLine")
    public final String headLine;
    @JsonProperty("message")
    public final String message;
    public final LocalDateTime createTime;
    public final boolean viewAble;
    public final Optional<LocalDateTime> viewTime;

    public Post(long id, String ip, String headline, String message, LocalDateTime createdAt, boolean viewAble,
                Optional<LocalDateTime> viewTime) {
        this.id = id;
        this.ip = ip;
        this.headLine = headline;
        this.message = message;
        this.createTime = createdAt;
        this.viewAble = viewAble;
        this.viewTime = viewTime;
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
