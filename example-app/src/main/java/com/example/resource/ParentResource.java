package com.example.resource;

import javax.ws.rs.Path;

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
