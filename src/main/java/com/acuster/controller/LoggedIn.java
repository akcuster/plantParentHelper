package com.acuster.controller;

import com.acuster.entity.User;
import com.acuster.persistence.AwsAuthorizationEndpoint;
import com.acuster.persistence.GenericDao;
import com.acuster.utilities.AwsCognitoRSAKeyProvider;
import com.acuster.utilities.PropertiesLoader;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;


/**
 * a servlet to authenticate a user after they log in using AWS cognito
 *
 * @author acuster
 */
@WebServlet(
        urlPatterns = {"/logged-in"}
)

public class LoggedIn extends HttpServlet implements PropertiesLoader {

    final Logger logger = LogManager.getLogger(this.getClass());
    final GenericDao<User> userDao = new GenericDao<>(User.class);
    private String url;
    private String outputMessage;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
        Properties awsCognito = (Properties) context.getAttribute("awsCognitoProperties");

        String awsCognitoRegion = awsCognito.getProperty("region");
        String awsUserPoolsId = awsCognito.getProperty("userPoolID");
        String authCode = request.getParameter("code");

        String jsonWebToken;
        DecodedJWT decodedJwt;
        String userName;
        String firstName;
        String lastName;
        String email;
        String birthdate;

        // Trade in user authorization code for a json web token
        jsonWebToken = getToken(awsCognito, authCode);
        logger.info("id token: " + jsonWebToken);

        // Decode and verify the json web token
        decodedJwt = decodeJsonWebToken(awsCognitoRegion, awsUserPoolsId, jsonWebToken);

        // If the identification token is verified, get the user's information and set them into the session
        if (decodedJwt != null) {
            userName = decodedJwt.getClaim("cognito:username").asString();
            firstName = decodedJwt.getClaim("given_name").asString();
            lastName = decodedJwt.getClaim("family_name").asString();
            email = decodedJwt.getClaim("email").asString();
            birthdate = decodedJwt.getClaim("birthdate").asString();
            logger.info(userName + firstName + lastName + email + birthdate);

            setUserIntoSession(session, userName, firstName, lastName, birthdate);
            logger.info("User set into session: " + session.getAttribute("user"));

            url = "user-profile";

        } else {
            //TODO redirect to error page
            logger.error("JWT is null" + decodedJwt);
            url = "error-success.jsp";
            outputMessage = "There was an error, please try again";
        }

        request.setAttribute("outputMessage", outputMessage);

        // Forward to the servlet that handle's the user profile page
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Check if the user already exists, if not, call createNewUser. Set user into the session and set the forward url
     * @param session the session
     * @param userName the username
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param birthdate the user's date of birth
     */
    private void setUserIntoSession(HttpSession session, String userName, String firstName, String lastName, String birthdate) {
        List<User> users;
        int id;
        User user;
        // Check if the user exists in the database, if not, create new user
        users = userDao.getByPropertyEqual("userName", userName);

        if (users == null || users.isEmpty()) {
            user = createNewUser(userName, firstName, lastName, birthdate);
            id = user.getId();
        } else {
            id = users.get(0).getId();
            user = users.get(0);
        }
        // Add the user to the session
        session.setAttribute("userId", id);
        session.setAttribute("user", user);

        url = "user-profile";
    }

    /**
     * Create a new user with the information from the id token
     * @param userName the username
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param birthdate the user's date of birth
     * @return the user object
     */
    private User createNewUser(String userName, String firstName, String lastName, String birthdate) {

        User user = new User(userName, firstName, lastName, LocalDate.parse(birthdate));
        int id = userDao.insert(user);

        if (id != 0) {
           logger.info("Successfully created new user");
        } else {
           logger.error("Failed to insert user");
           url = "error-success.jsp";
           outputMessage = "Failed to create new user, please try again";
        }
        return user;
    }

    /**
     * Instantiate a new RSAKeyProvider to get a json web key using the aws user pools id. Use the key to decode the json
     * web token
     * @param awsCognitoRegion the region the application is in
     * @param awsUserPoolsId the application user pools id
     * @param jsonWebToken the json web token from the aws authentication end point
     * @return the decoded json web token
     */
    private DecodedJWT decodeJsonWebToken(String awsCognitoRegion, String awsUserPoolsId, String jsonWebToken) {
        // Gets a key to check the id token against to verify the user's authentication
        RSAKeyProvider keyProvider = new AwsCognitoRSAKeyProvider(awsCognitoRegion, awsUserPoolsId);
        DecodedJWT decodedJwt = null;
        try {
            Algorithm algorithm = Algorithm.RSA256(keyProvider);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();

            decodedJwt = jwtVerifier.verify(jsonWebToken);
        } catch (JWTVerificationException e) {
           logger.error("Issue with verifying token " + e);
           outputMessage = "There was an error, please try logging in again";
           url = "error-success.jsp";
        } catch (Exception e) {
            logger.error("There was an error..." + e);
        }
        return decodedJwt;
    }

    /**
     * Send a post request with the user's authorization code to the AWS authorization endpoint and receive a json web token in response
     * @param awsCognito the properties loader for aws cognito properties
     * @param authCode the user's authorization code
     * @return a json web token with the user information
     */
    private String getToken(Properties awsCognito, String authCode) {
        String jsonWebToken;
        // Instantiate a new instance of AwsAuthorizationEndpoint to handle the AWS endpoint to trade the authorization code for a token
        AwsAuthorizationEndpoint authEndpoint = new AwsAuthorizationEndpoint();
        jsonWebToken = authEndpoint.postToken(authCode, awsCognito);
        return jsonWebToken;
    }


}

