package se.webstep.microservice.guestbook.resource;


import com.google.common.collect.ImmutableMap;
import io.dropwizard.jersey.params.IntParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Path("/guestbook")
@Produces(MediaType.APPLICATION_JSON)
public class EntriesResource {


    public EntriesResource(MicroServicesApplication service) {

    }

    @GET
    @Path(("{id}/entries"))
    public Response getEntries(@PathParam("id")IntParam id) {

        return Response.ok(ImmutableMap.of("list", Arrays.asList())).build();
    }
}
