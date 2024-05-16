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
      ModUserController modUserController = new ModUserController(dbConn);

      // routes
      redirect.get("/", "/home/");
      redirect.get("/home", "/home/");

      path("/user", () -> {
        post("/login", "application.json", userController.login);
        post("/signup", "application.json", userController.signup);
        get("/:username", "application.json",userController.getByUsername);
        get("/:username/t/:id", "application.json",threadController.getThreadsByUser);
        post("/:username/follow", "application.json", userController.follow);
      });

      path("/home", () -> {
        get("/", "application.json" , threadController.loadFeed);

        path("/t", () -> {
          get("/:id", "application.json", threadController.loadThread);
          post("/new", "application.json", threadController.create);
          delete("/:id", "application.json", threadController.delete);
        });

        post("/a/new", "application.json", threadController.create);
      });

      path("/mod", () -> {
        post("/login", "application.json", modUserController.login);
        get("/:username", "application.json", userController.getByUsername);
        get("/:username/t/:id", "application.json",threadController.getThreadsByUser);
      });

      if(dbConn != null)
        System.out.println("[Server Connected]");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }
}
