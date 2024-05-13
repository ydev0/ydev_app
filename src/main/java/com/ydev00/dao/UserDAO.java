package com.ydev00.dao;

import com.ydev00.model.User;
import com.ydev00.dao.ImageDAO;
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

  public User signup(User user) {
    try {
      query = "insert into user (name, username, email, password, root, pfp_id) values (?, ?, ?, ?, ?, ?, ?) returning id;";

      statement = dbConn.prepareStatement(query);
      statement.setString(1, user.getName());
      statement.setString(2, user.getUsername());
      statement.setString(3, user.getEmail());
      statement.setString(4, user.getPassword());
      statement.setInt(5, 0);
      
      if(user.getProfilePic() == null) {
        ImageDAO imageDAO = new ImageDAO(dbConn);
        Image image = imageDAO.getById(0);
        user.setProfilePic(image);
      }

      if(user.getProfilePic().getImage() == null) {
        ImageDAO imageDAO = new ImageDAO(dbConn);
        Image image = imageDAO.getById(user.getProfilePic().getId());
        user.setProfilePic(image);
      }

      statement.setInt(6, user.getProfilePic().getId());
      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        user.setId(resultSet.getInt("id"));
      }

      statement.close();
      resultSet.close();

    } catch (Exception ex) {
      System.err.println("Could not signup: "+ex.getMessage());
      return null;
    }  
    return user;
  }


  public User getByEmail(String email, String password) {
    User user = new User();
    try {
      query = "SELECT * FROM user WHERE email = ?;"; 

      statement = dbConn.prepareStatement(query);
      statement.setString(1, email);
      resultSet = statement.executeQuery();

      if (resultSet.getString("password") != password) {
        return null;
      }

      if(resultSet.next()) {
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setProfilePic(new Image(resultSet.getInt("pfp_id")));
      } 


    } catch (Exception e) {
      System.err.println("User not found! " +e.getMessage());
    }
    return user;
  }

  public User getUserByUsername(String username){
    User user = new User();
    try {
      query = "SELECT * FROM user WHERE username= ?;"; 

      statement = dbConn.prepareStatement(query);
      statement.setString(1, username);
      resultSet = statement.executeQuery();

      if(resultSet.next()) {
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword("");

        ImageDAO imageDAO = new ImageDAO(dbConn);
        Image image = imageDAO.getById(resultSet.getInt("pfp_id"));

        user.setProfilePic(image);
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

    return users;
  }
}
