package com.ydev00.controller;

import spark.Route;
import static spark.Spark.*;

import java.sql.Connection;

import com.ydev00.model.User;
import com.ydev00.dao.UserDAO;
import com.ydev00.util.Message;

import org.eclipse.jetty.http.HttpStatus;
import com.google.gson.Gson;

public class UserController {
  private User user;
  private Connection dbConn;
  private Gson gson;
  private UserDAO userDAO;


  public UserController() {}

  public UserController(Connection dbConn) {
    this.dbConn = dbConn;
    this.gson = new Gson();
    this.userDAO = new UserDAO(dbConn);
  } 


  public Route login = (request, response) -> {
    response.type("application.json");

    if (user == null) 
      return null;

    user = userDAO.getByEmail(gson.fromJson("email", String.class), gson.fromJson("password", String.class));

    if (user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logging in");
      return gson.toJson(message, Message.class);
    }

    response.status(HttpStatus.OK_200);

    String resp = "{\n\"user\": "
    + gson.toJson(user) + "\n";
    return resp; 
  };

  public Route signup = (request, response) -> {
    response.type("application.json");

    user = gson.fromJson(request.body(), User.class);
    



    return gson.toJson(userDAO.signup(user));
  };

  public Route getUser = (request, response) -> {
    response.type("application.json");

    user = userDAO.getUserByUsername(request.params("username"));

    if(user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logging in");
      return gson.toJson(message);
    }

    user.setAuth(true);
    response.status(HttpStatus.OK_200);
    return gson.toJson(user); 
  };
}
