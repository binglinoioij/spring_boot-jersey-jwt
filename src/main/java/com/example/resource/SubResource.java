package com.example.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by binglin on 2016/9/16.
 */
@Path("sub")
public class SubResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "sub1";
    }

}
