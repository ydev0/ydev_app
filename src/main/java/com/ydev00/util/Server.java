package com.ydev00.util;

import java.io.*;
import static spark.Spark.*;
import com.ydev00.model.*;
import com.ydev00.util.*;

public class Server {
  public Server(DBServer dbServer) {
    try {
    // init server
    port(8080);
    init();

    validate();
    // setup ports
    Router.setupPorts();

    System.out.println("Server connected");
    } catch (Exception ex) {
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }

  public void validate() {
    // spark befores like a auth
    System.out.println("Successfully validated");
  }
}
