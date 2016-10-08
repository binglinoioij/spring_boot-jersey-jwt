package com.example.entity.paramBean;

import java.io.Serializable;

import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;

/**
 * Created by binglin on 2016/9/17.
 */

public class UserBean implements Serializable {

    @QueryParam("name")
    private String name;

    @QueryParam("count")
    private int count;

    @HeaderParam("Content-Type")
    private String contentType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
