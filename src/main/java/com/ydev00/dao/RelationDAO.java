package com.ydev00.dao;

import com.ydev00.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
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

      User user = (User)userDAO.getByUsername(new User(follower));
      User followeeUser = (User)userDAO.getByUsername(new User(followee));

      query = "insert into relations (follower_id, followed_id) values (?, ?)";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      statement.setInt(2, followeeUser.getId());
      statement.executeQuery();
    } catch (Exception ex) {
      System.err.println("Could not follow: "+ex.getMessage());
    }
  }

  public User unfollow (String followee, String follower) {
    try {
      UserDAO userDAO = new UserDAO(dbConn);

      User user = (User)userDAO.getByUsername(new User(follower));
      User followeeUser = (User)userDAO.getByUsername(new User(followee));

      query = "delete from relations where follower_id = ? and followed_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      statement.setInt(2, followeeUser.getId());
      statement.executeQuery();
    } catch (Exception ex) {
      System.err.println("Could not follow: "+ex.getMessage());
    }
    return null;
  }

  public List<User> getFollowers (Object obj) {
    User user = (User)obj;
    List<User> users = new ArrayList<>();
    try {

      query = "select * from relations where follower_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, (user.getId()));
      resultSet = statement.executeQuery();


      UserDAO userDAO = new UserDAO(dbConn);
      while(resultSet.next()) {
        User follower = (User)userDAO.get(new User(resultSet.getInt("followed_id")));
        users.add(follower);
      }

      if(users.isEmpty()) {
        return null;
      }

    } catch (Exception ex) {
      System.err.println("Could not get followers: "+ex.getMessage());
    }
    return users;
  }

  public User like (Object obj, String username) {
    return null;
  }

  public User unlike (Object obj, String username) {
    return null;
  }
}
