package se.webstep.services.guestbook.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class WriterCreator {

    @NotEmpty
    public final String name;
    @NotEmpty
    public final String email;

    @JsonCreator
    public WriterCreator(@JsonProperty("name") String name,
                         @JsonProperty("email") String email) {
        this.name = name;
        this.email = email;
    }

}
