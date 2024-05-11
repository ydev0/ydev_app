package com.ydev00.dao;

import com.ydev00.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ImageDAO {
  private Connection dbConn;

  public ImageDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }
  public Image getById(int id){
    return new Image();
  }


} 
