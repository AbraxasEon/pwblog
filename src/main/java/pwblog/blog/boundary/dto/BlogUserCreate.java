/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwblog.blog.boundary.dto;

import pwblog.blog.boundary.adapters.RoleTypeAdapter;
import pwblog.blog.entity.BlogUser;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author indra
 */
public class BlogUserCreate {

    @NotEmpty
    public String fname;

    @NotEmpty
    public String lname;

    @NotEmpty
    public String email;

    @NotEmpty
    public String pwd;

    @NotEmpty
    @JsonbTypeAdapter(RoleTypeAdapter.class)
    public BlogUser.Role role;

}
