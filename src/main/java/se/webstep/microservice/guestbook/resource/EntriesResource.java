package se.webstep.microservice.guestbook.resource;

import io.dropwizard.jersey.params.IntParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/guestbook/{id}/entries")
@Produces(MediaType.APPLICATION_JSON)
public class EntriesResource {

    private final MicroServicesApplication service;

    public EntriesResource(MicroServicesApplication service) {
        this.service = service;
    }

    @GET
    public Response getEntries(@PathParam("id") IntParam id) {
        // TODO
        return null;
    }
}
