// NOT DONE YET

package com.ydev00.util;

import com.ydev00.model.*;
import com.google.gson.Gson;


public class Firebase {
  private Gson gson;
  private FirebaseConfig fbCfg;

  public Firebase() {
    this.gson = new Gson();
  }

  public Message fetch(String uri, String method, String reqBody) {
    String newUri = uri + "?key=" + fbCfg.getAPIKey();

    Fetcher fetcher = new Fetcher(fbCfg.getHost());

    return fetcher.fetch(method, newUri, reqBody);
  }

  public FirebaseUser login(User user) {
    String reqBody = gson.toJson(user, User.class);
    
    Message status = fetch("/accounts:signInWithPassword", "POST", reqBody);
    if (status.getType() != "INFO") {
      System.out.println("Could not login user on firebase - " + status.getMessage());

      return null;
    }
    
    FirebaseUser fb = gson.fromJson(status.getMessage(), FirebaseUser.class);

    return fb;
  }
}
