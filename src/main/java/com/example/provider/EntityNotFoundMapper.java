package com.example.provider;

import com.example.exception.ServiceException;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by binglin on 2016/9/16.
 */

@Provider
public class EntityNotFoundMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorCode", e.getErrorCode());
        map.put("msg", e.getMessage());
        return Response.status(404).entity(map).type(MediaType.APPLICATION_JSON).build();

    }
}
