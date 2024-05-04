package com.ydev00;

import com.ydev00.util.Server;
import com.ydev00.util.DBServer;
import com.ydev00.model.User;

public class App {
    private DBServer dbServer;
    private Server server;
    private User user;

  public static void main( String[] args ){
    DBServer dbServer = new DBServer();
    Server server = new Server(dbServer);
    User user = new User();
  }
}
