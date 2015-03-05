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
import java.util.stream.Collectors;


@Path("/guestbook")
@Produces(MediaType.APPLICATION_JSON)
public class GuestbookResource {

    public final MicroServicesApplication service;

    public GuestbookResource(MicroServicesApplication service) {
        this.service = service;
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") LongParam id) {
        Optional<Guestbook> guestbook = service.getJdbi().onDemand(GuestbookDao.class).get(id.get());
        if (guestbook.isPresent()) {
            return Response.ok(guestbook.get()).build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response create(@Valid CreateGuestbook createGuestbook) {
        return Response.created(URI.create(String.valueOf(service.getJdbi()
                .onDemand(GuestbookDao.class)
                .save(createGuestbook)))).build();
    }

    @PUT
    @Path("{id}/open")
    public Response open(@PathParam("id") LongParam id) {
        GuestbookDao guestbookDao = service.getJdbi().onDemand(GuestbookDao.class);
        Optional<Guestbook> guestbook = guestbookDao.get(id.get());
        if (guestbook.isPresent()) {
            guestbookDao.changeStatus(guestbook.get().id, Guestbook.Type.OPEN);
            return Response.ok().build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id}/close")
    public Response close(@PathParam("id") LongParam id) {
        Optional<Guestbook> guestbook = service.getJdbi().onDemand(GuestbookDao.class).get(id.get());
        if (guestbook.isPresent()) {
            service.getJdbi().onDemand(GuestbookDao.class).changeStatus(guestbook.get().id, Guestbook.Type.CLOSED);
            return Response.ok().build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/list/open")
    public Response allOpen() {
        return Response.ok(ImmutableMap.of("list", service.getJdbi().onDemand(GuestbookDao.class)
                .list()
                .stream()
                .filter(s -> s.status == Guestbook.Type.OPEN)
                .collect(Collectors.toList()))).build();
    }

    @GET
    @Path("/list")
    public Response all() {
        return Response.ok(ImmutableMap.of("list", service.getJdbi().onDemand(GuestbookDao.class).list())).build();
    }

}
