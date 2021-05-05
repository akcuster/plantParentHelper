package com.acuster.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * a servlet that redirects to amazon cognito hosted UI for login
 * @author acuster
 */

@WebServlet(
        urlPatterns = {"/log-in"}
)

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String redirect = "http://localhost:8080/PlantCollector_war/logged-in";
        String responseType = "code";
        String clientID = "29fon9l71edrci2b398ohtm3tf";
        String logInURL = "https://plant-collector.auth.us-east-2.amazoncognito.com/login?client_id=" + clientID +
                "&response_type=" + responseType + "&redirect_uri=" + redirect + "";

        response.sendRedirect(logInURL);

    }


}
