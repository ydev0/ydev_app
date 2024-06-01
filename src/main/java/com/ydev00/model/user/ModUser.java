package com.ydev00.model.user;

import com.ydev00.model.image.Image;

public class ModUser extends User{
  public ModUser() {
    super();
  }
  public ModUser(String username) {
    super(username);
  }

  public ModUser(int id, String username, String email, String password, Image profilePic, boolean auth) {
    super(id, username, email, password, profilePic, auth, true);
  }

}
