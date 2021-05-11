package com.acuster.controller;

import com.acuster.entity.User;
import com.acuster.entity.UserPlant;
import com.acuster.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebServlet(
        urlPatterns = {"/user-profile"}
)

public class UserProfile extends HttpServlet {

    final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("userId");
        User user = (User) session.getAttribute("user");
        logger.info("User: " + user);

        if (user != null && id != 0) {
            request.setAttribute("user", user);

        } else {
            //TODO redirect to login error page
            logger.error("There was a problem logging in...");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user-profile.jsp");
        dispatcher.forward(request, response);
    }

}
