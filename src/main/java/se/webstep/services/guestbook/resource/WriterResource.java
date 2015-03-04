package se.webstep.services.guestbook.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.jersey.params.LongParam;
import se.webstep.services.guestbook.GuestBookServicesApplication;
import se.webstep.services.guestbook.api.WriterCreator;
import se.webstep.services.guestbook.core.Writer;
import se.webstep.services.guestbook.jdbi.WriterDao;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/writer")
@Produces(MediaType.APPLICATION_JSON)
public class WriterResource {

    private final WriterDao writerDao;

    public WriterResource(GuestBookServicesApplication service) {
        this.writerDao = service.getJdbi().onDemand(WriterDao.class);
    }

    @POST
    @Timed
    public Response save(@Valid WriterCreator writerCreator){
        Writer writer = writerDao.insert(writerCreator);
        return Response.created(URI.create(String.valueOf(writer.id))).build();
    }
    
    @GET
    @Timed
    @Path("/{id}")
    public Response get(@PathParam("id")LongParam id) {
        return Response.ok(writerDao.get(id.get())).build();
    }
}
