package com.example.resource;

import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by binglin on 2016/9/16.
 */
@Path("/parent")
public class ParentResource {

    @Path("/sub")
    public Class<SubResource> getSubResource() {
//        Resource.Builder builder = Resource.builder();
//        builder.addChildResource(Resource.from(SubResource.class));
//        return builder.build();
        return SubResource.class;
    }
}
