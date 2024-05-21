package com.ydev00.util;

import static spark.Spark.*;

import java.sql.Connection;

import com.ydev00.controller.*;


public class Server {
  private Connection dbConn;

  public Server(DBServer dbServer) {
    try {
      port(8080);
      init();

      dbConn = dbServer.getConn();
      System.out.println(dbConn.getMetaData());

      UserController userController = new UserController(dbConn);
      ThreadController threadController = new ThreadController(dbConn);
      ModUserController modUserController = new ModUserController(dbConn);

      // routes
      redirect.get("/", "/home/");
      redirect.get("/home", "/home/");

      get("/getAll", userController.getAll); // done

      path("/user", () -> {
        post("/signup", userController.signup); // done
        post("/login", "application.json", userController.login); // done
        get("/:username", "application.json", userController.getByUsername); // done
        post("/logout", "application.json", userController.logout); // done

        get("/:username/t", "application.json", threadController.getThreadsByUser); // done
        post("/:username/follow", "application.json", userController.follow); // done
        post("/:username/unfollow", "application.json", userController.unfollow); // done
        post("/:username/like", "application.json", userController.like);
        post("/:username/unlike", "application.json", userController.unlike);
        get(":username/followers", "application.json", userController.getFollowers);
        get(":username/followees", "application.json", userController.getFollowees);
      });

      path("/home", () -> {
        get("/", "application.json" , threadController.loadFeed); // maybe done
        path("/t", () -> {
          get("/:id", "application.json", threadController.loadThread);
          post("/new", "application.json", threadController.create); // done
          post("/comment", "application.json", threadController.comment);
        });
      });

      path("/mod", () -> {
        get("/:username", "application.json", userController.getByUsername);
        path("delete", () -> {
          post("/user", "application.json", modUserController.deleteUser);
          post("/thread", "application.json", modUserController.deletePost);
        });
      });

      if(dbConn != null)
        System.out.println("[Server Connected]");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }
}
