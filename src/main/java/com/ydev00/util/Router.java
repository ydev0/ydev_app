package com.ydev00.util;

import static spark.Spark.*;
import com.ydev00.controller.UserController;

public class Router {

  public static final void setupPorts() {
    post("/login", UserController.login());
  }
}
