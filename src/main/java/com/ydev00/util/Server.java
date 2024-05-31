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
      post("/signup", userController.signup); // done
      post("/login", "application.json", userController.login); // done

      get("/user/getFollowers", "application.json", userController.getFollowers); // done
      get("/user/getFollowees", "application.json", userController.getFollowees);

      get("/user/t", "application.json", threadController.getThreadsByUser); // done

      get("/user/:username", "application.json", userController.getByUsername); // done
      post("/user/logout", "application.json", userController.logout); // done

      post("/user/follow", "application.json", userController.follow); // done
      post("/user/unfollow", "application.json", userController.unfollow); // done
      put("/user/update", "application.json", userController.update);


      get("/feed", "application.json" , threadController.loadFeed); // maybe done

      path("/t", () -> {
        get("/:id", "application.json", threadController.loadThread);
        post("/new", "application.json", threadController.create); // done
        post("/comment", "application.json", threadController.comment);

        post("/like", "application.json", userController.like);
        post("/unlike", "application.json", userController.unlike);
      });

      path("/mod", () -> {
        get("/:username", "application.json", userController.getByUsername);
        path("delete", () -> {
          delete("/user", "application.json", modUserController.deleteUser);
          delete("/thread", "application.json", modUserController.deletePost);
        });
      });

      if(dbConn != null)
        System.out.println("[Server Connected]");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }
}
