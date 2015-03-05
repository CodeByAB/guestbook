package se.webstep.microservice.guestbook.resource;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.wordnik.swagger.annotations.*;
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

@Api("Resource is handle guestbooks")
@Path("/guestbook")
@Produces(MediaType.APPLICATION_JSON)
public class GuestbookResource {

    public final MicroServicesApplication service;

    public GuestbookResource(MicroServicesApplication service) {
        this.service = service;
    }

    @ApiOperation("Fetching a gestbook with the given ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guestbook with given ID is returned"),
            @ApiResponse(code = 404, message = "No guestbook found")
    })
    @GET
    @Path("/{id}")
    public Response get(@ApiParam(value = "ID of guestbook", required = true)
                        @PathParam("id") LongParam id) {
        Optional<Guestbook> guestbook = service.getJdbi().onDemand(GuestbookDao.class).get(id.get());
        if (guestbook.isPresent()) {
            return Response.ok(guestbook.get()).build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @ApiOperation("Creating a guestbook")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Guestbook is created")
    })
    @POST
    public Response create(@Valid CreateGuestbook createGuestbook) {
        return Response.created(URI.create(String.valueOf(service.getJdbi()
                .onDemand(GuestbookDao.class)
                .save(createGuestbook)))).build();
    }

    @ApiOperation("Changing status for the guestbook to open")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guestbook has change status to open"),
            @ApiResponse(code = 404, message = "No guestbook found")
    })
    @PUT
    @Path("{id}/open")
    public Response open(@ApiParam(value = "ID of guestbook", required = true)
                         @PathParam("id") LongParam id) {
        GuestbookDao guestbookDao = service.getJdbi().onDemand(GuestbookDao.class);
        Optional<Guestbook> guestbook = guestbookDao.get(id.get());
        if (guestbook.isPresent()) {
            guestbookDao.changeStatus(guestbook.get().id, Guestbook.Type.OPEN);
            return Response.ok().build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @ApiOperation("Changing status for the guestbook to closed")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guestbook has changed status to closed"),
            @ApiResponse(code = 404, message = "No guestbook found")
    })
    @PUT
    @Path("{id}/close")
    public Response close(@ApiParam(value = "ID of guestbook", required = true)
                          @PathParam("id") LongParam id) {
        Optional<Guestbook> guestbook = service.getJdbi().onDemand(GuestbookDao.class).get(id.get());
        if (guestbook.isPresent()) {
            service.getJdbi().onDemand(GuestbookDao.class).changeStatus(guestbook.get().id, Guestbook.Type.CLOSED);
            return Response.ok().build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @ApiOperation("List all OPEN guestbooks")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returning all OPEN guestbooks")
    })
    @GET
    @Path("/list/open")
    public Response allOpen() {
        return Response.ok(ImmutableMap.of("list", service.getJdbi().onDemand(GuestbookDao.class)
                .list()
                .stream()
                .filter(s -> s.status == Guestbook.Type.OPEN)
                .collect(Collectors.toList()))).build();
    }


    @ApiOperation("List all guestbooks")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returning all guestbooks")
    })
    @GET
    @Path("/list")
    public Response all() {
        return Response.ok(ImmutableMap.of("list", service.getJdbi().onDemand(GuestbookDao.class).list())).build();
    }

}
