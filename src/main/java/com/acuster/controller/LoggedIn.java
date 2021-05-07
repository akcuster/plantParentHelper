package com.acuster.controller;

import com.acuster.entity.AwsTokenResponse;
import com.acuster.entity.User;
import com.acuster.persistence.AwsAuthorizationEndpoint;
import com.acuster.persistence.GenericDao;
import com.acuster.utilities.AwsCognitoRSAKeyProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;



/**
 * a servlet to search for a user
 * @author acuster
 */

@WebServlet(
        urlPatterns = {"/logged-in"}
)

public class LoggedIn extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final Logger logger = LogManager.getLogger(this.getClass());

        String awsCognitoRegion = "us-east-2";
        String awsUserPoolsId = "us-east-2_RhJRzXj6o";
        String authCode = request.getParameter("code");


        AwsAuthorizationEndpoint authEndpoint = new AwsAuthorizationEndpoint();
        String idToken = authEndpoint.postToken(authCode);
        //AwsTokenResponse tokenResponse = new AwsTokenResponse();

        //String idToken = tokenResponse.getIdToken();
        logger.error("id token: " + idToken);
        //String idToken = request.getParameter("id_token");
        DecodedJWT jwt = null;

        RSAKeyProvider keyProvider = new AwsCognitoRSAKeyProvider(awsCognitoRegion, awsUserPoolsId);

        try {
            Algorithm algorithm = Algorithm.RSA256(keyProvider);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();

            jwt = jwtVerifier.verify(idToken);
        } catch (JWTVerificationException e) {
           logger.error("Issue with verifying token " + e);
        } catch (Exception e) {
            logger.error("There was an error..." + e);
        }

//       try {
//           jwt = JWT.decode(idToken);
//       } catch (JWTDecodeException e) {
//          logger.error("Invalid token: " + e);
//       }

       if (jwt != null) {
           String userName = jwt.getClaim("cognito:username").asString();
           String firstName = jwt.getClaim("given_name").asString();
           String lastName = jwt.getClaim("family_name").asString();
           String email = jwt.getClaim("email").asString();

           logger.info(userName + firstName + lastName + email);
       } else {
           logger.error("JWT is null" + jwt);
       }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }


}

