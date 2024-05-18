package com.ydev00.model.user;

import com.google.gson.annotations.SerializedName;
import com.ydev00.model.image.Image;

public abstract class UserAbstract {
  @SerializedName("id")
  private int id;

  @SerializedName("username")
  private String username;

  @SerializedName("email")
  private String email;

  @SerializedName("password")
  private String password;

  @SerializedName("profilePic")
  private Image profilePic;

  @SerializedName("auth")
  private boolean auth;

  @SerializedName("root")
  private boolean root;

  public void setId(int id) {
    this.id = id;
  }

  public void setUsername(String userName) {
    this.username = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setProfilePic(Image pfp) {
    this.profilePic = pfp;
  }

  public void setAuth(boolean auth) {
    this.auth = auth;
  }

  public void setRoot(boolean root) {
    this.root = root;
  }

  public int getId() {
    return id;
  }

  public Image getProfilePic() {
    return profilePic;
  }

  public String getUsername(){
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean getAuth() {
    return auth;
  }

  public boolean isRoot() {
    return root;
  }
}
