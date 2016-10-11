package com.example;

import com.example.config.JerseyConfig;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.security.Key;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@SpringBootApplication
public class Jersey2Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new Jersey2Application()
                .configure(new SpringApplicationBuilder(Jersey2Application.class))
                .run(args);
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
        return registration;
    }

    @Bean
    public Key key() {
        SecretKey secretKey = MacProvider.generateKey(SignatureAlgorithm.HS512);
        return secretKey;
    }
}
