package com.ydev00.dao;

import com.ydev00.model.user.User;
import com.ydev00.model.thread.Thrd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class RelationDAO {
  private final Connection dbConn;
  private String query;
  private PreparedStatement statement ;
  private ResultSet resultSet;
  private final UserDAO userDAO;

  public RelationDAO(Connection dbConn) {
    this.dbConn = dbConn;
    this.userDAO = new UserDAO(dbConn);
  }

  public void follow (String usr, String flwr) {
    try {
      User user = (User)userDAO.getByUsername(new User(flwr));
      User followeeUser = (User)userDAO.getByUsername(new User(usr));

      if(user == null || followeeUser == null) {
        throw new Exception("User not found");
      }

      if (user.getId() == followeeUser.getId()) {
        throw new Exception("Cannot follow yourself");
      }

      query = "insert into usr_flw (usr_id, flw_id) values (?, ?)";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      statement.setInt(2, followeeUser.getId());
      statement.execute();

    } catch (Exception ex) {
      System.err.println("Could not follow: "+ex.getMessage());
    }
  }

  public void unfollow (String usr, String flwr) {
    try {
      User user = (User)userDAO.getByUsername(new User(flwr));
      User followeeUser = (User)userDAO.getByUsername(new User(usr));

      if(user == null || followeeUser == null) {
        throw new Exception("User not found");
      }

      if(usr.equals(flwr)) {
        throw new Exception("Cannot unfollow yourself");
      }

      query = "delete from usr_flw where usr_id = ? and flw_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      statement.setInt(2, followeeUser.getId());
      statement.execute();
    } catch (Exception ex) {
      System.err.println("Could not follow: "+ex.getMessage());
    }
  }

  public List<User> getFollowers (Object obj) {
    User user = (User)obj;
    List<User> users = new ArrayList<>();
    try {

      query = "select * from usr_flw where usr_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, (user.getId()));
      statement.execute();
      resultSet = statement.getResultSet();

      while(resultSet.next()) {
        User follower = userDAO.get(new User(resultSet.getInt("followed_id")));
        users.add(follower);
      }
    } catch (Exception ex) {
      System.err.println("Could not get followers: "+ex.getMessage());
      return null;
    }
    return users;
  }

  public List<User> getFollowees (Object obj) {
    User user = (User)obj;
    List<User> users = new ArrayList<>();

    try {
      query = "select * from usr_flw where flw_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, (user.getId()));
      statement.execute();
      resultSet = statement.getResultSet();

      while(resultSet.next()) {
        User followee = userDAO.get(new User(resultSet.getInt("flw_id")));
        users.add(followee);
      }
    } catch (Exception ex) {
      System.err.println("Could not get followees: "+ex.getMessage());
      return null;
    }
    return users;
  }

  public boolean like (Thrd thrd, User user) {
    try {
      query = "insert into usr_lk(thrd_id, usr_id) values (?, ?)";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thrd.getId());
      statement.setInt(2, user.getId());
      statement.execute();
    }
    catch (Exception ex) {
      System.err.println("Could not like: "+ex.getMessage());
      return false;
    }
    return true;
  }

  public boolean unlike (Thrd thrd, User user) {
    try {
      query = "delete from usr_lk where thrd_id = ? and usr_id = ?;";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thrd.getId());
      statement.setInt(2, user.getId());
      statement.execute();
    }
    catch (Exception ex) {
      System.err.println("Could not like: "+ex.getMessage());
      return false;
    }
    return true;
  }
}
