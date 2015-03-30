package se.webstep.microservice.guestbook.resource;

import com.google.common.base.Optional;
import com.wordnik.swagger.annotations.*;
import io.dropwizard.jersey.params.LongParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Entry;
import se.webstep.microservice.guestbook.jdbi.EntryDao;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Api("Resource is handle Entry")
@Path("/guestbook/{guestBookId}/entry")
@Produces(MediaType.APPLICATION_JSON)
public class EntryResource {

    private final MicroServicesApplication service;

    public EntryResource(MicroServicesApplication service) {
        this.service = service;
    }

    @ApiOperation("Creating a entry in a guestbook")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry created")
    })
    @POST
    public Response create(@ApiParam(value = "ID of guestbook", required = true)
                           @PathParam("guestBookId") LongParam guestbookId,
                           @Valid CreateEntry createEntry) {
        return Response.created(URI.create(String.valueOf(service.getJdbi()
                .onDemand(EntryDao.class)
                .save(guestbookId.get(), createEntry))))
                .build();
    }

    @ApiOperation("Fetching a entry with the given ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry with given ID is returned"),
            @ApiResponse(code = 404, message = "No entry found")
    })
    @GET
    @Path("/{id}")
    public Response get(@ApiParam(value = "ID of the entry", required = true)
                        @PathParam("id") LongParam id) {
        Optional<Entry> optional = service.getJdbi().onDemand(EntryDao.class).get(id.get());
        if (optional.isPresent()) {
            return Response.ok(optional.get()).build();
        } else {
            return Response.status(404).build();
        }
    }

    @ApiOperation("Deleting a entry with the given ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry is deleted")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@ApiParam(value = "ID of guestbook", required = true)
                           @PathParam("guestBookId") LongParam ignore,
                           @ApiParam(value = "ID of entry", required = true)
                           @PathParam("id") LongParam id) {
        service.getJdbi().onDemand(EntryDao.class).delete(id.get());
        return Response.ok().build();
    }

    @ApiOperation("Updating status for a entry to readable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status was updated")
    })
    @PUT
    @Path(("/{id}/readable"))
    public Response updateStatusReadable(@ApiParam(value = "ID of guestbook", required = true)
                                         @PathParam("guestBookId") LongParam ignore,
                                         @ApiParam(value = "ID of entry", required = true)
                                         @PathParam("id") LongParam id) {
        service.getJdbi().onDemand(EntryDao.class).updateStatus(id.get(), Entry.Status.READABLE);
        return Response.ok().build();
    }

    @ApiOperation("Updating status for a entry to unreadable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status was updated")
    })
    @PUT
    @Path(("/{id}/un_readable"))
    public Response updateStatusUnReadable(@ApiParam(value = "ID of guestbook", required = true)
                                           @PathParam("guestBookId") LongParam ignore,
                                           @ApiParam(value = "ID of entry", required = true)
                                           @PathParam("id") LongParam id) {
        service.getJdbi().onDemand(EntryDao.class).updateStatus(id.get(), Entry.Status.UN_READABLE);
        return Response.ok().build();
    }

}
