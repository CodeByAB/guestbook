package se.webstep.microservice.guestbook.resource;

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

@Api("Resource is Entries")
@Path("/guestbook/{id}/entries")
@Produces(MediaType.APPLICATION_JSON)
public class EntriesResource {

    public final MicroServicesApplication service;

    public EntriesResource(MicroServicesApplication service) {
        this.service = service;
    }

    @ApiOperation("Fetching all entries for a guestbook")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entries for guestbook with given ID are returned"),
            @ApiResponse(code = 404, message = "No guestbook found")
    })
    @GET
    public Response getEntries(@PathParam("id") LongParam guestbookId) {
        return Response.ok().entity(
            service.getJdbi().onDemand(EntryDao.class).list(guestbookId.get())).build();
    }
}
