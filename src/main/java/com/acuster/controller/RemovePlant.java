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

/**
 * a servlet to remove a plant from a user's collection
 * @author acuster
 */

@WebServlet(
        urlPatterns = {"/remove-plant"}
)

public class RemovePlant extends HttpServlet {

    final Logger logger = LogManager.getLogger(this.getClass());
    final GenericDao<UserPlant> userPlantDao = new GenericDao<>(UserPlant.class);
    final GenericDao<User> userDao = new GenericDao<>(User.class);
    
    private String outputMessage;
    private String url;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        int userPlantId;
        User user;
        int userId;

        user = (User) session.getAttribute("user");
        logger.info("User: " + user);
        
        userId = (int) session.getAttribute("userId");
        logger.info("User ID: " + userId);

        userPlantId = Integer.parseInt(request.getParameter("userPlantId"));
        logger.info("userPlantId parameter: " + userPlantId);
        
        checkIfUserIsLoggedIn(session, user, userId, userPlantId);

        request.setAttribute("outputMessage", outputMessage);


        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private void removeUserPlant(int userPlantId, User user) {
        UserPlant userPlant;
        userPlant = userPlantDao.getById(userPlantId);
        logger.info("UserPlant: " + userPlant);

        user.removePlant(userPlant);
        userDao.saveOrUpdate(user);
        outputMessage = "Plant Removed Successfully";
        logger.info("UserPlant: " + userPlant);
    }

    private void checkIfUserIsLoggedIn(HttpSession session, User user, int id, int userPlantId) {

        // Check if the user is logged in, send them to an error page if not
        if (id != 0) {
            session.setAttribute("user", user);
            session.setAttribute("userId", id);
            logger.info("User is logged in");
            removeUserPlant(userPlantId, user);
            url = "/error.jsp";

            
        } else {
            logger.error("There was a problem logging in...");
            outputMessage = "Sorry, you're not logged in";
            url = "error.jsp";
        }
    }


}

