package com.acuster.controller;

import com.acuster.utilities.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;


/**
 * a servlet that redirects to amazon cognito hosted UI for login
 * @author acuster
 */

@WebServlet(
        urlPatterns = {"/log-in"}
)

public class Login extends HttpServlet implements PropertiesLoader {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final Logger logger = LogManager.getLogger(this.getClass());

        Properties awsCognito = new Properties();

        try {
            awsCognito = loadProperties("/awsCognito.properties");
        } catch (Exception e) {
            logger.error("failed to load properties" + e);
        }

        String redirect = awsCognito.getProperty("redirect");
        String responseType = "code";
        String clientID = awsCognito.getProperty("clientID");
        String logInURL = "https://plant-collector.auth.us-east-2.amazoncognito.com/login?client_id=" + clientID +
                "&response_type=" + responseType + "&redirect_uri=" + redirect + "";

        response.sendRedirect(logInURL);

    }


}
