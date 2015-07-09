package se.webstep.microservice.guestbook.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ResourceTest implements TestRule {

    private final ResourceTestRule resources;

    public ResourceTest(Object resource, Object... resources) {
        this(ResourceTestRule.builder(), resource, resources);
    }

    public ResourceTest(ResourceTestRule.Builder builder, Object resource, Object... resources) {
        ObjectMapper objectMapper = JsonSupport.objectMapper();
        builder.setMapper(objectMapper);

        builder.addResource(resource);
        for (Object r : resources)
            builder.addResource(r);

        this.resources = builder.build();
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return resources.apply(base, description);
    }

    @SuppressWarnings("unused")
    public ResourceTestRule getUnderlyingRule() {
        return resources;
    }

    @SuppressWarnings("unused")
    public Client client() {
        return resources.client();
    }

    public Response doPost(String resource,Entity<?> payload) {
        return resources.client()
                .target(resource)
                .request()
                .post(payload, Response.class);
    }

    @SuppressWarnings("unused")
    public Response doPut(String resource, Entity<?> payload) {
        return resources.client()
                .target(resource)
                .request()
                .put(payload, Response.class);
    }

    @SuppressWarnings("unused")
    public Response doGet(String resource) {
        return resources.client()
                .target(resource)
                .request()
                .get(Response.class);
    }

    @SuppressWarnings("unused")
    public Response doDelete(String resource) {
        return resources.client()
                .target(resource)
                .request()
                .delete(Response.class);
    }

    @SuppressWarnings("unused")
    public void verifyJsonContentType(Response response) {
        MediaType mediaType = response.getMediaType();

        assertNotNull(mediaType);
        assertEquals("application", mediaType.getType());
        assertEquals("json", mediaType.getSubtype());
        assertEquals("UTF-8", mediaType.getParameters().get("charset"));
    }

    public ErrorResponse getErrorResponse(Response response) {
        return response.readEntity(ErrorResponse.class);
    }

    @SuppressWarnings("unused")
    public ErrorResponse getValidatedErrorResponse(Response response) {
        ErrorResponse errorResponse = getErrorResponse(response);

        assertNotNull("No body in error response", errorResponse);
        assertFalse("No type set in error response", Strings.isNullOrEmpty(errorResponse.type));
        assertFalse("No message set in error response", Strings.isNullOrEmpty(errorResponse.message));

        if (errorResponse.errors != null) {
            for (FieldError error : errorResponse.errors) {
                assertFalse("No field set in field error", Strings.isNullOrEmpty(error.field));
                assertFalse("No code set in field error", Strings.isNullOrEmpty(error.code));
            }
        }

        return errorResponse;
    }

    public static class ErrorResponse {

        @JsonProperty
        public String type;

        @JsonProperty
        public String message;

        @JsonProperty
        public List<FieldError> errors;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

    }

    public static class FieldError {
        @JsonProperty
        public String code;

        @JsonProperty
        public String field;

        @JsonProperty
        public String message;

        @JsonProperty
        public Object value;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

    }

}
