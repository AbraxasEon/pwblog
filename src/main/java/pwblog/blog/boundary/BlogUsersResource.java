package pwblog.blog.boundary;

import pwblog.blog.boundary.dto.BlogUserCreate;
import pwblog.blog.control.BlogUserStore;
import pwblog.blog.entity.BlogUser;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author indra
 */
@DenyAll
@Path("/users")
public class BlogUsersResource {

    @Context
    private ResourceContext resource;
    
    @Context
    private UriInfo uriInfo;

    @Inject
    private BlogUserStore store;
    
    @Context
    SecurityContext securityCtx;

    @Inject
    JsonWebToken jwt;

    @PostConstruct
    public void init() {
        System.out.println(uriInfo.getPath());
        System.out.println(uriInfo.getBaseUri());
        System.out.println(uriInfo.getAbsolutePath());
    }

    @RolesAllowed({"ADMIN"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BlogUser> searchAllUsers() {
        return store.findAllUsers();
    }

    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid BlogUserCreate u) {
        BlogUser created = store.createUsr(new BlogUser(u));
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }
    
    @RolesAllowed({"ADMIN", "USER"})
    @Path("{userId}")
    public BlogUserResource search(@PathParam("userId") Long id) {
        boolean isUserRole = securityCtx.isUserInRole(BlogUser.Role.USER.name()); // ritorna vero se il ruolo contenuto nel token ?? USER
        if (isUserRole && (jwt == null || jwt.getSubject()== null || Long.parseLong(jwt.getSubject()) != id)) { // jwt.getSubject ritorna l'id dello user
            throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity("Access forbidden: role not allowed").build());
        }
        BlogUserResource sub = resource.getResource(BlogUserResource.class);
        sub.setUserId(id);
        return sub;
    }

}
