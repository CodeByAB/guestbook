package se.webstep.microservice.guestbook.resource;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.jersey.params.LongParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.api.CreateGuestbook;
import se.webstep.microservice.guestbook.core.Guestbook;
import se.webstep.microservice.guestbook.jdbi.GuestbookDao;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;


@Path("/guestbook")
@Produces(MediaType.APPLICATION_JSON)
public class GuestbookResource {

    public final GuestbookDao dao;

    public GuestbookResource(MicroServicesApplication service) {
        dao = service.getJdbi().onDemand(GuestbookDao.class);
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") LongParam id) {
        Optional<Guestbook> guestbook = dao.get(id.get());
        if (guestbook.isPresent()) {
            return Response.ok(guestbook.get()).build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response create(@Valid CreateGuestbook createGuestbook) {
        return Response.created(URI.create(String.valueOf(dao.save(createGuestbook)))).build();
    }

    @GET
    @Path("/list")
    public Response all() {
        return Response.ok(ImmutableMap.of("list", dao.list())).build();
    }

}
