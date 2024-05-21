package com.ydev00.controller;

import com.ydev00.dao.RelationDAO;
import com.ydev00.dao.UserDAO;
import com.ydev00.util.Message;
import spark.Route;

import com.google.gson.Gson;

import com.ydev00.model.thread.Thrd;
import com.ydev00.model.user.User;
import com.ydev00.dao.ThreadDAO;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import org.eclipse.jetty.http.HttpStatus;

public class ThreadController {
  private Connection dbConn;
  private Gson gson;
  private UserDAO userDAO;
  private ThreadDAO threadDAO;
  private RelationDAO relationDAO;

  public ThreadController(Connection dbConn) {
    this.dbConn = dbConn;
    this.gson = new Gson();
    this.userDAO = new UserDAO(dbConn);
    this.threadDAO = new ThreadDAO(dbConn);
    this.relationDAO = new RelationDAO(dbConn);
  }

  public Route create = (request, response) -> {
    response.type("application.json");

    if(request.headers("username") == null || !(request.headers("auth").equals("true"))) {
      System.out.println("User not logged in");
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not logged in");
      return gson.toJson(message, Message.class);
    }

    Thrd thrd = gson.fromJson(request.body(), Thrd.class);
    System.out.println(thrd.toString());

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

  public Route loadFeed = (request, response) -> {
    response.type("application.json");

    if(request.headers("username") == null || !(request.headers("auth").equals("true"))) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not logged in");
      return gson.toJson(message, Message.class);
    }

    List<Thrd> feed = new ArrayList<>();

    List<User> followees = relationDAO.getFollowees(new User(request.headers("username")));

    for(User followee : followees) {
      feed.addAll(threadDAO.getByUser(followee));
      if(feed.size() >= 100) {
        break;
      }
    }
    return gson.toJson(feed);
  };

  public Route getThreadsByUser = (request, response) -> {
    response.type("application.json");

    List<Thrd> thrdList;

    User user = (User)userDAO.getByUsername(new User(request.params("username")));

    if (user == null) {
      response.status(HttpStatus.NOT_FOUND_404);
      Message message = new Message("Error", "User not found");
      return gson.toJson(message, Message.class);
    }

    ThreadDAO threadDAO = new ThreadDAO(dbConn);
    thrdList = threadDAO.getByUser(user);

    if (thrdList.isEmpty()) {
      response.status(HttpStatus.NOT_FOUND_404);
      Message message = new Message("Error", "No threads found");
      return gson.toJson(message, Message.class);
    }

    response.status(HttpStatus.OK_200);
    return gson.toJson(thrdList);
  };

  public Route loadThread = (request, response) -> {
    response.type("application.json");

    if(request.params("id") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Thread ID not provided");
      return gson.toJson(message, Message.class);
    }

    Thrd thrd = new Thrd(Integer.parseInt(request.params("id")));
    thrd = (Thrd) threadDAO.get(thrd);

    if(thrd == null) {
      response.status(HttpStatus.NOT_FOUND_404);
      Message message = new Message("Error", "Thread not found");
      return gson.toJson(message, Message.class);
    }

    response.status(HttpStatus.OK_200);
    return gson.toJson(thrd);
  };

}
