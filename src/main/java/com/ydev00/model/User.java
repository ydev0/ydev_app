package com.ydev00.model;

import com.google.gson.annotations.SerializedName;

public class User {
  private int id;       
  private String name;   
  private String username;
  private String password; 
  private Image profilePic;

  private boolean auth;

  public User() {
  }

  public User(int id, String name, String userName, String password, Image profilePic) {
    this.id = id;
    this.name = name;
    this.username = userName;
    this.password = password;
    this.profilePic = profilePic;
  }

  public User createGenericUser() {
    User user = new User(0, "Carlos", "CarlosRoot", "password", null);
    return user;
  } 

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUsername(String userName) {
    this.username = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setProfilePic(Image pfp) {
    this.profilePic = pfp;
  }

  public void setAuth(boolean auth) {
    this.auth = auth;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }
}
