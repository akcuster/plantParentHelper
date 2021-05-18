package com.acuster.controller;

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
import java.util.HashMap;
import java.util.Map;
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
    private String url;
    private String outputMessage;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("userId");
        User user = (User) session.getAttribute("user");

        Map<Integer, String> plants;

        logger.info("User: " + user);
        logger.info("User's Plants: " + user.getPlants());

        // Check that a user is logged in and retrieve their plants
        plants = checkIfUserIsLoggedIn(session, id, user);

        // Check that the user's list of plants isn't empty and set the output message accordingly
        checkForPlants(session, plants);

        request.setAttribute("outputMessage", outputMessage);

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Check if a user's list of plants is empty and output a message based on whether or not plants are found
     * @param session the session
     * @param plants the user's plants
     */
    private void checkForPlants(HttpSession session, Map<Integer, String> plants) {
        if (!plants.isEmpty()) {
            session.setAttribute("usersPlants", plants);
            outputMessage = "Your Plants";
        } else {
            outputMessage = "Your Plant Collection is Empty";
        }
    }

    /**
     * Check the session to see if a user is logged in. If so, call the getPlants method, passing in the user. If not,
     * set the output message telling the user they're not logged in and set the url for the error page.
     * @param session the session
     * @param id the user's id
     * @param user the user object
     * @return a list of the user's plants
     */
    private Map<Integer, String> checkIfUserIsLoggedIn(HttpSession session, int id, User user) {
        Map<Integer, String> plants = new HashMap<>();
        // Check if the user is logged in, send them to an error page if not
        if (id != 0) {
            session.setAttribute("user", user);
            session.setAttribute("userId", id);
            plants = getPlants(user);
            url = "/user-profile.jsp";
        } else {
            logger.error("There was a problem logging in...");
            outputMessage = "Sorry, you're not logged in";
            url = "error-success.jsp";
        }
        return plants;
    }

    /**
     * Gets user's plants.
     *
     * @param user the user
     * @return the plants
     */
    public Map<Integer, String> getPlants(User user) {
        Set<UserPlant> userPlants = user.getPlants();
        Map<Integer, String> plants = new HashMap<>();
        for (UserPlant userPlant : userPlants) {
            logger.info("user Plant in getPlants forEach: " + userPlant);


            plants.put(userPlant.getId(), userPlant.getPlant().getPlantName());
            logger.info("plants in getPlants forEach: " + plants);
        }

        return plants;
    }

}
