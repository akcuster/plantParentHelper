package com.acuster.controller;

import com.acuster.entity.Plant;
import com.acuster.entity.User;
import com.acuster.entity.UserPlant;
import com.acuster.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * a servlet to search for a user
 * @author acuster
 */

@WebServlet(
        urlPatterns = {"/search-user"}
)

public class SearchUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        GenericDao<User> userDao = new GenericDao<>(User.class);
        List<User> users;

        if (request.getParameter("submit").equals("search")) {
             users = userDao.getByPropertyEqual("userName", request.getParameter("userName"));

        } else {
            users = userDao.getAll();

        }

        request.setAttribute("users", users);
//        request.setAttribute("plants", getThePlants(users));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/results.jsp");
        dispatcher.forward(request, response);
    }

//    public List<Plant> getThePlants(List<User> users) {
////        List<Plant> plants = new ArrayList<Plant>();
//        for (User user : users) {
//            Set<UserPlant> userPlants = user.getPlants();
////            for (UserPlant userPlant : userPlants) {
////                plants.add(userPlant.getPlant());
////            }
//        }
//
////        return plants;
//
//    }


}
