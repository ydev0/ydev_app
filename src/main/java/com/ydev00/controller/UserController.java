package com.ydev00.controller;

import com.ydev00.dao.RelationDAO;
import com.ydev00.model.thread.Thrd;
import spark.Route;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

import com.ydev00.model.user.User;
import com.ydev00.model.user.ModUser;
import com.ydev00.model.image.Image;
import com.ydev00.dao.UserDAO;
import com.ydev00.dao.ImageDAO;
import com.ydev00.util.Message;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import org.eclipse.jetty.http.HttpStatus;
import com.google.gson.Gson;

/**
 * Controlador para operações relacionadas a usuários.
 * Gerencia as operações de registro, login, obtenção de informações de usuário, seguir e deixar de seguir outros usuários, curtir e descurtir postagens, entre outras.
 */
public class UserController {
  private Connection dbConn;
  private Gson gson;
  private UserDAO userDAO;
  private ImageDAO imageDAO;
  private RelationDAO relationDAO;

  /**
   * Construtor padrão da classe UserController.
   */
  public UserController() {}

  /**
   * Construtor da classe UserController.
   *
   * @param dbConn A conexão com o banco de dados.
   */
  public UserController(Connection dbConn) {
    this.dbConn = dbConn;
    this.gson = new Gson();
    this.userDAO = new UserDAO(dbConn);
    this.imageDAO = new ImageDAO(dbConn);
    this.relationDAO = new RelationDAO(dbConn);
  }

  /**
   * Rota para cadastro de usuário.
   */
  public Route signup = (request, response) -> {
    response.type("application.json");

    User user = gson.fromJson(request.body(), User.class);

    if (user == null || user.getEmail() == null || user.getPassword() == null || user.getUsername() == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Incomplete input data");
      return gson.toJson(message, Message.class);
    }

    User userExists = new User(user.getUsername());
    userExists = (User) userDAO.getByUsername(userExists);

    if (userExists.getEmail() != null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Username already exists");
      return gson.toJson(message, Message.class);
    }
    user = userDAO.create(user);

    response.status(HttpStatus.OK_200);
    return gson.toJson(user, User.class);
  };

  /**
   * Rota para login de usuário.
   */
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

  /**
   * Rota para obter usuário pelo nome de usuário.
   */
  public Route getByUsername = (request, response) -> {
    response.type("application.json");

    User user = (User) userDAO.getByUsername(new User(request.params("username")));


    if(user == null || user.getEmail() == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not found");
      return gson.toJson(message, Message.class);
    }

    Image image = imageDAO.get(user.getProfilePic());
    if(image != null)
      user.setProfilePic(image);

    response.status(HttpStatus.OK_200);
    return gson.toJson(user, User.class);
  };

  /**
   * Rota para obter todos os usuários.
   */
  public Route getAll = (request, response) -> {
    response.type("application.json");

    List<User> users = userDAO.listUsers();

    if(users == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "wtf");
      return gson.toJson(message, Message.class);
    }

    Type type = new TypeToken<ArrayList<User>>(){}.getType();
    return gson.toJson(users, type);
  };

  /**
   * Rota para atualizar o perfil do usuário.
   */
  public Route update = (request, response) -> {
    response.type("application.json");

    User user = gson.fromJson(request.body(), User.class);

    if(user == null || user.getUsername() == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Wrong input");
      return gson.toJson(message, Message.class);
    }

    user = (User) userDAO.update(user);

    if(user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Could not update user");
      return gson.toJson(message, Message.class);
    }

    return gson.toJson(user, User.class);
  };

  /**
   * Rota para um usuário seguir outro usuário.
   */
  public Route follow = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    User userToFollow = (User)userDAO.getByUsername(gson.fromJson(request.body(), User.class));

    if(userToFollow == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not found");
      return gson.toJson(message, Message.class);
    }

    relationDAO.follow(request.headers("username"), userToFollow.getUsername());
    return "Followed user + " + userToFollow.getUsername();
  };

  /**
   * Rota para um usuário deixar de seguir outro usuário.
   */
  public Route unfollow = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    User userToUnfollow = (User)userDAO.getByUsername(gson.fromJson(request.body(), User.class));

    if (userToUnfollow == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not found");
      return gson.toJson(message, Message.class);
    }

    relationDAO.unfollow(request.params("username"), userToUnfollow.getUsername());
    return "Unfollowed user" + userToUnfollow.getUsername();
  };

  /**
   * Rota para curtir uma thread
   */
  public Route like = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null || request.headers("username") == null ){
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    Thrd thrd = gson.fromJson(request.body(), Thrd.class);
    User user = (User) userDAO.getByUsername(new User(request.headers("username")));

    if(thrd == null || user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Wrong input");
      return gson.toJson(message, Message.class);
    }

    if(relationDAO.like(thrd, user))
      return "Liked post +" +thrd.getId();
    return "Could not like post +" +thrd.getId();
  };

  /**
   * Rota para descutir alguma thread
   */
  public Route unlike = (request, response) -> {
    response.type("application.json");

    if (request.headers("auth") == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    Thrd thrd = gson.fromJson(request.body(), Thrd.class);
    User user = (User) userDAO.getByUsername(gson.fromJson(request.body(), User.class));

    if(thrd == null || user == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Wrong input");
      return gson.toJson(message, Message.class);
    }

    if(relationDAO.unlike(thrd, user))
      return "Unliked post +" + thrd.getId();
    return "Could not unlike post +" + thrd.getId();
  };

  /**
   * Rota para que um usuário deslogue do sistema
   */
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
    return gson.toJson(user, User.class);
  };

  /**
   * Rota para obter os seguidores de um usuário
   */
  public Route getFollowers = (request, response) -> {
    response.type("application.json");

    if(!request.headers("auth").equals("true") || request.headers("username") == null){
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    User user = (User) userDAO.getByUsername(new User(request.headers("username")));

    if(user == null || user.getEmail() == null){
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not found");
      return gson.toJson(message, Message.class);
    }

    Image image = imageDAO.get(user.getProfilePic());
    if(image != null)
      user.setProfilePic(image);

    List<User> followees = relationDAO.getFollowers(user);

    if(followees == null || followees.isEmpty()) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Could not get followers");
      return gson.toJson(message, Message.class);
    }

    Type type = new TypeToken<ArrayList<User>>(){}.getType();
    return gson.toJson(followees, type);
  };

  /**
   * Rota para obter os seguidos de um usuário
   */
  public Route getFollowees = (request, response) -> {
    response.type("application.json");

    if(!request.headers("auth").equals("true") || request.headers("username") == null){
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Not logged in");
      return gson.toJson(message, Message.class);
    }

    User user = (User) userDAO.getByUsername(new User(request.headers("username")));

    if(user == null || user.getEmail() == null){
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not found");
      return gson.toJson(message, Message.class);
    }

    Image image = imageDAO.get(user.getProfilePic());
    if(image != null)
      user.setProfilePic(image);

    List<User> followers = relationDAO.getFollowees(user);

    if(followers == null || followers.isEmpty()) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "Could not get followers");
      return gson.toJson(message, Message.class);
    }

    Type type = new TypeToken<ArrayList<User>>(){}.getType();
    return gson.toJson(followers, type);
  };
}
