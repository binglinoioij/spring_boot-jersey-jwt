package com.example.config;

import com.example.filter.CacheFilter;
import com.example.filter.JWTSecurityFilter;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Key;

import javax.crypto.SecretKey;
import javax.inject.Inject;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * Created by binglin on 2016/9/11.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    @Inject
    private Key key;

    public JerseyConfig() {
        register(LoggingFeature.class);
        // roles security
        register(RolesAllowedDynamicFeature.class);
        // jwt filter
        register(JWTSecurityFilter.class);
        register(CacheFilter.class);
        // turn on Jackson, Moxy isn't that good of a solution.
        register(JacksonFeature.class);

        packages("com.example.resource", "com.example.provider");
        property("jersey.config.beanValidation.enableOutputValidationErrorEntity.server", "true");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        // register(HelloWordResourse.class);
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

}
