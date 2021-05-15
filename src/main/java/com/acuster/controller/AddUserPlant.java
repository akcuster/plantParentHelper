package com.acuster.controller;

import com.acuster.entity.Plant;
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
import java.time.LocalDate;
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
    private GenericDao<User> userDao = new GenericDao<>(User.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String outputMessage = null;

        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("userId");
        logger.info("User ID: " + id);
        User user = (User) session.getAttribute("user");
        logger.info("User: " + user);
        String plantName = request.getParameter("plantName");
        if (plantName == null) {
            plantName = (String) session.getAttribute("plantName");
        }
        session.setAttribute("plantName", plantName);
        logger.info("Plant Name: " + plantName);
        String dateAdopted = request.getParameter("dateAdopted");
        if (dateAdopted == null) {
            dateAdopted = (String) session.getAttribute("dateAdopted");
        }
        session.setAttribute("dateAdopted", dateAdopted);
        logger.info("Date Adopted: " + dateAdopted);
        String plantId = request.getParameter("plantid");
        if (plantId == null) {
            plantId = (String) session.getAttribute("plantId");
        }
        session.setAttribute("plantId", plantId);
        logger.info("Plant ID: " + plantId);

        // Check if user is logged in
        if (!checkIfUserLoggedIn(user, id)) {
            logger.error("User not logged in");
            //TODO change to login error page
            response.sendRedirect("index.jsp");
            return;
        }

        // Make sure a plant was confirmed to add
        if (plantId != null) {
            outputMessage = addPlantToCollection(plantId, user, dateAdopted);
        }

        // Redirect to add plant (or error) page searching for or confirming a plant to add fails
        if (request.getParameter("submit") == null && plantId == null) {
            logger.error("Search failed");
            //TODO redirect to error page
            response.sendRedirect("add-plant.jsp");
            return;
        }

        List<Plant> plants = searchPlants(plantName);

        // Set output message if no plants are found
        if (plantName == null || plants.isEmpty()) {
            outputMessage = "Sorry, no plants were found.";
            logger.info("No plants found");
        }

        // Set output message and plants into session
        request.setAttribute("outputMessage", outputMessage);
        session.setAttribute("plants", plants);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/add-plant.jsp");
        dispatcher.forward(request, response);
    }

    public boolean checkIfUserLoggedIn(User user, int id) {
        boolean loggedIn;
        loggedIn = (user != null && id != 0);

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

    public String addPlantToCollection(String plantId, User user, String dateAdopted) {
        Plant plant = plantDao.getById(Integer.parseInt(plantId));
        UserPlant newPlant = user.addPlant(plant, LocalDate.parse(dateAdopted));
        userDao.saveOrUpdate(user);
        String outputMessage = "Problem adding plant to collection. Try again";
        if (newPlant != null) {
            outputMessage = "Plant added successfully";
        }
        return outputMessage;
    }


}

