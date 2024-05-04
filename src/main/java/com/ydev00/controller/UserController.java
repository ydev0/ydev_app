package com.ydev00.controller;

import spark.Route;
import static spark.Spark.*;
import java.sql.Connection;
import com.ydev00.model.User;
import com.ydev00.dao.UserDAO;

public class UserController {
  private User user;
  private Connection dbConn;

  public UserController() {}

  public UserController(Connection dbConn) {
    this.dbConn = dbConn;
  } 


  public Route login = (request, response) -> {
    response.type("application/json");
    Route route = null;
    user = null;

        

    UserDAO userDAO = new UserDAO(dbConn);
    return route;
  };
}
