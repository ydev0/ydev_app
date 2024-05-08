package com.ydev00.util;

import static spark.Spark.*;

import java.io.*;
import java.sql.Connection;

import com.ydev00.model.*;
import com.ydev00.util.*;
import com.ydev00.controller.*;


public class Server {
  private Connection dbConn;
  public Server(DBServer dbServer, Firebase fb) {
    try {
      // init server
      port(8080);
      init();

      before();

      // setup ports
      dbConn = dbServer.getConn();

      UserController userController = new UserController(dbConn, fb);

      // routes 
      redirect.get("/", "/login"); // change to auth
      post("/login", userController.login);
      post("/loginByURL/*", "application.json" ,userController.loginByURL);


      System.out.println("Server connected");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }


  public void before() {
    options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
  }
}
