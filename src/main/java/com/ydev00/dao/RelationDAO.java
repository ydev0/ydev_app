package com.ydev00.dao;

import com.ydev00.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RelationDAO {
  private Connection dbConn;
  private String query;
  private PreparedStatement statement ;
  private ResultSet resultSet;

  public RelationDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }

  public User follow (Object obj) {
    return null;
  }

  public User unfollow (Object obj) {
    return null;
  }

  public User like (Object obj, String username) {
    return null;
  }

  public User unlike (Object obj, String username) {
    return null;
  }
}
