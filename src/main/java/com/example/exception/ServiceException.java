package com.example.exception;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by binglin on 2016/9/16.
 */
public class ServiceException extends WebApplicationException {

    private Integer errorCode;

    private String message;

    public ServiceException() {
        super(Response.Status.NOT_FOUND);
    }

    public ServiceException(String message) {
        super(Response.status(Response.Status.NOT_FOUND).
                entity(message).type("text/plain").build());
    }

    public ServiceException(Integer errorCode, String message) {
//        super(Response.status(Response.Status.NOT_FOUND).
//                entity(new Object[]{errorCode, message}).type(MediaType.APPLICATION_JSON).build());
        this.errorCode = errorCode;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
