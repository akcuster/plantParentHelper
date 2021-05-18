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
 *
 * @author acuster
 */
@WebServlet(
        urlPatterns = {"/add-plant"}
)

public class AddUserPlant extends HttpServlet {
    /**
     * The Logger.
     */
    final Logger logger = LogManager.getLogger(this.getClass());
    final GenericDao<Plant> plantDao = new GenericDao<>(Plant.class);
    final GenericDao<User> userDao = new GenericDao<>(User.class);

    private String url;
    private String outputMessage;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id;
        User user;
        String plantName;
        String dateAdopted;
        String plantId;
        String submitted;

        HttpSession session = request.getSession();

        submitted = request.getParameter("submit");
        logger.info("Submitted: " + submitted);

        id = (int) session.getAttribute("userId");
        logger.info("User ID: " + id);

        user = (User) session.getAttribute("user");
        logger.info("User: " + user);

        plantName = request.getParameter("plantName");
        if (plantName == null) {
            plantName = (String) session.getAttribute("plantName");
        }
        session.setAttribute("plantName", plantName);
        logger.info("Plant Name: " + plantName);

        dateAdopted = request.getParameter("dateAdopted");
        if (dateAdopted == null) {
            dateAdopted = (String) session.getAttribute("dateAdopted");
        }
        session.setAttribute("dateAdopted", dateAdopted);
        logger.info("Date Adopted: " + dateAdopted);

        plantId = request.getParameter("plantid");
        if (plantId == null) {
            plantId = (String) session.getAttribute("plantId");
        }
        session.setAttribute("plantId", plantId);
        logger.info("Plant ID: " + plantId);


        // Check if user is logged in
        checkIfUserLoggedIn(user, id, plantName, plantId, dateAdopted, submitted, session);


        // Set output message and plants into session
        request.setAttribute("outputMessage", outputMessage);


        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Check if user is logged in and return a boolean of true if the user is logged in.
     *
     * @param user the user
     * @param id   the id
     */
    public void checkIfUserLoggedIn(User user, int id, String plantName, String plantId, String dateAdopted, String submitted, HttpSession session) {

        if (user != null && id != 0) {
            directPlants(plantName, plantId, user, dateAdopted, submitted, session);
        } else {
            url = "/error-success.jsp";
            outputMessage = "You must be logged in to view this page";
        }


    }

    public void directPlants(String plantName, String plantId, User user, String dateAdopted, String submitted, HttpSession session) {
        if (plantId == null && submitted != null) {
            searchPlants(plantName, session);
        } else if (plantId != null && submitted == null) {
            addPlantToCollection(plantId, user, dateAdopted, session);
            session.setAttribute("plants", null);
        } else if (plantId == null && submitted == null){
            outputMessage = "Search for a Plant to Add to Your Collection";
            logger.info("First Time");
        } else {
            logger.error("Search failed");
            outputMessage = "There Was an Error, Please Try Again";
        }
        url = "/add-plant.jsp";
    }

    /**
     * Search the database for the plants similar to the plant the user wants to add to their collection.
     *
     * @param plantName the plant name
     * @param session the session
     */
    public void searchPlants(String plantName, HttpSession session) {
        List<Plant> plants;
        plants = plantDao.getByPropertyLike("plantName", plantName);
        if (plants.isEmpty()) {
            //TODO add option to add new plant to database
            outputMessage = "Plant Could Not Be Found";
            session.setAttribute("plantId", null);
        } else {
            outputMessage = "Confirm Plant to Add";
            session.setAttribute("plants", plants);
        }

    }

    /**
     * Add plant to collection. Return output message based on whether or not the plant was found
     *
     * @param plantId     the plant id
     * @param user        the user
     * @param dateAdopted the date adopted
     */
    public void addPlantToCollection(String plantId, User user, String dateAdopted, HttpSession session) {
        Plant plant = plantDao.getById(Integer.parseInt(plantId));
        UserPlant newPlant = user.addPlant(plant, LocalDate.parse(dateAdopted));
        logger.info("New Plant: " + newPlant);

        if (newPlant != null) {
            userDao.saveOrUpdate(user);
            outputMessage = "Plant added successfully";
        } else {
            outputMessage = "Problem adding plant to collection. Try again";
        }
        session.setAttribute("plantId", null);
    }


}

