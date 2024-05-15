package com.ydev00.util;

import static spark.Spark.*;

import java.io.*;
import java.sql.Connection;

import com.ydev00.model.*;
import com.ydev00.util.*;
import com.ydev00.controller.*;


public class Server {
  private Connection dbConn;

  public Server(DBServer dbServer) {
    try {
      // init server
      port(8080);
      init();

      dbConn = dbServer.getConn();
      System.out.println(dbConn.getMetaData());

      UserController userController = new UserController(dbConn);
      ThreadController threadController = new ThreadController(dbConn);
      ArticleController articleController = new ArticleController(dbConn);

      // routes 
      redirect.get("/", "/home"); 
      path("/user", () -> {
        post("/login", userController.login);
        get("/:username", "application.json",userController.getByUsername);

        get("/:username/t/:id", "application.json",threadController.getThreadsByUser);
      });

      path("/home", () -> {
        get("/", "application.json" , threadController.loadFeed);
        get("/t/:id", "application.json", threadController.loadThread);

        post("/t/new", "application.json", threadController.create);

        post("/a/new", "application.json", articleController.create);
        delete("/t/:id", "application.json", threadController.delete);
      });


      if(dbConn != null) 
        System.out.println("[Server Connected]");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }
}
