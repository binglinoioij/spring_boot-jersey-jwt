package com.example.resource;

import com.example.entity.bean.CombineRequest;
import com.example.entity.bean.CombineResponse;
import com.example.util.UrlMethodMappingHolder;

import org.glassfish.jersey.server.internal.JerseyResourceContext;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.RuntimeResource;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by binglin on 2016/10/17.
 */

@Path("/combine")
@PermitAll
public class CombineResource {

    @Context
    private UriInfo uriInfo;

    @Context
    private ResourceContext resourceContext;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response combine(List<CombineRequest> combineRequest) {
        if (resourceContext instanceof JerseyResourceContext) {
            final JerseyResourceContext jerseyResourceContext = (JerseyResourceContext) resourceContext;

            List<Resource> rootResources = jerseyResourceContext.getResourceModel().getRootResources();
            List<RuntimeResource> runtimeResources = jerseyResourceContext.getResourceModel().getRuntimeResourceModel().getRuntimeResources();
//        runtimeResources.get(7).getChildRuntimeResources().get(0).getFullPathRegex();
            if (UrlMethodMappingHolder.isEmpty()) {
                runtimeResources.stream().filter(root -> !(root.getFullPathRegex().startsWith("/application") || root.getFullPathRegex().equals("/")))
                        .forEach(root -> {
                            getMethod(root, UrlMethodMappingHolder.mapping);
                        });
            }
            ArrayList<ResourceMethod> resourceMethods = combineRequest.stream().map(x -> UrlMethodMappingHolder.get(x.getUrl(), x.getMethod()))
                    .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Object> collect = resourceMethods.stream().filter(x -> x != null).map(x -> {
                Class<?> handlerClass = x.getInvocable().getHandler().getHandlerClass();
                Object resource = jerseyResourceContext.getResource(handlerClass);
                Object invoke = null;
                CombineResponse combineResponse = new CombineResponse();
                try {
                    Collection<Parameter> parameters = x.getInvocable().getHandler().getParameters();
                    invoke = x.getInvocable().getHandlingMethod().invoke(resource, null);
                    if (invoke instanceof Response) {
                        Response response = (Response) invoke;

                        combineResponse.setEntity(response.getEntity());
                        combineResponse.setHttpStatus(response.getStatus());
                    } else {
                        combineResponse.setEntity(invoke);
                    }

                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return combineResponse;
            }).collect(Collectors.toCollection(ArrayList::new));
            return Response.ok().entity(collect).build();
        }
        return null;
    }

    private Map getMethod(RuntimeResource resource, Map<String, ResourceMethod> map) {
        List<ResourceMethod> allMethods = resource.getResourceMethods();
        allMethods.forEach(method -> {
            String regex = resource.getFullPathRegex().endsWith("/") ?
                    resource.getFullPathRegex().substring(0, resource.getFullPathRegex().length() - 1) : resource.getFullPathRegex();
            map.put(regex + "#" + method.getHttpMethod(), method);
        });
        if (null != resource.getChildRuntimeResources()) {
            List<RuntimeResource> childs = resource.getChildRuntimeResources();
            for (int i = 0; i < childs.size(); i++) {
                getMethod(childs.get(i), map);
            }
        }
        return map;

    }
}
