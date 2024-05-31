package com.ydev00.dao;

import com.ydev00.model.image.Image;
import com.ydev00.model.image.ImageData;

import java.sql.*;
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

      query = "insert into image (type, image, width, height) values (?, ?, ?, ?)";
      statement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, image.getType());

      Blob blob = image.getImage().toBlob();
      statement.setBlob(2, blob);

      statement.setInt(3, image.getWidth());
      statement.setInt(4, image.getHeight());

      statement.execute();
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
        image.setWidth(resultSet.getInt("width"));
        image.setHeight(resultSet.getInt("height"));

        ImageData imageData = new ImageData();
        image.setImage(imageData.blobToImageData((resultSet.getBlob("image"))));
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
        image.setImage(image.getImage().blobToImageData(resultSet.getBlob("image")));
        image.setWidth(resultSet.getInt("width"));
        image.setHeight(resultSet.getInt("height"));
        images.add(image);
      }
    }
    catch (Exception ex) {
      System.err.println("Image not found: "+ex.getMessage());
    }
    return images;
  }

  @Override
  public Object delete(Object obj) {
    Image image = (Image) obj;
    try {
      query = "delete from image where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, image.getId());
      statement.execute();
      return true;
    } catch (Exception ex) {
      System.err.println("Image not deleted: "+ex.getMessage());
      return false;
    }
  }

  @Override
  public Object update(Object obj) {
    return null;
  }
}
