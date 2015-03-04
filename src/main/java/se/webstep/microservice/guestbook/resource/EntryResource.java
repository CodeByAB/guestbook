package se.webstep.microservice.guestbook.resource;

import io.dropwizard.jersey.params.LongParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/guestbook/{id}/entry")
@Produces(MediaType.APPLICATION_JSON)
public class EntryResource {

    public EntryResource(MicroServicesApplication service) {

    }

    @POST
    public Response create(@PathParam("id") LongParam id) {

        return Response.created(URI.create("id")).build();
    }

    @DELETE
    public Response delete(@PathParam("id") LongParam id) {

        return Response.ok().build();
    }
}
