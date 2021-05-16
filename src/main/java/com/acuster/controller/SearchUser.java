package com.acuster.controller;

import com.acuster.entity.User;
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

//TODO add functionality to add friends, which will use this user search
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
        List<User> users = new ArrayList<>();

        if (request.getParameter("submit").equals("search")) {
             users = userDao.getByPropertyEqual("userName", request.getParameter("userName"));
            //TODO add the ability to search by name as well
            //TODO add the ability to search for all users that have a specific plant
        } else {
            //TODO output a message that no users were found

        }

        request.setAttribute("users", users);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/results.jsp");
        dispatcher.forward(request, response);
    }


}
