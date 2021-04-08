/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwblog.blog.boundary;

import pwblog.blog.boundary.dto.BlogUserUpdate;
import pwblog.blog.control.BlogUserStore;
import pwblog.blog.control.CommentStore;
import pwblog.blog.entity.BlogUser;
import pwblog.blog.entity.Comment;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author indra
 */
@RolesAllowed({"ADMIN", "USER"})
public class BlogUserResource {

    @Inject
    private CommentStore commentStore;

    @Inject
    private BlogUserStore store;

    private Long userId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public BlogUser searchUser() {
        BlogUser user = store.findUserById(userId).orElseThrow(() -> new NotFoundException());
        return user;
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BlogUser updateUser(BlogUserUpdate u) {
        BlogUser oldUser = store.findUserById(userId).orElseThrow(() -> new NotFoundException());
        BlogUser newUser = store.updateUsr(oldUser, u);
        return newUser;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser() {
        store.findUserById(userId).orElseThrow(() -> new NotFoundException());
        store.banUsr(userId);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @RolesAllowed({"ADMIN"})
    @PATCH
    public Response banUser() {
        store.findUserById(userId).orElseThrow(() -> new NotFoundException());
        commentStore.findCommentsByUser(userId).orElseThrow(() -> new NotFoundException()).stream().forEach(v -> v.setDeleted(true));
        store.banUsr(userId);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> comments() {
        return commentStore.findCommentsByUser(userId).orElseThrow(() -> new NotFoundException());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
