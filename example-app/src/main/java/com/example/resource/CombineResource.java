package com.example.resource;

import com.example.entity.bean.CombineRequest;
import com.example.entity.bean.CombineResponse;
import com.example.util.UrlMethodMappingHolder;

import org.glassfish.jersey.server.internal.JerseyResourceContext;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.RuntimeResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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

    private static final Logger logger = LoggerFactory.getLogger(CombineResource.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response combine(List<CombineRequest> combineRequest) {
        if (resourceContext instanceof JerseyResourceContext) {
            final JerseyResourceContext jerseyResourceContext = (JerseyResourceContext) resourceContext;

            List<RuntimeResource> runtimeResources = jerseyResourceContext.getResourceModel().getRuntimeResourceModel().getRuntimeResources();
            //初始化UrlMethodMappingHolder的mapping，记录下每个url下给定的http method对应的ResourceMethod
            if (UrlMethodMappingHolder.isEmpty()) {
                runtimeResources.stream().filter(root -> !(root.getFullPathRegex().startsWith("/application") || root.getFullPathRegex().equals("/")))
                        .forEach(root -> getMethod(root, UrlMethodMappingHolder.mapping));
            }
            Map<CombineRequest, ResourceMethod> methodMap = new HashMap<CombineRequest, ResourceMethod>();
            combineRequest.forEach(x -> {
                methodMap.put(x, UrlMethodMappingHolder.get(x.getUrl(), x.getMethod()));
            });
            LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
            List<CombineResponse> combineResponses = new ArrayList<>(combineRequest.size());
            methodMap.forEach((k, v) -> {
                Class<?> methodClass = v.getInvocable().getHandlingMethod().getDeclaringClass();
                Object resource = jerseyResourceContext.getResource(methodClass);
                Method handlingMethod = v.getInvocable().getHandlingMethod();
                String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(handlingMethod);
                Object result = null;
                CombineResponse combineResponse = new CombineResponse();
                combineResponse.setUrl(k.getUrl());
                combineResponse.setMethod(k.getMethod());
                combineResponse.setHttpStatus(200);
                try {
                    if (parameterNames != null && parameterNames.length > 0) {
                        Object[] objects = new Object[]{};
                        objects = Stream.of(parameterNames).map(p -> k.getParam().get(p)).collect(Collectors.toCollection(LinkedList::new)).toArray(objects);
                        result = handlingMethod.invoke(resource, objects);
                    } else {
                        result = handlingMethod.invoke(resource, null);
                    }
                } catch (Exception e) {
                    logger.error("invoke method error method={} , request={}", new Object[]{handlingMethod, k, e});
                    combineResponse.setHttpStatus(500);
                }
                if (result instanceof Response) {
                    Response response = (Response) result;
                    combineResponse.setEntity(response.getEntity());
                    combineResponse.setHttpStatus(response.getStatus());
                } else {
                    combineResponse.setEntity(result);
                }
                combineResponses.add(combineResponse);
            });
            return Response.ok().entity(combineResponses).build();
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
