package se.webstep.microservice.guestbook.resource;

import com.google.common.collect.ImmutableMap;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.dropwizard.jersey.params.LongParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.jdbi.EntryDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Resource is handle Entries")
@Path("/entries")
@Produces(MediaType.APPLICATION_JSON)
public class EntriesResource {

    public final MicroServicesApplication service;

    public EntriesResource(MicroServicesApplication service) {
        this.service = service;
    }


    @ApiOperation("Fetching guestbook entries with the given guesbook ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entries from the guestbook with the given ID is returned")
    })
    @GET
    @Path(("/{id}/entries"))
    public Response getEntries(@PathParam("id") LongParam id) {
        return Response.ok(ImmutableMap.of("list", service.getJdbi().onDemand(EntryDao.class).listbyGuestbook(id.get().longValue()))).build();

    }
}
