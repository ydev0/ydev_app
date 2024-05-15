package com.ydev00.model;

import java.io.InputStream;

import com.google.gson.annotations.SerializedName;

public class Image {
  @SerializedName("id")
  private int id;

  @SerializedName("image")
  private InputStream image;

  public Image() {}

  public Image(int id) {
    this.id = id;
  }
  public Image(int id, InputStream image) {
    this.id = id;
    this.image = image;
  }

  public int getId() {
    return id;
  }
  public InputStream getImage() {
    return image;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setImage(InputStream image) {
    this.image = image;
  }
}