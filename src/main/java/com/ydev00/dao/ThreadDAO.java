package com.ydev00.dao;

import com.ydev00.model.Thrd;
import com.ydev00.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThreadDAO implements DAO{private Connection dbConn;
  private String query;
  private PreparedStatement statement;
  private ResultSet resultSet;

  public ThreadDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }

  @Override
  public Object create(Object obj) {
    return 0;
  }

  @Override
  public Object get(Object obj) {
    return null;
  }

  @Override
  public List<Thrd> getAll() {
    try {
      query = "select * from posts";



    } catch (Exception ex) {
      System.err.println("Could not get threads: "+ex.getMessage());
    }

    return new ArrayList<Thrd>();
  }

  public List<Thrd> getByUser(User user) {
    List<Thrd> thrdList = new ArrayList<>();
    try {
      query = "select * from posts where usr_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      resultSet = statement.executeQuery();


      while(resultSet.next()) {
        Thrd thrd = new Thrd();
        thrd.setId(resultSet.getInt("id"));
        thrd.setContent(resultSet.getString("content"));
        thrdList.add(thrd);
      }
    } catch (Exception ex) {
      System.err.println("Could not get threads by user: "+ex.getMessage());
    }
    return thrdList;
  }

  public Object delete(Object obj) {
    try {
      Thrd thrd = (Thrd) obj;
      query = "delete from posts where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thrd.getId());
      resultSet = statement.executeQuery();

      if(resultSet.next()) {
        thrd.setId(resultSet.getInt("id"));
      }

      return thrd;
    } catch (Exception ex) {
      System.err.println("Post not found: "+ex.getMessage());
    }
    return null;
  }
}
