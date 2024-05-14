package com.ydev00.dao;

import com.ydev00.model.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ImageDAO implements DAO{
  private Connection dbConn;
  private String query;
  private PreparedStatement statement ;
  private ResultSet resultSet;

  public ImageDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }

  @Override
  public Image create(Object obj) {
    Image image = (Image) obj;

    return image;
  }

  @Override
  public Image get(Object obj){
    Image image = (Image) obj;
    try {
      query = "select * from image where id = ?";

      statement = dbConn.prepareStatement(query);
      statement.setInt(1, image.getId());

      resultSet = statement.executeQuery();

      if(resultSet.next()) {
        image.setImage(null); // convert blob to file -> resultSet.getBlob("image")
      }
      return image;
    } catch (Exception ex) {
      System.err.println("Image not found: "+ex.getMessage());
    }
    return null;
  }

  @Override
  public List<?> getAll() {
    return List.of();
  }


} 
