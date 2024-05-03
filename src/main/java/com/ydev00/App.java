package com.ydev00;

import com.ydev00.util.Server;
import com.ydev00.model.User;

public class App {
  public static void main( String[] args ){
    Server server = new Server();
    Server.initServer();
    server.helloWorld();
    User user = new User();
  }
}
