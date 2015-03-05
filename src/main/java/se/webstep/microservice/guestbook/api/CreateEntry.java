package se.webstep.microservice.guestbook.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(value = "Create entry")
public class CreateEntry {

    @ApiModelProperty(value = "Entry text", required = true)
    @NotEmpty(message = "Text must not be empty")
    public final String text;

    @ApiModelProperty(value = "Author of entry", required = true)
    @NotEmpty(message = "Author must not be empty")
    public final String author;

    public CreateEntry(@JsonProperty("text") String text,
                       @JsonProperty("author") String author) {
        this.text = text;
        this.author = author;
    }

}
