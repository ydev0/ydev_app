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

      get("/user/:username/getFollowers", "application.json", userController.getFollowers); // done
      get("/user/:username/getFollowees", "application.json", userController.getFollowees); // done

      get("/user/:username/t", "application.json", threadController.getThreadsByUser); // done

      get("/user/:username", "application.json", userController.getByUsername); // done


      post("/user/follow", "application.json", userController.follow); // done
      post("/user/unfollow", "application.json", userController.unfollow); // done

      put("/user/update", "application.json", userController.update);


      post("/user/logout", "application.json", userController.logout); // done

      get("/feed", "application.json" , threadController.loadFeed); // done

      get("/t/:id", "application.json", threadController.loadThread); // done

      post("/t/new", "application.json", threadController.create); // done

      post("/t/comment/:id", "application.json", threadController.comment); // done

      post("/t/like", "application.json", userController.like);
      post("/t/unlike", "application.json", userController.unlike);

      delete("/delete/user", "application.json", modUserController.deleteUser);
      delete("/delete/thread", "application.json", modUserController.deletePost);

      if(dbConn != null)
        System.out.println("[Server Connected]");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }
}
