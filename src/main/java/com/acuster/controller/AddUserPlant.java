package com.acuster.controller;

import com.acuster.entity.Plant;
import com.acuster.entity.User;
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
import java.util.ArrayList;
import java.util.List;

/**
 * a servlet to add a plant to a user's collection
 * @author acuster
 */

@WebServlet(
        urlPatterns = {"/add-plant"}
)

public class AddUserPlant extends HttpServlet {
    final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDao<Plant> plantDao = new GenericDao<>(Plant.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("userId");
        User user = (User) session.getAttribute("user");
        String outputMessage = "";

        logger.info("User: " + user);

        if (!checkIfUserLoggedIn(user, id)) {
            logger.error("User not logged in");
            //TODO change to login error page
            response.sendRedirect("index.jsp");
        }

        if (!request.getParameter("submit").equals("addPlant")) {
            logger.error("Search failed");
            //TODO redirect to error page
            response.sendRedirect("add-plant.jsp");
        }
        String plantName = request.getParameter("plantName");

        if (plantName == null) {
            outputMessage = "Sorry, no plants were found.";
            logger.info("No plants found");
        }
        List<Plant> plants = searchPlants(plantName);
        session.setAttribute("outputMessage", outputMessage);
        session.setAttribute("plants", plants);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/results.jsp");
        dispatcher.forward(request, response);
    }

    public boolean checkIfUserLoggedIn(User user, int id) {
        boolean loggedIn = false;
        loggedIn = user != null && id != 0;

        return loggedIn;
    }

    public List<Plant> searchPlants(String plantName) {
        List<Plant> plants;
        plants = plantDao.getByPropertyLike("plantName", plantName);
        if (plants.isEmpty()) {
            plants = null;
        }
        return plants;
    }


}

