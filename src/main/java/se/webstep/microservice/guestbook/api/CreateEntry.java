package se.webstep.microservice.guestbook.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class CreateEntry {
    @NotEmpty
    public final String message;

    @JsonCreator
    public CreateEntry(@JsonProperty("message") String message) {
        this.message = message;
    }
}
