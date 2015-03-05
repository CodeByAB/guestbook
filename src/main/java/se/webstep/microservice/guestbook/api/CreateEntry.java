package se.webstep.microservice.guestbook.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Create entry")
public class CreateEntry {


    @ApiModelProperty(value = "Title of the entry", required = true)
    @NotEmpty(message = "Entry title must not be empty")
    public final String title;

    @ApiModelProperty(value = "Message of the entry", required = true)
    @NotEmpty(message = "Entry message must not be empty")
    public final String message;

    @JsonCreator
    public CreateEntry(@JsonProperty("title") String title, @JsonProperty("message") String message) {
        this.title = title;
        this.message = message;
    }

}
