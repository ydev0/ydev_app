package com.ydev00.model;


public class User {
  private int id;           // user id
  private String name;      // user real name
  private String userName   // user userName
  private boolean isLogged; // checker for logged user

  //constructor

  public User(int id, String name) {
    this.id = id;
    this.name = name;

  }

  // methods
  public User login() {
    User user = new User(0, "CarlosRoot");


    return user;
  } 



  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
