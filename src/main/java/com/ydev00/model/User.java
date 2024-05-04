package com.ydev00.model;

public class User {
  private int id;       
  private String name;   
  private String userName;
  private String password; 
  private boolean isLogged; 

  //constructors
  public User() {
  }

  public User(int id, String name, String userName, String password) {
    this.id = id;
    this.name = name;
    this.userName = userName;
    this.password = password;
  }

  // methods
  public User createUser() {
    User user = new User(0, "Carlos", "CarlosRoot", "password");
    user.isLogged = true;
    return user;
  } 

  // getters and setters
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
