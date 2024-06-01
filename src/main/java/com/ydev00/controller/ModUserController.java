package com.ydev00.controller;

import com.google.gson.Gson;
import com.ydev00.model.user.ModUser;
import com.ydev00.util.Message;
import org.eclipse.jetty.http.HttpStatus;
import spark.Route;

import com.ydev00.model.user.User;
import com.ydev00.model.thread.Thrd;
import com.ydev00.model.image.Image;
import com.ydev00.dao.ImageDAO;
import com.ydev00.dao.ThreadDAO;
import com.ydev00.dao.RelationDAO;
import com.ydev00.dao.UserDAO;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

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

    ModUser user = (ModUser)userDAO.get(new User(request.headers("username")));

    if(!user.isRoot())  {
      response.status(HttpStatus.FORBIDDEN_403);
      return "Forbidden";
    }

    Thrd thrd = gson.fromJson(request.body(), Thrd.class);
    ThreadDAO threadDAO = new ThreadDAO(dbConn);


    thrd = (Thrd) threadDAO.get(thrd);


    RelationDAO relationDAO = new RelationDAO(dbConn);
    List<Thrd> assocThreads = new ArrayList<>();
    assocThreads.addAll(relationDAO.getLinkedThreads(thrd));

    for(Thrd t : assocThreads) {
      threadDAO.delete(t);
    }

    thrd = (Thrd) threadDAO.delete(thrd);


    return "carlos";
  };

  public Route deleteUser = (request, response) -> {
    response.type("application/json");

    ModUser root = (ModUser)userDAO.getByUsername(new ModUser(request.headers("username")));

    if(!root.isRoot())  {
      response.status(HttpStatus.FORBIDDEN_403);
      Message message = new Message("Error", "Forbidden");
      return gson.toJson(message, Message.class);
    }

    User user = gson.fromJson(request.body(), User.class);
    user = (User) userDAO.getByUsername(user);

    if(user == null || user.getProfilePic() == null) {
      response.status(HttpStatus.FAILED_DEPENDENCY_424);
      Message message = new Message("Error", "User not found");
      return gson.toJson(message, Message.class); 
    }

    ImageDAO imageDAO = new ImageDAO(dbConn);
    Image image = imageDAO.get(user.getProfilePic());
    user.setProfilePic(image);

    user = (User) userDAO.delete(user);

    if (user == null){
      response.status(HttpStatus.OK_200);
      Message message = new Message("Success", "User deleted");
      return gson.toJson(message, Message.class);
    }

    response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
    Message message = new Message("Error", "Could not delete user");
    return gson.toJson(message, Message.class);
  };
}
