package se.webstep.microservice.guestbook.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class CreateEntry {
    @NotEmpty(message = "Name must be given")
    public final String name;
    @NotEmpty(message = "E-mail must be given")
    public final String email;
    @NotEmpty(message = "Message must be given")
    public final String message;

    @JsonCreator
    public CreateEntry(@JsonProperty("name") String name,
                       @JsonProperty("email") String email,
                       @JsonProperty("message") String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

}
