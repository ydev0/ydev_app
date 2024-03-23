package com.ydev00;

import java.io.*;
import static spark.Spark.*;

public class Server {
 
  public void helloWorld(){
    get("/hello", (req, res) -> "Hello, World!");
  }
}
