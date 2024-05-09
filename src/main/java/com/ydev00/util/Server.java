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

      before();

      // setup ports
      dbConn = dbServer.getConn();

      UserController userController = new UserController(dbConn);

      // routes 
      redirect.get("/", "/login"); // change to auth
      post("/login", userController.login);


      System.out.println("Server connected");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }


}
