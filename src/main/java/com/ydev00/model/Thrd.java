package com.ydev00.model;

import java.util.ArrayList;

public class Thrd {
    private int id;
    private ArrayList<Thrd> thrdList = new ArrayList<>();
    private String content;

    public Thrd() {
    }

    public Thrd(int id, ArrayList<Thrd> thrdList, String content) {
        this.id = id;
        this.thrdList = thrdList;
        this.content = content;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ArrayList<Thrd> getPostList() {
        return thrdList;
    }
    public void setPostList(ArrayList<Thrd> thrdList) {
        this.thrdList = thrdList;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
