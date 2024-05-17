package com.ydev00.model;

import com.google.gson.annotations.SerializedName;

public class User extends UserAbstract {

  public User() {
  }

  public User(int id,  String username, String email, String password, Image profilePic) {
    setId(id);
    setUsername(username);
    setEmail(email);
    setPassword(password);
    setProfilePic(profilePic);
    setAuth(false);
  }

  public User(int id,  String username, String email, String password, Image profilePic) {
    setEmail(email);
    setPassword(password);
    setAuth(false);
  }

  public User(int id,  String username, String email, String password, Image profilePic) {
    setUsername(username);
    setEmail(email);
    setPassword(password);
    setProfilePic(profilePic);
    setAuth(false);
  }
}