package com.ydev00.dao;

import com.ydev00.model.user.User;
import com.ydev00.model.image.Image;
import com.ydev00.model.user.UserAbstract;

import java.sql.*;

import java.util.*;

/**
 * Classe para operações de acesso a dados relacionadas a usuários.
 */
public class UserDAO implements DAO{
  private final Connection dbConn;
  private String query;
  private PreparedStatement statement ;
  private ResultSet resultSet;

  public UserDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }

  /**
   * Cria um novo usuário no banco de dados.
   *
   * @param obj O objeto representando o usuário a ser criado.
   * @return O objeto User criado.
   */
  public User create(Object obj) {
    User user = (User) obj;
    ImageDAO imageDAO = new ImageDAO(dbConn);
    try {
      query = "insert into user (username, email, password, root, pfp_id) values (?, ?, ?, false , ?);";

      Image image = new Image(user.getProfilePic().getType(), user.getProfilePic().getImage());
      image = imageDAO.create(image);
      user.setProfilePic(image);

      statement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getEmail());
      statement.setString(3, user.getPassword());
      statement.setInt(4, image.getId());
      statement.execute();
      resultSet = statement.getGeneratedKeys();

      if (resultSet.next()) {
        user.setId(resultSet.getInt(1));
      }

    } catch (Exception ex) {
      System.err.println("Could not signup: "+ex.getMessage());
      return null;
    }
    return user;
  }

  @Override
  public List<?> getAll() {
    return List.of();
  }


  @Override
  public Object get(Object obj) {
    return null;
  }

  /**
   * Obtém um usuário do banco de dados com base no ID ou no email fornecido.
   *
   * @param obj O objeto contendo o ID ou email do usuário a ser obtido.
   * @return O objeto User obtido.
   * @throws SQLException Se ocorrer um erro durante a execução da consulta SQL.
   */
  public <T extends UserAbstract> T get(T user) throws SQLException {
    try {
      if(user.getEmail() != null) {
        query = "SELECT * FROM user WHERE email = ?;";
        statement = dbConn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getEmail());
        System.out.println("Getting user by email");
      }
      if(user.getId() != 0) {
        query = "SELECT * FROM user WHERE id = ?;";
        statement = dbConn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, user.getId());
        System.out.println("Getting user by id");
      }

      statement.execute();
      resultSet = statement.getResultSet();

      if(resultSet.next()) {
        if (!(resultSet.getString("password").equals(user.getPassword())) && user.getEmail() != null) {
          throw new Exception("Password does not match!");
        }
        user.setId(resultSet.getInt("id"));
        user.setEmail(resultSet.getString("email"));
        user.setUsername(resultSet.getString("username"));
        user.setRoot(resultSet.getBoolean("root"));
        user.setProfilePic(new Image(resultSet.getInt("pfp_id")));
        user.setPassword("");
      }
    } catch (Exception e) {
      System.err.println("User not found! " +e.getMessage());
      return null;
    }
    return user;
  }

  /**
   * Obtém um usuário do banco de dados com base no nome de usuário fornecido.
   *
   * @param user O objeto User contendo o nome de usuário a ser obtido.
   * @return O objeto User obtido.
   */
  public <T extends User> Object getByUsername(T user) {
    try {
      query = "SELECT * FROM user WHERE username = ?;";

      statement = dbConn.prepareStatement(query);
      statement.setString(1, user.getUsername());
      resultSet = statement.executeQuery();

      if(resultSet.next()) {
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword("");
        user.setProfilePic(new Image(resultSet.getInt("pfp_id")));
        user.setRoot(resultSet.getBoolean("root"));
        user.setAuth(false);
      }
    }
    catch (Exception ex) {
      System.err.println("User not found!" + ex.getMessage());
      return null;
    }
    return user;
  }

  /**
   * Lista todos os usuários do banco de dados.
   *
   * @return Uma lista de objetos User representando todos os usuários no banco de dados.
   */
  public List<User> listUsers(){
    List<User> users = new ArrayList<>();
    try {
      query = "select * from user";
      statement = dbConn.prepareStatement(query);
      statement.execute();
      resultSet = statement.getResultSet();

      while(resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword("");
        user.setRoot(resultSet.getBoolean("root"));
        user.setProfilePic(new Image(resultSet.getInt("pfp_id")));
        user.setAuth(false);
        users.add(user);
      }
    } catch (Exception ex) {
      System.err.println("Could not get users: "+ex.getMessage());
      return null;
    }
    return users;
  }

  /**
   * Exclui um usuário do banco de dados.
   *
   * @param obj O objeto User a ser excluído.
   * @return Sempre retorna null, pois o método não está configurado para retornar um objeto após a exclusão.
   */
  @Override
  public Object delete(Object obj) {
    User user = (User) obj;
    try {
      user = (User) getByUsername(user);

      if (user == null) {
        throw new Exception("User not found");
      }

      ImageDAO imageDAO = new ImageDAO(dbConn);
      Image image = (Image) imageDAO.delete(user.getProfilePic());
      if(image == null)
        user.setProfilePic(null);

      query = "delete from user where id = ?";
      statement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      statement.setInt(1, user.getId());
      statement.execute();
      resultSet = statement.getGeneratedKeys();

    } catch (Exception ex) {
      System.err.println("User not found: "+ex.getMessage());
      return user;
    }
    return null;
  }

  @Override
  public Object update(Object obj) {
    User user = (User) obj;
    try {
      query = "update user set username = ?, email = ?, password = ?, pfp_id = ? where id = ?;";
      statement = dbConn.prepareStatement(query);
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getEmail());
      statement.setString(3, user.getPassword());
      statement.setInt(4, user.getProfilePic().getId());
      statement.setInt(5, user.getId());
      statement.execute();
      resultSet = statement.getGeneratedKeys();

      if(resultSet.next()) {
        user.setId(resultSet.getInt(1));
      }
    } catch (Exception ex) {
      System.err.println("Could not update user: "+ex.getMessage());
      return null;
    }
    return user;
  }
}
