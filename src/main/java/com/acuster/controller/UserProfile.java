package com.acuster.controller;

import com.acuster.entity.Plant;
import com.acuster.entity.User;
import com.acuster.entity.UserPlant;
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
import java.util.Set;


/**
 * A servlet to handle the output for a user profile.
 */
@WebServlet(
        urlPatterns = {"/user-profile"}
)

public class UserProfile extends HttpServlet {

    /**
     * The Logger.
     */
    final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("userId");
        User user = (User) session.getAttribute("user");
        List<Plant> plants = new ArrayList<>();
        String url = "/error.jsp";
        String outputMessage = "";
        logger.info("User: " + user);
        logger.info("User's Plants: " + user.getPlants());

        // Check if the user is logged in, send them to an error page if not
        if (id != 0) {
            request.setAttribute("user", user);
            plants = getPlants(user);
            url = "/user-profile.jsp";
        } else {
            String errorMessage = "Sorry, you're not logged in";
            request.setAttribute("errorMessage", errorMessage);
            logger.error("There was a problem logging in...");
        }

        // Check if the user has any plants and output a message based on the check
        if (!plants.isEmpty()) {
            session.setAttribute("usersPlants", plants);
            outputMessage = "Your Plants";
        } else {
            outputMessage = "Your Plant Collection is Empty";
        }
        session.setAttribute("outputMessage", outputMessage);

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Gets user's plants.
     *
     * @param user the user
     * @return the plants
     */
    public List<Plant> getPlants(User user) {
        Set<UserPlant> userPlants = user.getPlants();
        List<Plant> plants = new ArrayList<>();
        for (UserPlant userPlant : userPlants) {
            logger.info("user Plant in getPlants forEach: " + userPlant);


            plants.add(userPlant.getPlant());
            logger.info("plants in getPlants forEach: " + plants);
        }

        return plants;
    }

}
