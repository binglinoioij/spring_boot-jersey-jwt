package com.example.resource;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;

import java.util.Collection;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by binglin on 2016/10/9.
 */
@Path("redisson")
@PermitAll
public class RedissonResource {

    @Inject
    private RedissonClient redissonClient;

    @GET
    @Path("keys")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listKeys() {
        RKeys keys = redissonClient.getKeys();
        Collection<String> strings = keys.findKeysByPattern("*");
        return Response.ok().entity(strings).build();
    }
}
