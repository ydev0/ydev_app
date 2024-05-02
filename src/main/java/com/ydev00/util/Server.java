package com.ydev00.util;

import java.io.*;
import static spark.Spark.*;

public class Server {
  /* parameters */


  /* methods */
  public static void initServer() {
    port(8080);
  }
  
  public void stopServer() {
    stop();
  }
  public void helloWorld() {
    get("/hello/:name", (request, response) -> {
      return "Hello: " + request.params(":name");
    });
  }
}
