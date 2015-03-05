package se.webstep.microservice.guestbook.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import se.webstep.microservice.guestbook.core.Guestbook;

/**
 * Created by marc on 2015-03-05.
 */
public class CreateEntry {

    @NotEmpty(message = "Message must not be empty")
    public final String message;

    @JsonCreator
    public CreateEntry(@JsonProperty("message") String message) {
        this.message = message;
    }
}
