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


  public UserController() {}

  public UserController(Connection dbConn) {
    this.dbConn = dbConn;
    this.gson = new Gson();
  } 


  public Route login = (request, response) -> {
    response.type("application.json");

    User user = gson.fromJson(request.body(), User.class);

    if (user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logging in");
      return gson.toJson(message);
    }

    UserDAO userDAO = new UserDAO(dbConn);


    response.status(HttpStatus.OK_200);

    String resp = "{\n\"user\": "
    + gson.toJson(user) +"\n";

    return resp;
  };

  public Route signup = (request, response) -> {
    response.type("application.json");

    User user = gson.fromJson(request.body(), User.class);



    return "carlos";
  };

  public Route getUser = (request, response) -> {

    return "carlos";
  };
}
