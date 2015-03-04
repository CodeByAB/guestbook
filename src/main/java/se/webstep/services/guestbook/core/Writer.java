package se.webstep.services.guestbook.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Writer {

    @JsonProperty("id")
    public final long id;
    @JsonProperty("name")
    public final String name;
    @JsonProperty("city")
    public final String city;
    @JsonProperty("email")
    public final String email;
    @JsonProperty("web_site")
    public final String website;

    public Writer(long id,
                  String name,
                  String city,
                  String email,
                  String website) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.email = email;
        this.website = website;
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
