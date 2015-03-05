package se.webstep.microservice.guestbook.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import se.webstep.microservice.guestbook.core.Guestbook;

@ApiModel(value = "Create guestbook")
public class CreateGuestbook {

    @ApiModelProperty(value = "Name of the guestbook", required = true)
    @NotEmpty(message = "Name must not be empty")
    public final String name;
    public final Guestbook.Type status;

    @JsonCreator
    public CreateGuestbook(@JsonProperty("name") String name) {
        this.name = name;
        this.status = Guestbook.Type.CLOSED;
    }

}
