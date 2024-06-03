package com.ydev00.dao;

import com.ydev00.model.image.Image;
import com.ydev00.model.image.ImageData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para operações de acesso a dados relacionadas a imagens.
 */
public class ImageDAO implements DAO{
  private Connection dbConn;
  private String query;
  private PreparedStatement statement ;
  private ResultSet resultSet;


  public ImageDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }

  /**
   * Cria uma nova entrada de imagem no banco de dados.
   *
   * @param obj O objeto Image a ser criado.
   * @return O objeto Image criado.
   */
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

  /**
   * Obtém uma imagem do banco de dados com base no ID fornecido.
   *
   * @param obj O objeto Image com o ID da imagem a ser obtida.
   * @return O objeto Image obtido.
   */
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

  /**
   * Obtém todas as imagens presentes no banco de dados.
   *
   * @return Uma lista contendo todas as imagens encontradas.
   */
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

  /**
   * Exclui uma imagem do banco de dados com base no objeto Image fornecido.
   *
   * @param obj O objeto Image a ser excluído.
   * @return O objeto Image excluído.
   */
  @Override
  public Object delete(Object obj) {
    Image image = (Image) obj;
    try {
      query = "delete from image where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, image.getId());
      statement.execute();
    } catch (Exception ex) {
      System.err.println("Image not deleted: "+ex.getMessage());
    } 
    return image;
  }

  /**
   * Método de atualização não implementado para a classe ImageDAO.
   *
   * @param obj O objeto Image a ser atualizado.
   * @return Sempre retorna null, pois a atualização não é suportada.
   */
  @Override
  public Object update(Object obj) {
    return null;
  }
}
