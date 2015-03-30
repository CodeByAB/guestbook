package se.webstep.microservice.guestbook.resource;

import com.google.common.base.Optional;
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

@Path("/guestbook/{guestBookId}/entry")
@Produces(MediaType.APPLICATION_JSON)
public class EntryResource {

    private final MicroServicesApplication service;

    public EntryResource(MicroServicesApplication service) {
        this.service = service;
    }

    @POST
    public Response create(@PathParam("guestBookId") LongParam guestbookId,
                           @Valid CreateEntry createEntry) {
        return Response.created(URI.create(String.valueOf(service.getJdbi()
                .onDemand(EntryDao.class)
                .save(guestbookId.get(), createEntry))))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") LongParam id) {
        Optional<Entry> optional = service.getJdbi().onDemand(EntryDao.class).get(id.get());
        if (optional.isPresent()) {
            return Response.ok(optional.get()).build();
        } else {
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("guestBookId") LongParam ignore,
                           @PathParam("id") LongParam id) {
        service.getJdbi().onDemand(EntryDao.class).delete(id.get());
        return Response.ok().build();
    }

    @PUT
    @Path(("/{id}/{status}"))
    public Response updateStatus(@PathParam("guestBookId") LongParam ignore,
                                 @PathParam("id") LongParam id,
                                 @PathParam("status") Entry.Status status) {
        service.getJdbi().onDemand(EntryDao.class).updateStatus(id.get(), status);
        return Response.ok().build();
    }

}
