package com.ydev00.model;

import java.io.File;

import com.google.gson.annotations.SerializedName;

public class Image {
  @SerializedName("id")
  private int id;

  @SerializedName("image")
  private File image;

  public Image() {}

  public Image(int id) {
    this.id = id;
  }
  public Image(int id, File image) {
    this.id = id;
    this.image = image;
  }

  public int getId() {
    return id;
  }
  public File getImage() {
    return image;
  }

  public void setId(int id) { 
    this.id = id;
  }

  public void setImage(File image) {
    this.image = image;
  }
}
