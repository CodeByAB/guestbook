package se.webstep.microservice.guestbook.resource;

import com.google.common.base.Optional;
import com.wordnik.swagger.annotations.*;
import io.dropwizard.jersey.params.LongParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Entry;
import se.webstep.microservice.guestbook.core.Guestbook;
import se.webstep.microservice.guestbook.jdbi.EntryDao;
import se.webstep.microservice.guestbook.jdbi.GuestbookDao;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Api("Resource is handle single entries")
@Path("/guestbook/{guestbookId}/entry")
@Produces(MediaType.APPLICATION_JSON)
public class EntryResource {

    private MicroServicesApplication service;

    public EntryResource(MicroServicesApplication service) {
        this.service = service;
    }

    @ApiOperation("Creating an entry")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry is created")
    })
    @POST
    public Response create(@PathParam("guestbookId") LongParam guestbookId,
                           @Valid @ApiParam(value = "entry", defaultValue = "{ \"text\": \"Hello world\", \"author\": \"Uzilan\"}") CreateEntry createEntry) {
        Optional<Guestbook> guestbook = service.getJdbi().onDemand(GuestbookDao.class).get(guestbookId.get());
        if (guestbook.isPresent()) {
            return Response.created(URI.create(String.valueOf(service.getJdbi()
                    .onDemand(EntryDao.class)
                    .save(guestbookId.get(), createEntry)))).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @ApiOperation("Deleting an entry")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry is deleted")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("guestbookId") LongParam guestbookId, @PathParam("id") LongParam id) {
        Optional<Guestbook> guestbook = service.getJdbi().onDemand(GuestbookDao.class).get(guestbookId.get());
        if (guestbook.isPresent()) {
            Optional<Entry> entry = service.getJdbi().onDemand(EntryDao.class).get(guestbookId.get(), id.get());
            if (entry.isPresent()) {
                service.getJdbi().onDemand(EntryDao.class).delete(id.get());
                return Response.ok().build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
