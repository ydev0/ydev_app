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

      // routes 
      redirect.get("/", "/home"); // change to auth
      path("user", () -> {
        post("/login", userController.login);
        get("/:username", userController.getUserByUsername);
      });

      get("/home", (request, response) -> {
        return "home";
      });


      if(dbConn != null) 
        System.out.println("[Server Connected]");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }


}
