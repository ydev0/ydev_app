package com.ydev00.dao;

import com.ydev00.model.User;
import com.ydev00.model.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.*;

public class UserDAO {
  private Connection dbConn;
  private String query;
  private PreparedStatement statement ;
  private ResultSet resultSet;

  public UserDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }


  public User getByEmail(String email, String password) {
    User user = new User();
    try {

      query = "SELECT * FROM user WHERE email = ?;"; 

      statement = dbConn.prepareStatement(query);
      statement.setString(1, email);
      resultSet = statement.executeQuery();

      if(resultSet != null && resultSet.next()) {
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setProfilePic(new Image(resultSet.getInt("pfp_id")));
        if(password.equals(user.getPassword()))
          return user;
      } 
      return null;
    } catch (Exception e) {
      System.err.println("User not found! " +e.getMessage());
    }
    return user;
  }

  public List<User> listUsers(){
    List<User> users = new ArrayList<>();

    return users;
  }
}
