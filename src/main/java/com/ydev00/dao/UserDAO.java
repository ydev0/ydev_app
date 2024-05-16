package com.ydev00.dao;

import com.ydev00.model.User;
import com.ydev00.dao.ImageDAO;
import com.ydev00.model.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.*;

public class UserDAO implements DAO{
  private Connection dbConn;
  private String query;
  private PreparedStatement statement ;
  private ResultSet resultSet;

  public UserDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }

  public User create(Object obj) {
    User user = (User) obj;
    try {
      query = "insert into user (username, email, password, root, pfp_id) values (?, ?, ?, 0 , 0) returning id;";

      statement = dbConn.prepareStatement(query);
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getEmail());
      statement.setString(3, user.getPassword());

      user.setProfilePic(null);

      statement.executeQuery();
      resultSet = statement.getGeneratedKeys();

      if (resultSet.next()) {
        user.setId(resultSet.getInt(1));
      }

    } catch (Exception ex) {
      System.err.println("Could not signup: "+ex.getMessage());
      return null;
    }
    return user;
  }

  @Override
  public List<?> getAll() {
    return List.of();
  }

  @Override
  public Object get(Object obj) throws SQLException {
    User user = (User) obj;
    try {
      query = "SELECT * FROM user WHERE email = ?;";

      statement = dbConn.prepareStatement(query);
      statement.setString(1, user.getEmail());
      resultSet = statement.executeQuery();

      if(resultSet.next()) {
        if (!(resultSet.getString("password").equals(user.getPassword()))) {
          System.out.println("Password does not match!");
          return null;
        }

        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword("");
        user.setProfilePic(new Image(resultSet.getInt("pfp_id")));
        user.setAuth(true);
      }
    } catch (Exception e) {
      System.err.println("User not found! " +e.getMessage());
    }
    return user;
  }

  public Object getByUsername(String username) throws SQLException {
    User user = new User();
    try {
      query = "SELECT * FROM user WHERE username= ?;";

      statement = dbConn.prepareStatement(query);
      statement.setString(1, username);
      resultSet = statement.executeQuery();

      if(resultSet.next()) {
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(" ");
        user.setProfilePic(new Image(resultSet.getInt("pfp_id")));
        return user;
      }
    }
    catch (Exception ex) {
      System.err.println("User not found!" + ex.getMessage());
    }
    return null;
  }

  public List<User> listUsers(){
    List<User> users = new ArrayList<User>();

    try {
      query = "select * from user";
      statement = dbConn.prepareStatement(query);
      resultSet = statement.executeQuery();

      while(resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword("");

        ImageDAO imageDAO = new ImageDAO(dbConn);
        Image image = (Image)imageDAO.get(new Image(resultSet.getInt("pfp_id")));

        user.setProfilePic(image);
        users.add(user);
      }
    } catch (Exception ex) {
      System.err.println("Could not get users: "+ex.getMessage());
    }
    return users;
  }

  public Object delete(Object obj) {
    try {
      User user = (User) obj;
      query = "delete from user where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      resultSet = statement.executeQuery();

    } catch (Exception ex) {
      System.err.println("User not found: "+ex.getMessage());
    }
    return null;
  }
}