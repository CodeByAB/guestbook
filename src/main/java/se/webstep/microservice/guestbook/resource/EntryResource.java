package se.webstep.microservice.guestbook.resource;

import com.google.common.base.Optional;
import com.wordnik.swagger.annotations.*;
import io.dropwizard.jersey.params.LongParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Guestbook;
import se.webstep.microservice.guestbook.jdbi.EntryDao;
import se.webstep.microservice.guestbook.jdbi.GuestbookDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Api("Resource is handle Entry")
@Path("/guestbook/{guestbookId}/entry")
@Produces(MediaType.APPLICATION_JSON)
public class EntryResource {

    public final MicroServicesApplication service;

    public EntryResource(MicroServicesApplication service) {
        this.service = service;
    }

    @ApiOperation("Create an entry in a specific guestbook")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry was created"),
            @ApiResponse(code = 404, message = "Unknown guestbook")
    })
    @POST
    public Response create(@ApiParam(value = "ID of guestbook", required = true)
                               @PathParam("guestbookId") LongParam guestbookId, CreateEntry createEntry) {
        System.out.println("create - guestbookId = " + guestbookId + ", entry: " + createEntry);
        Optional<Guestbook> guestbookOptional = service.getJdbi().onDemand(GuestbookDao.class).get(guestbookId.get());
        if( !guestbookOptional.isPresent() ){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        long newEntryId = service.getJdbi().onDemand(EntryDao.class).save(guestbookId.get(), createEntry);
        return Response.created(URI.create(String.valueOf(newEntryId))).build();
    }


    @ApiOperation("Delete an entry")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry was deleted"),
            @ApiResponse(code = 404, message = "Unknown entry")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@ApiParam(value = "Guestbook Id", required = true) @PathParam("guestbookId") LongParam guestbookId,
                           @ApiParam(value = "Entry Id", required = true) @PathParam("id") LongParam id) {
        EntryDao entryDao = service.getJdbi().onDemand(EntryDao.class);
        if( !entryDao.get(id.get()).isPresent()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        entryDao.delete(id.get());
        return Response.ok().build();
    }
}
