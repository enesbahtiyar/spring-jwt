package com.eb.security;

import com.eb.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider
{
    /*
        1. method to generate Token
        2. method to validate Token
        3. method to parse Token(extract username from token)
     */

    //secret key to create token
    private String secretKey = "sboot";

    //token expiration duration
    private long jwtExpiration =  86400000; //24 hours * 60 min * 60 * 1000

    //********* method to generate token **********
    /*

        token should be generated for authenticated /signed user
        to generate/build token we need 3 things
            1. username of authenticatedUse,,,,,,,,,,,,ü,
            2. expire time
            3. secret key
     */

    public String createToken(Authentication authentication)
    {
        UserDetailsImpl authenticatedUser = (UserDetailsImpl) authentication.getPrincipal(); //getPrincipal method returns authenticatedUser

        //generate Token

        return Jwts.builder().
                setSubject(authenticatedUser.getUsername()). //set username of logged in user
                setIssuedAt(new Date()). // the time when token created
                setExpiration(new Date(new Date().getTime() + jwtExpiration)). //time when token will expired
                signWith(SignatureAlgorithm.ES512, secretKey). //encoding method/algorithm with secret key
                compact(); //compact //compress //zip
    }

    //****** method to validate token ************

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); //validating token
            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return false;

    }

    //****** method to parse token **********

    //to extract userName from Token
    public String getUserNameFromToken(String token)
    {
        return Jwts.parser().
                setSigningKey(secretKey).
                parseClaimsJws(token).
                getBody(). //reach out to token body
                getSubject(); //we used SetSubject method to create token, now we are using getSubjectMethod
    }
}
