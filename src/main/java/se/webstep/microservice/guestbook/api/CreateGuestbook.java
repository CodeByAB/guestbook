package se.webstep.microservice.guestbook.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import se.webstep.microservice.guestbook.core.Guestbook;

public class CreateGuestbook {

    @NotEmpty(message = "Name must not be empty")
    public final String name;
    public final Guestbook.Type status;

    @JsonCreator
    public CreateGuestbook(@JsonProperty("name") String name) {
        this.name = name;
        this.status = Guestbook.Type.CLOSED;
    }

}
