package com.ydev00.util;

import java.sql.*; 

public class DBServer {
  private Connection conn;

  public DBServer() {
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/test?" +
        "user=root&password=password");
    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public Connection getConn() {
    return conn;
  }
}
