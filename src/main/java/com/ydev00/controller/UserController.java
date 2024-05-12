package com.ydev00.controller;

import spark.Route;
import static spark.Spark.*;

import java.sql.Connection;

import com.ydev00.model.User;
import com.ydev00.model.Image;
import com.ydev00.dao.UserDAO;
import com.ydev00.dao.ImageDAO;
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

    user.setName(gson.fromJson("name", String.class));
    
    System.out.println("CARLOS:" + gson.fromJson("name", String.class));
    user.setUsername(gson.fromJson("username", String.class));
    user.setEmail(gson.fromJson("email", String.class));
    
    user.setProfilePic(new Image(Integer.valueOf(gson.fromJson("pfp_id", String.class))));
    user.setName(gson.fromJson("name", String.class));

    user = userDAO.getByEmail(gson.fromJson("email", String.class), gson.fromJson("password", String.class));

    if (user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logging in");
      return gson.toJson(message, Message.class);
    }

    ImageDAO imageDAO = new ImageDAO(dbConn);

    user.setProfilePic(imageDAO.getById(user.getProfilePic().getId()));

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

  public Route getUserByUsername = (request, response) -> {
    response.type("application.json");

    user = userDAO.getUserByUsername(request.params("username"));

    if(user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logging in");
      return gson.toJson(message, Message.class);
    }

    response.status(HttpStatus.OK_200);
    user.setAuth(true);
    return gson.toJson(user, User.class); 
  };
}
