package com.ydev00.util;

import java.io.*;
import static spark.Spark.*;
import com.ydev00.model.*;

public class Server {


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
  public void helloUser(User user) {
    post("/hello", (request, responde) -> {
      response.body(user.getName());


    });
  }
}
