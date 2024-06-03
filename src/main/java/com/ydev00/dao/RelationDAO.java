package com.ydev00.dao;

import com.ydev00.model.image.Image;
import com.ydev00.model.user.User;
import com.ydev00.model.thread.Thrd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe para operações de acesso a dados relacionadas a relações de usuário.
 */
public class RelationDAO {
  private final Connection dbConn;
  private String query;
  private PreparedStatement statement ;
  private ResultSet resultSet;
  private UserDAO userDAO;
  private ImageDAO imageDAO;
  private ThreadDAO threadDAO;

  public RelationDAO(Connection dbConn) {
    this.dbConn = dbConn;
    this.userDAO = new UserDAO(dbConn);
    this.imageDAO = new ImageDAO(dbConn);
    this.threadDAO = new ThreadDAO(dbConn);
  }

  /**
   * Segue um usuário.
   *
   * @param usr  O nome de usuário que está seguindo.
   * @param flwr O nome de usuário que está sendo seguido.
   */
  public void follow (String usr, String flwr) {
    try {
      User user = (User)userDAO.getByUsername(new User(usr));
      User flwdUsr = (User)userDAO.getByUsername(new User(flwr));

      if(user == null || flwdUsr == null) {
        throw new Exception("User not found");
      }

      if (user.getId()== flwdUsr.getId()) {
        throw new Exception("Cannot follow yourself");
      }

      query = "insert into usr_flw (usr_id, flw_id) values (?, ?)";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      statement.setInt(2, flwdUsr.getId());
      statement.execute();


    } catch (Exception ex) {
      System.err.println("Could not follow: "+ex.getMessage());
    }
    System.out.println("Followed");
  }


  /**
   * Deixa de seguir um usuário.
   *
   * @param usr  O nome de usuário que está deixando de seguir.
   * @param flwr O nome de usuário que estava sendo seguido.
   */
  public void unfollow (String usr, String flwr) {
    try {
      User user = (User)userDAO.getByUsername(new User(usr));
      User flwdUsr = (User)userDAO.getByUsername(new User(flwr));

      if(user == null || flwdUsr == null) {
        throw new Exception("User not found");
      }

      if(usr.equals(flwr)) {
        throw new Exception("Cannot unfollow yourself");
      }

      query = "delete from usr_flw where usr_id = ? and flw_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      statement.setInt(2, flwdUsr.getId());
      statement.execute();
    } catch (Exception ex) {
      System.err.println("Could not follow: "+ex.getMessage());
    }
    System.out.println("Unfollowed");
  }


  /**
   * Obtém os seguidores de um usuário.
   *
   * @param obj O objeto User para o qual os seguidores estão sendo obtidos.
   * @return Uma lista de usuários que seguem o usuário especificado.
   */
  public List<User> getFollowers (Object obj) {
    User user = (User)obj;
    List<User> users = new ArrayList<>();
    try {

      query = "select * from usr_flw where usr_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, (user.getId()));
      statement.execute();
      resultSet = statement.getResultSet();

      while(resultSet.next()) {
        User follower = userDAO.get(new User(resultSet.getInt("flw_id")));
        user.setProfilePic(imageDAO.get(user.getProfilePic()));
        users.add(follower);
      }
    } catch (Exception ex) {
      System.err.println("Could not get followers: "+ex.getMessage());
      return null;
    }
    return users;
  }

  /**
   * Obtém os seguidos de um usuário.
   *
   * @param obj O objeto User para o qual os usuários seguidos estão sendo obtidos.
   * @return Uma lista de usuários que são seguidos pelo usuário especificado.
   */
  public List<User> getFollowees (Object obj) {
    User user = (User)obj;
    List<User> users = new ArrayList<>();
    try {

      query = "select * from usr_flw where flw_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, (user.getId()));
      statement.execute();
      resultSet = statement.getResultSet();

      while(resultSet.next()) {
        User follower = userDAO.get(new User(resultSet.getInt("usr_id")));
        user.setProfilePic(imageDAO.get(user.getProfilePic()));
        users.add(follower);
      }
    } catch (Exception ex) {
      System.err.println("Could not get followers: "+ex.getMessage());
      return null;
    }
    return users;
  }

  /**
   * Dá um like em uma thread.
   *
   * @param thrd a thread qual o like está sendo dado.
   * @param user O usuário que está dando o like.
   * @return true se o like foi dado com sucesso, false caso contrário.
   */
  public boolean like (Thrd thrd, User user) {
    try {
      query = "insert into usr_lk(thrd_id, usr_id) values (?, ?)";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thrd.getId());
      statement.setInt(2, user.getId());
      statement.execute();
    }
    catch (Exception ex) {
      System.err.println("Could not like: "+ex.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Remove um like de uma thread.
   *
   * @param thrd A thread do qual o like está sendo removido.
   * @param user O usuário que está removendo o like.
   * @return true se o like foi removido com sucesso, false caso contrário.
   */
  public boolean unlike (Thrd thrd, User user) {
    try {
      query = "delete from usr_lk where thrd_id = ? and usr_id = ?;";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thrd.getId());
      statement.setInt(2, user.getId());
      statement.execute();
    }
    catch (Exception ex) {
      System.err.println("Could not like: "+ex.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Liga duas threads.
   *
   * @param main A thread principal ao qual a outra thread será ligada.
   * @param assoc A thread que será ligada à thread principal.
   * @return true se as threads foram ligados com sucesso, false caso contrário.
   */
  public boolean link (Thrd main, Thrd assoc) {
    try {
      query = "insert into thrd_lst(main_id, assoc_id) values (?, ?)";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, main.getId());
      statement.setInt(2, assoc.getId());
      statement.execute();
    }
    catch (Exception ex) {
      System.err.println("Could not link: "+ex.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Desliga duas threads que estão ligadas.
   *
   * @param main A thread principal do qual a outra thread será desligada.
   * @param assoc A thread que será desligado da thread principal.
   * @return true se as threads foram desligadas com sucesso, false caso contrário.
   */
  public boolean unlink (Thrd main, Thrd assoc) {
    try {
      query = "delete from thrd_lst where main_id = ? and assoc_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, main.getId());
      statement.setInt(2, assoc.getId());
      statement.execute();
    }
    catch (Exception ex) {
      System.err.println("Could not unlink: "+ex.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Obtém uma lista de threads ligados a uma thread específico.
   *
   * @param thrd A thread para o qual as threads ligadas estão sendo obtidos.
   * @return Uma lista de threads ligados á thread especificada.
   */
  public List<Thrd> getLinkedThreads(Thrd thrd) {
    List<Thrd> thrds = new ArrayList<>();
    try {
      query = "select * from thrd_lst where main_id = ?;";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thrd.getId());
      statement.execute();
      resultSet = statement.getResultSet();

      while (resultSet.next()) {
        Thrd assoc = new Thrd();
        assoc.setId(resultSet.getInt("assoc_id"));
        assoc = (Thrd) threadDAO.get(assoc);
        System.out.println(assoc.toString());
        thrds.add(assoc);
      }
    }
    catch (Exception ex) {
      System.err.println("Could not get linked threads: "+ex.getMessage());
      return null;
    }
    return thrds;
  }
}
