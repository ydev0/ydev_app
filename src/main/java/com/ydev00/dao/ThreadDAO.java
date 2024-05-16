package com.ydev00.dao;

import com.ydev00.model.Article;
import com.ydev00.model.Thrd;
import com.ydev00.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThreadDAO implements DAO{
  private Connection dbConn;
  private String query;
  private PreparedStatement statement;
  private ResultSet resultSet;

  public ThreadDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }

  @Override
  public Object create(Object obj) {
    return null;
  }

  public Object create(Object obj, int id) {
    Article article = (Article) obj;
    if(article.getMarkdown() != null) {
      ArticleDAO articleDAO = new ArticleDAO(dbConn);
      return articleDAO.create(article, id);
    }
    try {
      query = "insert into thread (text, usr_id) values (?, ?) returning id;";
      statement = dbConn.prepareStatement(query);
      statement.setString(1, article.getText());
      statement.setInt(2, id);
      resultSet = statement.executeQuery();

      if(resultSet.next()) {
        article.setId(resultSet.getInt(1));
      }
    } catch (Exception ex) {
      System.err.println("Could not create thread: "+ex.getMessage());
    }
    return 0;
  }

  @Override
  public Object get(Object obj) {
    Article article = (Article) obj;
    try {
      query = "select * from thread where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, article.getId());
      resultSet = statement.executeQuery();

      while(resultSet.next()) {
        article.setId(resultSet.getInt("id"));
        article.setText(resultSet.getString("text"));
        if(resultSet.getBlob("content") != null) {
          article.setTitle(resultSet.getString("title"));
          article.setMarkdown(resultSet.getBlob("content"));
        }
        else {
          article.setTitle(null);
          article.setMarkdown(null);
        }
      }
    } catch (Exception ex) {
      System.err.println("Could not get threads:"+ex.getMessage());
    }
    return article;
  }

  @Override
  public List<?> getAll() {
    List<Thrd> thrdList = new ArrayList<>();
    try {
      query = "select * from thread;";
      statement = dbConn.prepareStatement(query);
      resultSet = statement.executeQuery();

      while(resultSet.next()) {
        Article thrd = new Article();
        thrd.setId(resultSet.getInt("id"));
        thrd.setText(resultSet.getString("text"));
        if(resultSet.getBlob("content") != null) {
          thrd.setTitle(resultSet.getString("title"));
          thrd.setMarkdown(resultSet.getBlob("content"));
        }
        else {
          thrd.setTitle(null);
          thrd.setMarkdown(null);
        }
        thrdList.add(thrd);
      }
    } catch (Exception ex) {
      System.err.println("Could not get threads: "+ex.getMessage());
    }
    return thrdList;
  }

  public List<?> getByUser(User user) {
    List<Thrd> thrdList = new ArrayList<>();
    try {
      query = "select * from thread where usr_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      resultSet = statement.executeQuery();

      while(resultSet.next()) {
        Article thrd = new Article();
        thrd.setId(resultSet.getInt("id"));
        thrd.setText(resultSet.getString("text"));
        if(resultSet.getBlob("content") != null) {
          thrd.setTitle(resultSet.getString("title"));
          thrd.setMarkdown(resultSet.getBlob("content"));
        }
        else {
          thrd.setTitle(null);
          thrd.setMarkdown(null);
        }
        thrdList.add(thrd);
      }
    } catch (Exception ex) {
      System.err.println("Could not get threads: "+ex.getMessage());
    }
    return thrdList;
  }

  public Object delete(Object obj) {
    try {
      Thrd thrd = (Thrd) obj;
      query = "delete from thread where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thrd.getId());
      resultSet = statement.executeQuery();
      return thrd;
    } catch (Exception ex) {
      System.err.println("Post not found: "+ex.getMessage());
    }
    return null;
  }
}
