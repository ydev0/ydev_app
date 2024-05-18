package com.ydev00.controller;

import com.ydev00.dao.RelationDAO;
import com.ydev00.model.thread.Thrd;
import spark.Route;

import java.sql.Connection;
import java.util.List;

import com.ydev00.model.user.User;
import com.ydev00.model.user.ModUser;
import com.ydev00.model.image.Image;
import com.ydev00.dao.UserDAO;
import com.ydev00.dao.ImageDAO;
import com.ydev00.util.Message;

import org.eclipse.jetty.http.HttpStatus;
import com.google.gson.Gson;

public class UserController {
  private Connection dbConn;
  private Gson gson;
  private UserDAO userDAO;
  private ImageDAO imageDAO;
  private RelationDAO relationDAO;


  public UserController() {}

  public UserController(Connection dbConn) {
    this.dbConn = dbConn;
    this.gson = new Gson();
    this.userDAO = new UserDAO(dbConn);
    this.imageDAO = new ImageDAO(dbConn);
    this.relationDAO = new RelationDAO(dbConn);
  }

  public Route signup = (request, response) -> {
    response.type("application.json");

    User user = gson.fromJson(request.body(), User.class);

    if (user == null || user.getEmail() == null || user.getPassword() == null || user.getUsername() == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Incomplete input data");
      return gson.toJson(message, Message.class);
    }

    if ((User) userDAO.getByUsername(user) != null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Username already exists");
      return gson.toJson(message, Message.class);
    }

    user = (User) userDAO.create(user);

    response.status(HttpStatus.OK_200);
    return gson.toJson(user, User.class);
  };

  public Route login = (request, response) -> {
    response.type("application.json");

    User user =  gson.fromJson(request.body(), User.class);

    if (user == null || user.getEmail() == null || user.getPassword() == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Wrong input");
      return gson.toJson(message, Message.class);
    }

    user = userDAO.get(user);

    if (user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logging in");
      return gson.toJson(message, Message.class);
    }

    Image image = imageDAO.get(user.getProfilePic());

    if(image != null)
      user.setProfilePic(image);

    response.status(HttpStatus.OK_200);
    user.setAuth(true);

    if(user.isRoot()) {
      return gson.toJson(user, ModUser.class);
    }

    return gson.toJson(user, User.class);
  };


  public Route getByUsername = (request, response) -> {
    response.type("application.json");

    User user = (User) userDAO.getByUsername(new User(request.params("username")));

    if(user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logging in");
      return gson.toJson(message, Message.class);
    }

    Image image = imageDAO.get(user.getProfilePic());
    if(image != null)
      user.setProfilePic(image);

    response.status(HttpStatus.OK_200);
    return gson.toJson(user, User.class);
  };

  public Route getAll = (request, response) -> {
    response.type("application.json");

    List<User> lista = userDAO.listUsers();

    if(lista == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "wtf");
      return gson.toJson(message, Message.class);
    }

    return gson.toJson(lista,User.class);
  };

  public Route follow = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    relationDAO.follow(request.params("username"), request.headers("username"));

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

    relationDAO.unfollow(request.params("username"), request.headers("username"));

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

    relationDAO.like(thrd.getId(), request.headers("username"));

    return "Liked post +" +thrd.getId();
  };

  public Route unlike = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    Thrd thrd = gson.fromJson(request.body(), Thrd.class);

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