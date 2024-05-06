package com.ydev00.controller;

import spark.Route;
import static spark.Spark.*;

import java.sql.Connection;
import com.google.gson.Gson;

import com.ydev00.model.User;
import com.ydev00.dao.UserDAO;
import org.eclipse.jetty.http.HttpStatus;

public class UserController {
  private User user;
  private Connection dbConn;
  private Gson gson;

  public UserController() {}

  public UserController(Connection dbConn) {
    this.dbConn = dbConn;
  } 

  public Route loginByURL = (request, response) -> {
    response.type("application.json");
    Route route = null;

    String userName = request.url();

    System.out.println(userName);

    user.setUsername(userName);

    return route;
  };


  public Route login = (request, response) -> {
    response.type("application/json");
    
    Route route = null;
    user = gson.fromJson(request.body(), User.class); 

    if (user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      return gson.toJson(new Exception("ERROR" + "Could not login user"));
    }

    UserDAO userDAO = new UserDAO(dbConn);
    user = userDAO.getById(1);

    // add picture here
    response.status(HttpStatus.OK_200);

    return route;
  };
}
