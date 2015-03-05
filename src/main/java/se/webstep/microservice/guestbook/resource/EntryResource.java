package se.webstep.microservice.guestbook.resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.dropwizard.jersey.params.LongParam;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.jdbi.EntryDao;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Api("Resource handles Entries")
@Path("/entries/{id}/entry")
@Produces(MediaType.APPLICATION_JSON)
public class EntryResource {

    public final MicroServicesApplication service;

    public EntryResource(MicroServicesApplication service) {
        this.service = service;
    }


    @ApiOperation("Creating a guestbook entry")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry was created")
    })
    @POST
    public Response create( CreateEntry createEntry) {

        return Response.created(URI.create(String.valueOf(service.getJdbi()
                .onDemand(EntryDao.class)
                .save(createEntry)))).build();
    }

    @ApiOperation("Delete a guestbook entry")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry was deleted")
    })
    @DELETE
    public Response delete(@PathParam("id") LongParam id) {
        /*return Response.created(URI.create((String.valueOf(service.getJdbi()
        .onDemand(EntryDao.class)
        .delete(id.))))).build();*/
        return null;
    }
}
