package com.ydev00.dao;

import com.ydev00.model.thread.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ArticleDAO {
  private Connection dbConn;
  private String query;
  private PreparedStatement statement;
  private ResultSet resultSet;

  public ArticleDAO() {
  }

  public ArticleDAO(Connection dbConn) {
    this.dbConn = dbConn;
  }

  public Object create(Object obj) {
    Article article = (Article) obj;
    try {
      query = "insert into article(title, markdown) values (?, ?);";
      statement = dbConn.prepareStatement(query);
      statement.setString(1, article.getTitle());
      statement.setString(2, article.getMarkdown());
      statement.execute();
      resultSet = statement.getGeneratedKeys();

      if(resultSet.next()) {
        article.setId(resultSet.getInt(1));
      }
    } catch (Exception ex) {
      System.err.println("Could not create article: "+ex.getMessage());
      return null;
    }
    return article;
  }

  public Object get(Object obj) {
    Article article = (Article) obj;
    try {
      query = "select * from article where id = ?";
      statement = dbConn.prepareStatement(query);
      statement.setInt(1, article.getId());
      statement.execute();
      resultSet = statement.getResultSet();

      if(resultSet.next()) {
        article.setTitle(resultSet.getString("title"));
        article.setMarkdown(resultSet.getString("markdown"));
      }
    } catch (Exception ex) {
      System.err.println("Could not get article: "+ex.getMessage());
      return null;
    }
    return article;
  }
}
