package se.webstep.microservice.guestbook.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Create entry")
public class CreateEntry {
    @ApiModelProperty(value = "Name of the writer", required = true)
    @NotEmpty(message = "Name must be given")
    public final String name;
    @ApiModelProperty(value = "Email of the writer", required = true)
    @NotEmpty(message = "E-mail must be given")
    public final String email;
    @ApiModelProperty(value = "Message from the writer", required = true)
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
