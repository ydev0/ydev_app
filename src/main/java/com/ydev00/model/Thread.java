package com.ydev00.model;

import java.util.*;

public class Thread {
  private int id;
  private ArrayList<Thread> threadList = new ArrayList<>();
  private String content;

  public void setId(int id) {
    this.id = id;
  }
  public void setThreadList(ArrayList<Thread> threadList) {
    this.threadList = threadList;
  }
  public void setContent(String content){
    this.id = id;
  }

}
