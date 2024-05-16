package com.ydev00.model;

import com.google.gson.annotations.SerializedName;

public class ModUser extends User{
  @SerializedName("root")
  private boolean root;

  public ModUser() {
    super();
  }

  public ModUser(int id, String username, String email, String password, Image profilePic) {
    super(id, username, email, password, profilePic);
    this.root = true;
  }

  public boolean isRoot() {
    return root;
  }

  public void setRoot(boolean root) {
    this.root = root;
  }
}
