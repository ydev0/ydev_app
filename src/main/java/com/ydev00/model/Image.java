package com.ydev00.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Blob;
public class Image {
  @SerializedName("id")
  private int id;

  @SerializedName("type")
  private String type;

  @SerializedName("image")
  private Blob image;

  public Image() {}

  public Image(int id) {
    this.id = id;
  }

  public Image(String type, Blob image) {
    this.type = type;
    this.image = image;
  }

  public Image(int id, String type, Blob image, int width, int height) {
    this.id = id;
    this.type = type;
    this.image = image;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Blob getImage() {
    return image;
  }

  public void setImage(Blob image) {
    this.image = image;
  }
}