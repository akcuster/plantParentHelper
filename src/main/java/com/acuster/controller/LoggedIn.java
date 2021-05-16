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

    /**
     * The Logger.
     */
    final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDao<User> userDao = new GenericDao<>(User.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext context = getServletContext();
        Properties awsCognito = (Properties) context.getAttribute("awsCognitoProperties");

        String awsCognitoRegion = awsCognito.getProperty("region");
        String awsUserPoolsId = awsCognito.getProperty("userPoolID");
        String authCode = request.getParameter("code");
        DecodedJWT jwt = null;

        // Instantiate a new instance of AwsAuthorizationEndpoint to handle the AWS endpoint to trade the authorization code for a token
        AwsAuthorizationEndpoint authEndpoint = new AwsAuthorizationEndpoint();
        String idToken = authEndpoint.postToken(authCode, awsCognito);

        logger.info("id token: " + idToken);

        // Gets a key to check the id token against to verify the user's authentication
        RSAKeyProvider keyProvider = new AwsCognitoRSAKeyProvider(awsCognitoRegion, awsUserPoolsId);

        try {
            Algorithm algorithm = Algorithm.RSA256(keyProvider);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();

            jwt = jwtVerifier.verify(idToken);
        } catch (JWTVerificationException e) {
           logger.error("Issue with verifying token " + e);
           //TODO redirect to error page if login fails
        } catch (Exception e) {
            logger.error("There was an error..." + e);
        }

        // If the identification token is verified, get the user's information
        if (jwt != null) {
            String userName = jwt.getClaim("cognito:username").asString();
            String firstName = jwt.getClaim("given_name").asString();
            String lastName = jwt.getClaim("family_name").asString();
            String email = jwt.getClaim("email").asString();
            String birthdate = jwt.getClaim("birthdate").asString();

            logger.info(userName + firstName + lastName + email + birthdate);


            // Check if the user exists in the database, if not, create new user
            List<User> users = userDao.getByPropertyEqual("userName", userName);
            int id = 0;
            User user = null;
            if (users == null || users.isEmpty()) {
                user = new User(userName, firstName, lastName, LocalDate.parse(birthdate));
                id = userDao.insert(user);

                if (id != 0) {
                   logger.info("Successfully created new user");

                } else {
                   logger.error("Failed to insert user");
                   //TODO redirect to error page
                }
            } else {
                id = users.get(0).getId();
                user = users.get(0);
            }
            // Add the user to the session
            HttpSession session = request.getSession();
            session.setAttribute("userId", id);
            session.setAttribute("user", user);

           logger.info("User set into session: " + session.getAttribute("user"));

        } else {
            //TODO redirect to error page
            logger.error("JWT is null" + jwt);
        }

        // Forward to the servlet that handle's the user profile page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user-profile");
        dispatcher.forward(request, response);
    }


}

