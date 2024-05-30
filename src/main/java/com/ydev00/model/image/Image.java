package com.ydev00.model.image;

import com.google.gson.annotations.SerializedName;

public class Image {
  @SerializedName("id")
  private int id;

  @SerializedName("type")
  private String type;

  @SerializedName("image")
  private ImageData image;

  @SerializedName("width")
  private int width;

  @SerializedName("height")
  private int height;

  public Image() {}

  public Image(int id) {
    this.id = id;
  }

  public Image(String type, ImageData image) {
    this.type = type;
    this.image = image;
  }

  public Image(int id, String type, ImageData image, int width, int height) {
    this.id = id;
    this.type = type;
    this.image = image;
    this.width = width;
    this.height = height;
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

  public ImageData getImage() {
    return image;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setImage(ImageData image) {
    this.image = image;
  }

  public String toString() {
    return "Image {" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", image='" + image + '\'' +
            ", width='" + width + '\'' +
            ", height='" + height + '\'' +
            '}';
  }
}
