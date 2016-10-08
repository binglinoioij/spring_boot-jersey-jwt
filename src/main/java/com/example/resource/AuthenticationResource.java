/**
 * Created by Philip A Senger on November 10, 2015
 */
package com.example.resource;



import com.example.entity.bean.Token;
import com.example.entity.mongo.User;
import com.example.exception.EntityNotFoundException;
import com.example.repository.UserRepository;
import com.example.util.TokenUtil;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    @Context
    Key key;

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {

        Date expiry = getExpiryDate(15);
        User user = authenticate(username, password);

        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        String jwtString = TokenUtil.getJWTString(username, user.getRoles(), user.getVersion(), expiry, key);
        Token token = new Token();
        token.setAuthToken(jwtString);
        token.setExpires(expiry);

        return Response.ok(token).build();
    }

    /**
     * get Expire date in minutes.
     *
     * @param minutes the minutes in the future.
     * @return
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
