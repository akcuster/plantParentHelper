package com.acuster.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(
        urlPatterns = {"/user-profile"}
)

public class UserProfile extends HttpServlet {

    final Logger logger = LogManager.getLogger(this.getClass());
}
