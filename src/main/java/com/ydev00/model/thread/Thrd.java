package com.ydev00.model.thread;

import com.google.gson.annotations.SerializedName;
import com.ydev00.model.user.User;

public class Thrd {
  @SerializedName("id")
  private int id;

  @SerializedName("text")
  private String text;

  @SerializedName("article")
  private Article article;

  @SerializedName("user")
  private User user;

  public Thrd() {
  }

  public Thrd(int id) {
    this.id = id;
  }

  public Thrd(String text) {
    this.text = text;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public Article getArticle() {
    return article;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String toString() {
    return "Thrd {" +
    "id=" + id +
    ", text='" + text + '\'' +
    ", article='" + article+ '\'' +
    ", user='" + user + '\'' +
    '}';
  }
}
