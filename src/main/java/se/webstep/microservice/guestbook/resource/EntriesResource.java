package se.webstep.microservice.guestbook.resource;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.dropwizard.jersey.params.IntParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.core.Guestbook;
import se.webstep.microservice.guestbook.jdbi.EntryDao;
import se.webstep.microservice.guestbook.jdbi.GuestbookDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Resource is handle entries")
@Path("/guestbook/{id}/entries")
@Produces(MediaType.APPLICATION_JSON)
public class EntriesResource {

    public final MicroServicesApplication service;

    public EntriesResource(MicroServicesApplication service) {
        this.service = service;
    }

    @ApiOperation("Fetching all entries with the given guestbook ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entries for guestbook with given ID are returned"),
            @ApiResponse(code = 404, message = "No guestbook found")
    })
    @GET
    public Response getEntries(@PathParam("id") IntParam id) {
        Optional<Guestbook> guestbook = service.getJdbi().onDemand(GuestbookDao.class).get(id.get());
        if (guestbook.isPresent()) {
            return Response.ok(ImmutableMap.of("list", service.getJdbi().onDemand(EntryDao.class).list(id.get()))).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
