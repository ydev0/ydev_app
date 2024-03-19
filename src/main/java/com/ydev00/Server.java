package com.ydev00;

import java.io.*;
import static spark.Spark.*;

public class Server {
  private static int serverCounter;
  public Server(){
    initServer();
  }
  public void initServer() {
    init();
    serverCounter++;
  }
  public void stopServer() {
    System.out.println("Server Stopped");
  }
  public void helloWorld(){
    if(serverCounter == 0){
      initServer();
    }
    get("/hello", (req, res) -> "Hello, World!");
  }
}
