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

  public Object create(Object obj, int id) {
    Article article = (Article) obj;
    try {
      query = "insert into thread (text, title, content, usr_id) values (?, ?, ?, ?) returning id;";
      statement = dbConn.prepareStatement(query);
      statement.setString(1, article.getText());
      statement.setString(2, article.getTitle());
      statement.setBlob(3, article.getMarkdown());
      statement.setInt(4, id);
      resultSet = statement.executeQuery();

      if(resultSet.next()) {
        article.setId(resultSet.getInt(1));
      }
    } catch (Exception ex) {
      System.err.println("Could not create article: "+ex.getMessage());
    }
    return article;
  }
}
