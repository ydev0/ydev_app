package com.ydev00.model;

import com.google.gson.annotations.SerializedName;

public class ModUser extends User{
  @SerializedName("root")
  private boolean root;

  public ModUser() {
    super();
  }

  public ModUser(int id, String name, String username, String email, String password, Image profilePic) {
    super(id, name, username, email, password, profilePic);
    this.root = true;
  }


}
