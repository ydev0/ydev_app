package com.ydev00.model.thread;

import com.google.gson.annotations.SerializedName;

public class Article {
  @SerializedName("id")
  private int id;

  @SerializedName("title")
  private String title;

  @SerializedName("markdown")
  private String markdown;

  public Article() {
    super();
  }

  public Article(int id ) {
    this.id = id;
  }

  public Article(String title, String markdown) {
    this.title = title;
    this.markdown = markdown;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMarkdown() {
    return markdown;
  }

  public void setMarkdown(String markdown) {
    this.markdown = markdown;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String toString() {
    return "Article {" +
            ", title='" + title + '\'' +
            ", markdown='" + markdown + '\'' +
            '}';
  }
}