package com.ydev00.dao;

import com.ydev00.model.thread.Article;
import com.ydev00.model.thread.Thrd;
import com.ydev00.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThreadDAO implements DAO{
  private final Connection dbConn;
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
    Thrd thrd = (Thrd) obj;
    try {
      query = "insert into thread (text, usr_id) values (?, ?); ";
      if(thrd.getArticle() != null) {
        ArticleDAO articleDAO = new ArticleDAO(dbConn);
        Article article = (Article) articleDAO.create(thrd);
        thrd.setArticle(article);
        query = "insert into thread (text, usr_id, article_id) values (?, ?, ?); ";
      }
      statement = dbConn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      statement.setString(1, thrd.getText());
      statement.setInt(2, id);
      if(thrd.getArticle() != null )
        statement.setInt(3, thrd.getArticle().getId());
      statement.execute();
      resultSet = statement.getGeneratedKeys();

      if(resultSet.next()) {
        thrd.setId(resultSet.getInt(1));
      }
    } catch (Exception ex) {
      System.err.println("Could not create thread: "+ex.getMessage());
      return null;
    }
    return thrd;
  }

  @Override
  public Object get(Object obj) {
    Thrd thread = (Thrd) obj;
    try {
      query = "select * from thread where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thread.getId());
      resultSet = statement.executeQuery();

      while(resultSet.next()) {
        thread.setId(resultSet.getInt("id"));
        thread.setText(resultSet.getString("text"));

        if(resultSet.getString("markdown") != null) {
          ArticleDAO articleDAO = new ArticleDAO(dbConn);
          Article article = (Article) articleDAO.get(new Article(resultSet.getInt("article_id")));
          thread.setArticle(article);
        }
      }
    } catch (Exception ex) {
      System.err.println("Could not get threads:"+ex.getMessage());
      return null;
    }
    return thread;
  }

  @Override
  public List<Thrd> getAll() {
    List<Thrd> thrdList = new ArrayList<>();
    Thrd thrd = new Thrd();

    try {
      query = "select * from thread;";
      statement = dbConn.prepareStatement(query);
      statement.execute();
      resultSet = statement.getResultSet();

      while(resultSet.next()) {
        thrd.setId(resultSet.getInt("id"));
        thrd.setText(resultSet.getString("text"));
        if(resultSet.getBlob("content") != null) {
          ArticleDAO articleDAO = new ArticleDAO(dbConn);
          Article article = (Article) articleDAO.get(new Article(resultSet.getInt("article_id")));
          thrd.setArticle(article);
        }
        thrdList.add(thrd);
      }
    } catch (Exception ex) {
      System.err.println("Could not get threads: "+ex.getMessage());
      return null;
    }
    return thrdList;
  }

  public List<Thrd> getByUser(User user) {
    List<Thrd> thrdList = new ArrayList<>();
    try {
      query = "select * from thread where usr_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      statement.execute();
      resultSet = statement.getResultSet();

      while(resultSet.next()) {
        Thrd thrd = new Thrd();
        thrd.setId(resultSet.getInt("id"));
        thrd.setText(resultSet.getString("text"));
        if(resultSet.getInt("article_id") != 0) {
          ArticleDAO articleDAO = new ArticleDAO(dbConn);
          Article article = (Article) articleDAO.get(new Article(resultSet.getInt("article_id")));
          thrd.setArticle(article);
        }
        thrdList.add(thrd);
      }
    } catch (Exception ex) {
      System.err.println("Could not get threads: "+ex.getMessage());
      return null;
    }
    return thrdList;
  }

  public Object delete(Object obj) {
    Thrd thrd = (Thrd) obj;
    try {
      query = "delete from thread where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thrd.getId());
      resultSet = statement.executeQuery();
    } catch (Exception ex) {
      System.err.println("Thread not found: "+ex.getMessage());
      return null;
    }
    return thrd;
  }
}
