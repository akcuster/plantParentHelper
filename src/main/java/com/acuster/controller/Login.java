package com.acuster.controller;

import com.acuster.utilities.PropertiesLoader;
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
import java.util.Properties;


/**
 * a servlet that redirects to amazon cognito hosted UI for login
 * @author acuster
 */

@WebServlet(
        urlPatterns = {"/log-in"}
)

public class Login extends HttpServlet implements PropertiesLoader {

    private Properties awsCognito;
    final Logger logger = LogManager.getLogger(this.getClass());

    public void init() throws ServletException {
        try {
            awsCognito = loadProperties("/awsCognito.properties");
        } catch (Exception e) {
            logger.error("failed to load properties" + e);
        }

        ServletContext context = getServletContext();

        context.setAttribute("awsCognitoProperties", awsCognito);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        final Logger logger = LogManager.getLogger(this.getClass());

        String redirect = awsCognito.getProperty("localRedirectUri");
        String responseType = "code";
        String clientID = awsCognito.getProperty("clientID");
        String logInURL = "https://plant-collector.auth.us-east-2.amazoncognito.com/login?client_id=" + clientID +
                "&response_type=" + responseType + "&redirect_uri=" + redirect + "";

        response.sendRedirect(logInURL);

    }


}
