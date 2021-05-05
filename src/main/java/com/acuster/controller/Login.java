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

public class SearchUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String logInURL = "https://plant-collector.auth.us-east-2.amazoncognito.com/login?client_id=29fon9l71edrci2b398ohtm3tf&response_type=code&scope=aws.cognito.signin.user.admin+email+openid+phone+profile&redirect_uri=http://localhost:8080/PlantCollector_war/logged-in";

        RequestDispatcher dispatcher = request.getRequestDispatcher(logInURL);
        dispatcher.forward(request, response);
    }


}
