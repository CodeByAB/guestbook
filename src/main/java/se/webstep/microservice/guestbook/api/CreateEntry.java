package se.webstep.microservice.guestbook.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import io.dropwizard.jersey.params.LongParam;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by reynir on 5.3.15.
 */

@ApiModel(value = "Create entry")
public class CreateEntry {


    @ApiModelProperty(value = "Id of guestbook", required = true)
    @NotEmpty(message = "the guestbook_id should not be empty")
    public final Long guestbook_id;

    @ApiModelProperty(value = "Name of the sender", required = true)
    @NotEmpty(message = "Name must not be empty")
    public final String name;

    @ApiModelProperty(value = "Message of the entry", required = true)
    @NotEmpty(message = "Message must not be empty")
    public final String message;

    @JsonCreator
    public CreateEntry(@JsonProperty("guestbook_id") String guestbook_id,
                       @JsonProperty("name") String name,
                       @JsonProperty("message") String message){

        this.guestbook_id = Long.parseLong(guestbook_id);
        this.name = name;
        this.message = message;
    }
}
