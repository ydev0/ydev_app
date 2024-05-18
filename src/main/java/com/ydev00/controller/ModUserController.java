package com.ydev00.controller;

import com.google.gson.Gson;
import spark.Route;

import com.ydev00.model.user.User;
import com.ydev00.model.thread.Thrd;
import com.ydev00.dao.ThreadDAO;
import com.ydev00.dao.UserDAO;

import java.sql.Connection;

public class ModUserController {
  private Gson gson;
  private Connection dbConn;
  private UserDAO userDAO;

  public ModUserController(Connection dbConn) {
    this.dbConn = dbConn;
    this.gson = new Gson();
    this.userDAO = new UserDAO(dbConn);
  }



  public Route deletePost = (request, response) -> {
    response.type("application/json");

    if(request.headers("root" ) == null) {
      response.status(403);
      return "Forbidden";
    }

    Thrd thrd = gson.fromJson(request.body(), Thrd.class);

    ThreadDAO threadDAO = new ThreadDAO(dbConn);

    thrd = (Thrd) threadDAO.delete(thrd);


    return "carlos";
  };

  public Route deleteUser = (request, response) -> {
    response.type("application/json");

    if(request.headers("root" ) == null) {
      response.status(403);
      return "Forbidden";
    }

    User user = gson.fromJson(request.body(), User.class);

    userDAO = new UserDAO(dbConn);
    user = userDAO.get(user);
    if(user == null) {
      response.status(404);
      return "User not found";
    }
    user = (User) userDAO.delete(user);

    return null;
  };

}
