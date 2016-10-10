/**
 * Created by Philip A Senger on November 10, 2015
 */
package com.example.resource;


import com.example.entity.bean.Token;
import com.example.entity.mongo.User;
import com.example.exception.EntityNotFoundException;
import com.example.repository.UserRepository;
import com.example.util.TokenUtil;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@PermitAll
@Path("/authentication")
public class AuthenticationResource {

    private final static Logger logger = Logger.getLogger(AuthenticationResource.class.getName());

    /**
     * HK2 Injection.
     */
    @Context
    UserRepository dao;

    @Inject
    private StringRedisTemplate stringRedisTemplate;


    String key = "qwertyuiop";

    String redisKey = "USER_{0}_TOKEN";

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {

        Date expiry = getExpiryDate(120);
        User user = authenticate(username, password);


        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        String jwtString = TokenUtil.getJWTString(username, user.getRoles(), user.getVersion(), user.getId(), expiry, key);
        Token token = new Token();
        token.setAuthToken(jwtString);
        token.setExpires(expiry);
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set(MessageFormat.format(redisKey, user.getId().toString()), jwtString, 120, TimeUnit.MINUTES);
        return Response.ok(token).build();
    }

    @Path("refresh")
    @GET
    @Produces("application/json")
    public Response refreshToken(@QueryParam("token") String token) {

        logger.info("token is "+token);
        if (TokenUtil.isValidByStringKey(token, key)) {
            Long id = Long.valueOf(TokenUtil.getId(token, key));
            String tokenInRedis = stringRedisTemplate.opsForValue().get(MessageFormat.format(redisKey, id.toString()));


            if (null == tokenInRedis || !tokenInRedis.equals(token)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            Date expiry = getExpiryDate(120);
            String userName = TokenUtil.getName(token, key);
            String[] roles = TokenUtil.getRoles(token, key);
            Integer version = TokenUtil.getVersion(token, key);
            String jwtString = TokenUtil.getJWTString(userName, roles, version, id, expiry, key);
            Token jwtToken = new Token();
            jwtToken.setAuthToken(jwtString);
            jwtToken.setExpires(expiry);
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
            opsForValue.set(MessageFormat.format(redisKey, id.toString()), jwtString, 120, TimeUnit.MINUTES);
            return Response.ok(jwtToken).build();
        } else {

            logger.info("404");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /**
     * get Expire date in minutes.
     *
     * @param minutes the minutes in the future.
     */

    private Date getExpiryDate(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    private User authenticate(String username, String password) throws NotAuthorizedException {
        // Validate the extracted credentials
        User user = null;
        try {
            user = dao.findByUserName(username);
        } catch (EntityNotFoundException e) {
            logger.info("Invalid username '" + username + "' ");
            throw new NotAuthorizedException("Invalid username '" + username + "' ");
        }
        // we need to actually test the Hash not the password, we should never store the password in the database.
        if (user.getPassword().equals(password)) {
            logger.info("USER AUTHENTICATED");
        } else {
            logger.info("USER NOT AUTHENTICATED");
            throw new NotAuthorizedException("Invalid username or password");
        }
        return user;
    }


}
