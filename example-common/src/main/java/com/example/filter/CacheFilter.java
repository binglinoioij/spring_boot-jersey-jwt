package com.example.filter;

import com.example.annotation.Cache;
import com.example.util.JsonUtils;

import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Created by binglin on 2016/10/11.
 */
@Provider
@Priority(Priorities.USER)
public class CacheFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        Annotation[] entityAnnotations = containerResponseContext.getEntityAnnotations();
        String method = containerRequestContext.getMethod();
        if ("GET".equals(method) || "HEAD".equals(method)) {
            Stream.of(entityAnnotations).forEach(x -> {
                if (x.annotationType().equals(Cache.class)) {
                    String ifNoneMatch = containerRequestContext.getHeaders().getFirst("If-None-Match");
                    if (null == containerResponseContext.getEntity()) {
                        return;
                    }
                    String eTag = DigestUtils.md5DigestAsHex(JsonUtils.toJsonString(containerResponseContext.getEntity()).getBytes());
                    if (eTag.equals(ifNoneMatch)) {
                        containerResponseContext.setEntity(null);
                        containerResponseContext.setStatus(Response.Status.NOT_MODIFIED.getStatusCode());
                    } else {
                        containerResponseContext.getHeaders().add("Cache-Control", "no-transform, max-age=86400");
                        containerResponseContext.getHeaders().add("Etag", eTag);
                    }
                }
            });

        }
    }
}
