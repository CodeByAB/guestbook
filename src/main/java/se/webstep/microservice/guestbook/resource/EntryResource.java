package se.webstep.microservice.guestbook.resource;

import io.dropwizard.jersey.params.LongParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.jdbi.EntryDao;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/guestbook/{id}/entry")
@Produces(MediaType.APPLICATION_JSON)
public class EntryResource {

    private final MicroServicesApplication service;

    public EntryResource(MicroServicesApplication service) {
        this.service = service;
    }

    @POST
    public Response create(@PathParam("id") LongParam id, @Valid CreateEntry entry) {
        return Response.created(
                URI.create(
                    String.valueOf(
                            getEntryDao().save(id.get(), entry)
                    )
        )).build();
    }

    @DELETE
    public Response delete(@PathParam("id") LongParam id) {
        getEntryDao().delete(id.get());
        return Response.ok().build();
    }

    private EntryDao getEntryDao() {
        return service.getJdbi().onDemand(EntryDao.class);
    }
}
