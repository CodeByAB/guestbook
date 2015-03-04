package se.webstep.microservice.guestbook.resource;

import org.apache.commons.io.IOUtils;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;


@Path("/doc")
@Produces(MediaType.TEXT_HTML + ";charset=UTF-8")
public class DocumentationResource {

    private static final String SUPPORTED_IMAGE_MIME_TYPES = "image/png, image/jpeg, image/tiff, image/gif";

    private final String environmentName;

    private final String htmlTemplate;

    public DocumentationResource(String environmentName) {
        this.environmentName = environmentName;
        this.htmlTemplate = getHtmlTemplate();
    }

    @GET
    public Response getIndexDocument() throws IOException {
        return getDocumentation("index");
    }

    @GET
    @Path("/{documentName}")
    public Response getDocumentation(@PathParam("documentName") String documentName) throws IOException {
        // fetch document from resources/doc
        String documentation = getDocumentContents(documentName);
        if (documentation != null) {
            return Response.ok().entity(addToTemplate(documentation)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/images/{imageName}")
    @Produces(SUPPORTED_IMAGE_MIME_TYPES)
    public Response getImage(@PathParam("imageName") String imageName) {
        byte[] imageFile = getImageFile(imageName);
        if (imageFile != null) {
            return Response.ok(imageFile).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private String addToTemplate(String contents) throws IOException {
        return htmlTemplate.replace("#{content}", getAsHtml(contents)).replace("#{title}", environmentName);
    }

    private String getHtmlTemplate() {
        return getFileContents("/doc_template/html_template.html");
    }

    private String getFileContents(String path) {
        InputStream inputStream = this.getClass().getResourceAsStream(path);
        if (inputStream != null) {
            try {
                return IOUtils.toString(inputStream, "UTF-8");
            } catch (IOException e) {
                throw new RuntimeException("Unexpected exception reading file", e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return null;
    }

    private byte[] getImageFile(String path) {
        InputStream inputStream = this.getClass().getResourceAsStream("/doc/images/" + path);
        if (inputStream != null) {
            try {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                throw new RuntimeException("Unexpected exception reading file", e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return null;
    }

    private String getDocumentContents(String documentName) {
        return getFileContents("/doc/" + documentName + ".md");
    }

    private String getAsHtml(String markdown) throws IOException {
        return new PegDownProcessor(Extensions.ALL).markdownToHtml(markdown);
    }
}