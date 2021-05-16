package com.acuster.controller;

import com.acuster.entity.User;
import com.acuster.persistence.GenericDao;
import com.acuster.utilities.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * a servlet to log a user out of aws cognito
 * @author acuster
 */

@WebServlet(
        urlPatterns = {"/log-out"}
)

public class LogOut extends HttpServlet implements PropertiesLoader {

    final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDao<User> userDao = new GenericDao<>(User.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext context = getServletContext();
        Properties awsCognito = (Properties) context.getAttribute("awsCognitoProperties");

        String clientID = awsCognito.getProperty("clientID");
        String redirect = awsCognito.getProperty("localLogoutRedirect");
        String url = "https://plant-collector.auth.us-east-2.amazoncognito.com/logout?client_id=" + clientID
                + "&logout_uri=" + redirect + "";

        response.sendRedirect(url);
    }
}
