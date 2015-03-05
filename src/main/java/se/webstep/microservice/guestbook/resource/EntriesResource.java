package se.webstep.microservice.guestbook.resource;

import com.google.common.collect.ImmutableMap;
import com.wordnik.swagger.annotations.*;
import io.dropwizard.jersey.params.IntParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.jdbi.EntryDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Resource is handle entries")
@Path("/guestbook/{guestbookId}/entries")
@Produces(MediaType.APPLICATION_JSON)
public class EntriesResource {

    public final MicroServicesApplication service;

    public EntriesResource(MicroServicesApplication service) {
        this.service = service;
    }

    @ApiOperation("List all entries in given guestbook")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returning all entries in given guestbook")
    })
    @GET
    public Response getEntries(@ApiParam(value = "ID of guestbook", required = true)
                                   @PathParam("guestbookId") IntParam guestbookId) {
        return Response.ok(ImmutableMap.of("entries", service.getJdbi().onDemand(EntryDao.class)
                .list(guestbookId.get()))).build();
    }
}
