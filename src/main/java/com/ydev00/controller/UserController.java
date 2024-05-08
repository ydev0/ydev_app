package com.ydev00.controller;

import spark.Route;
import static spark.Spark.*;

import java.sql.Connection;
import com.google.gson.Gson;

import com.ydev00.util.Firebase;
import com.ydev00.model.User;
import com.ydev00.dao.UserDAO;

import org.eclipse.jetty.http.HttpStatus;

public class UserController {
  private User user;
  private Connection dbConn;
  private Gson gson;
  private Firebase fb;

  public UserController() {}

  public UserController(Connection dbConn, Firebase fb) {
    this.dbConn = dbConn;
    this.gson = new Gson();
    this.fb = fb;
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
    user = userDAO.getByEmail("email@email.com", "password");

    // add picture here

    System.out.println("user logged in: "+ user.getName());

    response.status(HttpStatus.OK_200);

    String resp = "carlos"; //WARN change this

    return resp;
  };
}
