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
    Thrd thrd = (Thrd) obj;
    try {

      if(thrd.getArticle() != null)
        query = "insert into thread (text, usr_id, article_id) values (?, ?, ?); ";
      else
        query = "insert into thread (text, usr_id) values (?, ?); ";

      statement = dbConn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      statement.setString(1, thrd.getText());
      statement.setInt(2, thrd.getUser().getId());

      if(thrd.getArticle() != null) {
        ArticleDAO articleDAO = new ArticleDAO(dbConn);
        Article article = (Article) articleDAO.create(new Article(thrd.getArticle().getTitle(), thrd.getArticle().getMarkdown()));
        thrd.setArticle(article);
        statement.setInt(3, thrd.getArticle().getId());
        System.out.println("Article created");
      }
        

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
      statement.execute();
      resultSet = statement.getResultSet(); 

      while(resultSet.next()) {
        thread.setId(resultSet.getInt("id"));
        thread.setText(resultSet.getString("text"));
        UserDAO userDAO = new UserDAO(dbConn);
        thread.setUser(userDAO.get(new User(resultSet.getInt("usr_id"))));

        if(resultSet.getInt("article_id")!= 0) {
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
        UserDAO userDAO = new UserDAO(dbConn);
        thrd.setUser(userDAO.get(new User(resultSet.getInt("usr_id"))));

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
        UserDAO userDAO = new UserDAO(dbConn);
        thrd.setUser((User) userDAO.get(new User(resultSet.getInt("usr_id"))));

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

  public List<Thrd> getByUser(User user, int max) {
    List<Thrd> thrdList = new ArrayList<>();
    try {
      query = "select * from thread where usr_id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, user.getId());
      statement.execute();
      resultSet = statement.getResultSet();

      while(resultSet.next() && thrdList.size() < max) {
        Thrd thrd = new Thrd();
        thrd.setId(resultSet.getInt("id"));
        thrd.setText(resultSet.getString("text"));
        UserDAO userDAO = new UserDAO(dbConn);
        thrd.setUser(userDAO.get(new User(resultSet.getInt("usr_id"))));
        if(resultSet.getInt("article_id") != 0) {
          ArticleDAO articleDAO = new ArticleDAO(dbConn);
          Article article = (Article) articleDAO.get(new Article(resultSet.getInt("article_id")));
          thrd.setArticle(article);
        }
        thrdList.add(thrd);
      }
    } catch (Exception ex) {
      System.err.println("Could not get threads: "+ex.getMessage());
    }
    return thrdList;
  }

  @Override
  public Object delete(Object obj) {
    Thrd thrd = (Thrd) obj;
    try {
      if(thrd.getArticle() != null) {
        ArticleDAO articleDAO = new ArticleDAO(dbConn);
        articleDAO.delete(thrd.getArticle());
      }

      query = "delete from thread where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, thrd.getId());
      statement.execute();
      resultSet = statement.getResultSet();
    } catch (Exception ex) {
      System.err.println("Could not delete thread: "+ex.getMessage());
      return thrd;
    }
    return null;
  }

  @Override
  public Object update(Object obj) {
    return null;
  }
}
