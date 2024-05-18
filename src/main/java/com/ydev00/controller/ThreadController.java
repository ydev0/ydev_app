package com.ydev00.controller;

import com.ydev00.dao.UserDAO;
import com.ydev00.util.Message;
import spark.Route;

import com.google.gson.Gson;

import com.ydev00.model.thread.Thrd;
import com.ydev00.model.user.User;
import com.ydev00.dao.ThreadDAO;

import java.util.List;

import java.sql.Connection;
import org.eclipse.jetty.http.HttpStatus;

public class ThreadController {
  private Connection dbConn;
  private Gson gson;

  public ThreadController(Connection dbConn) {
    this.dbConn = dbConn;
    this.gson = new Gson();
  }

  public Route loadFeed = (request, response) -> {
    response.type("application.json");

    return gson.toJson(new Thrd());
  };

  public Route create = (request, response) -> {
    response.type("application.json");

    if(request.headers("username") == null || request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not logged in");
      return gson.toJson(message, Message.class);
    }

    Thrd thrd = gson.fromJson(request.body(), Thrd.class);

    UserDAO userDAO = new UserDAO(dbConn);
    User user = (User) userDAO.getByUsername(new User(request.headers("username")));

    if(user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not logged in");
      return gson.toJson(message, Message.class);
    }

    ThreadDAO threadDAO = new ThreadDAO(dbConn);

    thrd = (Thrd) threadDAO.create(thrd, user.getId());

    if(thrd == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Could not create thread");
      return gson.toJson(message, Message.class);
    }

    return gson.toJson(thrd, Thrd.class);
  };

  public Route getThreadsByUser = (request, response) -> {
    response.type("application.json");

    if (request.headers("username") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not logged in");
      return gson.toJson(message, Message.class);
    }

    List<Thrd> thrdList;

    UserDAO userDAO = new UserDAO(dbConn);
    User user = (User)userDAO.getByUsername(request.headers("username"));

    if (user == null) {
      response.status(HttpStatus.NOT_FOUND_404);
      Message message = new Message("Error", "User not found");
      return gson.toJson(message, Message.class);
    }

    ThreadDAO threadDAO = new ThreadDAO(dbConn);
    thrdList = (List<Thrd>) threadDAO.getByUser(user);

    if (thrdList.isEmpty()) {
      response.status(HttpStatus.NOT_FOUND_404);
      Message message = new Message("Error", "No threads found");
      return gson.toJson(message, Message.class);
    }

    return gson.toJson(thrdList, Thrd.class);
  };

  public Route loadThread = (request, response) -> {
    response.type("application.json");

    List<Thrd> thrdList;

    ThreadDAO threadDAO = new ThreadDAO(dbConn);

    thrdList = (List<Thrd>) threadDAO.getAll();

    if(thrdList.isEmpty()) {
      return gson.toJson(new Thrd());
    }

    return gson.toJson(thrdList, List.class);
  };

}
