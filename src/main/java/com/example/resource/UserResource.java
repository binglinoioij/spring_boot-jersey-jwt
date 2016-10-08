package com.example.resource;

import com.example.entity.mongo.User;
import com.example.entity.paramBean.UserBean;
import com.example.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by binglin on 2016/9/17.
 */

@Path("/users")
@PermitAll
@RolesAllowed("user")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    public Response list() {
        List<User> all = userService.findAll();
        return Response.ok().entity(all).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("bean")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response testGet(@BeanParam UserBean userBean) {
        return Response.ok().entity(userBean).type(MediaType.APPLICATION_JSON).build();
    }
}
