package com.ydev00.util;

import java.sql.*; 

public class DBServer {
  private Connection conn;

  public DBServer() {
    int retries = 5;
    while (retries > 0) {
      try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ydev_db?" + "user=root&password=password");
        // conn = DriverManager.getConnection("jdbc:mysql://ydev_db:3306/ydev_db?" + "user=root&password=password");
        break;
      } catch (SQLException ex) {
        retries--;
        System.out.println("Failed to connect to database. Retrying in 5 seconds...");
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }  
      if (retries == 0) {
        System.out.println("Failed to connect to database. Exiting...");
        System.exit(1);
      }
    }
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public Connection getConn() {
    return conn;
  }
}
