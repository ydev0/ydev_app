package com.ydev00.model;

import com.google.gson.annotations.SerializedName;

public class ModUser extends User{
  public ModUser() {
    super();
  }

  public ModUser(int id, String username, String email, String password, Image profilePic, boolean auth) {
    super(id, username, email, password, profilePic, auth, true);
  }

}
