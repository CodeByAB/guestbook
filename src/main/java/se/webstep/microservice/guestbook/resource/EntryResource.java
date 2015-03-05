package se.webstep.microservice.guestbook.resource;

import com.wordnik.swagger.annotations.Api;
import io.dropwizard.jersey.params.LongParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.jdbi.EntryDao;
import javax.validation.Valid;
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


    @POST
    public Response create(@PathParam("guestbookId") LongParam guestbookId, @Valid CreateEntry createEntry) {
        return Response.created(URI.create(String.valueOf(service.getJdbi()
                .onDemand(EntryDao.class)
                .save(guestbookId.get(), createEntry)))).build();
    }


    @DELETE
    public Response delete(@PathParam("id") LongParam id) {
        // TODO
        return null;
    }
}
