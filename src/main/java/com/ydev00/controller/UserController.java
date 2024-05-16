package com.ydev00.controller;

import com.ydev00.dao.RelationDAO;
import com.ydev00.model.Thrd;
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

    User user =  gson.fromJson(request.body(), User.class);

    if (user == null || user.getEmail() == null || user.getPassword() == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Wrong input");
      return gson.toJson(message, Message.class);
    }

    user = (User) userDAO.get(user);

    if (user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logging in");
      return gson.toJson(message, Message.class);
    }

    ImageDAO imageDAO = new ImageDAO(dbConn);
    Image image = imageDAO.get(user.getProfilePic());
    user.setProfilePic(image);

    response.status(HttpStatus.OK_200);
    return gson.toJson(user, User.class);
  };

  public Route signup = (request, response) -> {
    response.type("application.json");

    System.out.println(gson.toJson(request.body(), User.class));
    System.out.println("rodrigo");

    user = gson.fromJson(request.body(), User.class);

    if (userDAO.getByUsername(user.getUsername()) != null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Username already exists");
      return gson.toJson(message, Message.class);
    }

    user = (User)userDAO.create(user);

    response.status(HttpStatus.OK_200);
    return gson.toJson("User created", String.class) + gson.toJson(user, User.class);
  };

  public Route getByUsername = (request, response) -> {
    response.type("application.json");

    user = (User) userDAO.getByUsername(request.params("username"));

    if(user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logging in");
      return gson.toJson(message, Message.class);
    }

    ImageDAO imageDAO = new ImageDAO(dbConn);
    Image image = imageDAO.get(user.getProfilePic());
    user.setProfilePic(image);

    response.status(HttpStatus.OK_200);
    return gson.toJson(user, User.class);
  };

  public Route follow = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }


    RelationDAO relationDAO = new RelationDAO(dbConn);
    relationDAO.follow(request.params("username"), gson.fromJson(request.body(), User.class).getUsername());

    return "Followed user +" + request.params("username");
  };

  public Route unfollow = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }


    User user = gson.fromJson(request.body(), User.class);

    return "Unfollowed user +" +user.getUsername();
  };

  public Route like = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    Thrd thrd = gson.fromJson(request.body(), Thrd.class);

    RelationDAO relationDAO = new RelationDAO(dbConn);
    relationDAO.like(thrd.getId(), request.headers("username"));

    return "Liked post +" + thrd.getId();
  };

  public Route unlike = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    Thrd thrd = gson.fromJson(request.body(), Thrd.class);

    RelationDAO relationDAO = new RelationDAO(dbConn);
    relationDAO.unlike(thrd.getId(), request.headers("username"));

    return "Unliked post +" + thrd.getId();
  };

  public Route logout = (request, response) -> {
    response.type("application.json");

    if(request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    User user = gson.fromJson(request.body(), User.class);

    if(user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Wrong input");
      return gson.toJson(message, Message.class);
    }

    user.setAuth(false);

    return "Logged user out";
  };
}