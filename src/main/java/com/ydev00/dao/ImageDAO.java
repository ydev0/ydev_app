package com.ydev00.dao;

import com.ydev00.model.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImageDAO {
  private Connection dbConn;
  private String query;
  private PreparedStatement statement ;
  private ResultSet resultSet;

  public ImageDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }

  public Image getById(int id){
    Image image = new Image();
    try {
      query = "select * from image where id = ?";

      statement = dbConn.prepareStatement(query);
      statement.setInt(1, id);

      resultSet = statement.executeQuery();

      if(resultSet.next()) {
        image.setId(resultSet.getInt("id"));
        //  image.setImage(resultSet.getBlob("image")); 
        image.setImage(null);
        // TODO set image
      } 

      return image;

    } catch (Exception ex) {
      System.err.println("Image not found: "+ex.getMessage());
    }
    return null;
  }
} 
