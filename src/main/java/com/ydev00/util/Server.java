package com.ydev00.util;

import java.io.*;
import static spark.Spark.*;
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
      before();

      // setup ports
      dbConn = dbServer.getConn();
      UserController userController = new UserController(dbConn);

      redirect.get("/", "/login"); 
      post("/login", userController.login);
      post("/loginByURL/*", "application.json" ,userController.loginByURL);


      System.out.println("Server connected");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }

  public void before() {
    // spark befores like a auth
    System.out.println("Successfully validated");
  }
}
