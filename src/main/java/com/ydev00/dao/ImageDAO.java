package com.ydev00.dao;

import com.ydev00.model.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    try {
      if(image == null) {
        return get(new Image(0));
      }

      query = "insert into image (type, image) values (?, ?) returning  id";
      statement = dbConn.prepareStatement(query);
      statement.setString(1, image.getType());
      statement.setBlob(2, image.getImage());
      statement.executeQuery();
      resultSet = statement.getGeneratedKeys();

      if(resultSet.next()) {
        image.setId(resultSet.getInt(1));
      }
    } catch (Exception ex) {
      System.err.println("Image not created: "+ex.getMessage());
      return null;
    }
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
        image.setId(resultSet.getInt("id"));
        image.setType(resultSet.getString("type"));
        image.setImage(resultSet.getBlob("image"));
        return image;
      }
    } catch (Exception ex) {
      System.err.println("Image not found: "+ex.getMessage());
    }
    return null;
  }

  @Override
  public List<?> getAll() {
    List<Image> images = new ArrayList<>();
    try {
      query = "select * from image";
      statement = dbConn.prepareStatement(query);
      resultSet = statement.executeQuery();

      while(resultSet.next()) {
        Image image = new Image();
        image.setId(resultSet.getInt("id"));
        image.setType(resultSet.getString("type"));
        image.setImage(resultSet.getBlob("image"));
        images.add(image);
      }
    }
    catch (Exception ex) {
      System.err.println("Image not found: "+ex.getMessage());
    }
    return images;
  }
}