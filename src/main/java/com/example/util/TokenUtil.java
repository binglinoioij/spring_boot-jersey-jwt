/**
 * Created by Philip A Senger on November 10, 2015
 */
package com.example.util;


import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtil {

    public static String getJWTString(String username, String[] roles, int version, Long id, Date expires, String key) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        if (username == null) {
            throw new NullPointerException("null username is illegal");
        }
        if (roles == null) {
            throw new NullPointerException("null roles are illegal");
        }
        if (expires == null) {
            throw new NullPointerException("null expires is illegal");
        }
        if (key == null) {
            throw new NullPointerException("null key is illegal");
        }
        if (id == null) {
            throw new NullPointerException("null id is illegal");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        String jwtString = Jwts
                .builder()
                .setIssuer("Jersey-Security-Basic")
                .setSubject(username + "#" + id.toString())
                .setAudience(StringUtils.join(Arrays.asList(roles), ","))
                .setExpiration(expires)
                .setIssuedAt(new Date())
                .setId(String.valueOf(version))
                .signWith(signatureAlgorithm, "qwertyuiop")
                .compact();
        return jwtString;
    }

    public static boolean isValid(String token, String key) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token.trim());
//            Jwts.parser().setSigningKey(key).parseClaimsJws(token.trim());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isValidByStringKey(String token, String key) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token.trim());
//            Jwts.parser().setSigningKey(key).parseClaimsJws(token.trim());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getName(String jwsToken, String key) {
        if (isValid(jwsToken, key)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);

//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
            return claimsJws.getBody().getSubject().split("#")[0];
        }
        return null;
    }

    public static String getId(String jwsToken, String key) {
        if (isValid(jwsToken, key)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);

//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
            return claimsJws.getBody().getSubject().split("#")[1];
        }
        return null;
    }

    public static String[] getRoles(String jwsToken, String key) {
        if (isValid(jwsToken, key)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
            return claimsJws.getBody().getAudience().split(",");
        }
        return new String[]{};
    }

    public static int getVersion(String jwsToken, String key) {
        if (isValid(jwsToken, key)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
            return Integer.parseInt(claimsJws.getBody().getId());
        }
        return -1;
    }

}
