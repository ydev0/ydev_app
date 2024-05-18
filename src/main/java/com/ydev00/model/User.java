package com.ydev00.model;

import com.google.gson.annotations.SerializedName;

public class User extends UserAbstract {

  public User() {
  }

  public User(String username) {
    setUsername(username);
  }

  public User(int id) {
    setId(id);
  }

  public User(String username, String email, String password, Image profilePic) {
    setUsername(username);
    setEmail(email);
    setPassword(password);
    setProfilePic(profilePic);
    setAuth(false);
  }

  public User( String email, String password) {
    setEmail(email);
    setPassword(password);
    setAuth(false);
  }

  public User(int id,  String username, String email, String password, Image profilePic, boolean auth) {
    setUsername(username);
    setEmail(email);
    setPassword(password);
    setProfilePic(profilePic);
    setAuth(auth);
    setRoot(false);
  }

  public User(int id,  String username, String email, String password, Image profilePic, boolean auth, boolean root) {
    setUsername(username);
    setEmail(email);
    setPassword(password);
    setProfilePic(profilePic);
    setAuth(auth);
    setRoot(root);
  }

  public String toString() {
    return "User {" +
            "id=" + getId() +
            ", username='" + getUsername() + '\'' +
            ", email='" + getEmail()+ '\'' +
            ", password='" + getPassword()+ '\'' +
            ", pfp= '" +getProfilePic()+ '\'' +
            ", auth= '" +getAuth()+ '\'' +
            '}';
  }
}