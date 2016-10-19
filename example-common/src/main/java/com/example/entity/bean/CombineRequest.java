package com.example.entity.bean;

import java.util.Map;

/**
 * Created by binglin on 2016/10/17.
 */
public class CombineRequest {

    private String url;

    private String method;

    private Map param;

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

    public Map getParam() {
        return param;
    }

    public void setParam(Map param) {
        this.param = param;
    }
}
