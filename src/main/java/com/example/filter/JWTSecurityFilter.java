/**
 * Created by Philip A Senger on November 10, 2015
 */
package com.example.filter;


import com.example.entity.mongo.User;
import com.example.exception.EntityNotFoundException;
import com.example.repository.UserRepository;
import com.example.util.TokenUtil;

import org.glassfish.jersey.server.ContainerRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * https://simplapi.wordpress.com/2013/01/24/jersey-jax-rs-implements-a-http-basic-auth-decoder/
 */

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTSecurityFilter implements ContainerRequestFilter {

    final static Logger logger = Logger.getLogger(JWTSecurityFilter.class.getName());

    /**
     * HK2 Injection.
     */
    @Context
    UserRepository dao;

    String key = "qwertyuiop";

    @Context
    SecurityContext securityContext;

    @Context
    javax.inject.Provider<UriInfo> uriInfo;

    public static String extractJwtTokenFromAuthorizationHeader(String auth) {
        //Replacing "Bearer Token" to "Token" directly
        return auth.replaceFirst("[B|b][E|e][A|a][R|r][E|e][R|r] ", "").replace(" ", "");
    }

    /*
    get application.wadl和application.wadl/xsd0.xsd不做拦截
    wadl Web 应用程序描述语言
    post 方法如果是 申请token 也不做拦截
    当然还有其它不需要拦截的url，应当做 正则匹配，这部分用户是普通 的未登录用户
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        logger.info("进入 filter");
        String method = requestContext.getMethod().toLowerCase();
        String path = ((ContainerRequest) requestContext).getPath(true).toLowerCase();

        if (("get".equals(method) && ("application.wadl".equals(path) || "application.wadl/xsd0.xsd".equals(path)))
                || ("post".equals(method) && "authentication".equals(path))) {
            // pass through the filter.
            requestContext.setSecurityContext(new SecurityContextAuthorizer(uriInfo, () -> "anonymous", new String[]{"anonymous"}));
            return;
        }

        String authorizationHeader = ((ContainerRequest) requestContext).getHeaderString("authorization");
        if (authorizationHeader == null) {
            requestContext.setSecurityContext(new SecurityContextAuthorizer(uriInfo, () -> "anonymous", new String[]{"anonymous"}));
            return;
        }

        String strToken = extractJwtTokenFromAuthorizationHeader(authorizationHeader);
        if (TokenUtil.isValid(strToken, key)) {
            String name = TokenUtil.getName(strToken, key);
            String[] roles = TokenUtil.getRoles(strToken, key);
            int version = TokenUtil.getVersion(strToken, key);
            if (name != null && roles.length != 0 && version != -1) {
                User user = null;
                try {
                    user = dao.findByUserName(name);
                } catch (EntityNotFoundException e) {
                    logger.info("User not found " + name);
                }
                if (user != null) {
                    if (user.getVersion() == version && user.getRoles() != null &&
                            Arrays.asList(user.getRoles()).containsAll(Arrays.asList(roles))) {
                        requestContext.setSecurityContext(new SecurityContextAuthorizer(uriInfo, () -> name, roles));
                        logger.info("校验合法");
                        return;
                    } else {
                        logger.info("Version or roles did not match the token");
                    }
                } else {
                    logger.info("User not found");
                }
            } else {
                logger.info("name, roles or version missing from token");
            }
        } else {
            logger.info("token is invalid");
        }
        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }
}
