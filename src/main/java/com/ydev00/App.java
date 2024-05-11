package com.ydev00;

import com.ydev00.util.*;

public class App {
  private DBServer dbServer;
  private Server server;

  public static void main( String[] args ) {
    DBServer dbServer = new DBServer();
    Server server = new Server(dbServer);
  } 
}
