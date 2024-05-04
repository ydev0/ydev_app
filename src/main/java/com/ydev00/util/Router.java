package com.ydev00.util;

import static spark.Spark.*;
import com.ydev00.controller.UserController;

public class Router {

  public Router() {

  }

  UserController userController = new UserController();

  public final void setupPorts() {
    post("/login", userController.login);
  }
}
