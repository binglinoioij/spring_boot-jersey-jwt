package com.example.entity.mongo;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Created by binglin on 2016/9/15.
 */

@Document(collection = "user")
public class User {

    @Id
    private Long id;

    @NotNull(message = "Username can not be null")
    @NotEmpty
    private String userName;

    @NotNull(message = "password can not be null")
    @NotEmpty
    private String password;

    private String[] roles;

    private Integer version;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public User(Long id) {
        this.id = id;
    }
}
