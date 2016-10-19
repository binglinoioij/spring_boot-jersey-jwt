package com.example.entity.bean;

import javax.ws.rs.core.Response;

/**
 * Created by binglin on 2016/10/17.
 */
public class CombineResponse {
    private String url;

    private String method;

    private Object entity;

    private int httpStatus;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
