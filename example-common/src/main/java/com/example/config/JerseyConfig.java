package com.example.config;

import com.example.filter.CacheFilter;
import com.example.filter.JWTSecurityFilter;

import org.glassfish.jersey.filter.LoggingFilter;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import javax.inject.Inject;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * Created by binglin on 2016/9/11.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    private static final Logger LOGGER = Logger.getLogger(ResourceConfig.class.getName());

    @Inject
    private Key key;

    public JerseyConfig() {
        // roles security
        register(RolesAllowedDynamicFeature.class);
        // jwt filter
        register(JWTSecurityFilter.class);
        // cache filter
        register(CacheFilter.class);
        // turn on Jackson, Moxy isn't that good of a solution.
        register(JacksonFeature.class);
        //logging feature
        register(new LoggingFeature(Logger.getLogger(JerseyConfig.class.getName()),
                LoggingFeature.Verbosity.PAYLOAD_ANY));

        packages("com.example.resource", "com.example.provider");
        property("jersey.config.beanValidation.enableOutputValidationErrorEntity.server", "true");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "INFO");
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

}
