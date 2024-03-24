package com.ydev00;

import com.ydev00.util.Server;

public class App {
  public static void main( String[] args ){
    Server server = new Server();
    Server.initServer();
    server.helloWorld();
  }
}
