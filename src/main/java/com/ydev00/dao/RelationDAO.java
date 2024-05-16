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

  public void follow (String followee, String follower) {
    try {
      UserDAO userDAO = new UserDAO(dbConn);
      User user = (User)userDAO.getByUsername(follower);

      User followeeUser = (User)userDAO.getByUsername(followee);

      query = "insert into relations (follower_id, followed_id) values (?, ?)";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      statement.setInt(2, followeeUser.getId());
      statement.executeQuery();
    } catch (Exception ex) {
      System.err.println("Could not follow: "+ex.getMessage());
    }
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
