package com.ydev00.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Thrd {
    @SerializedName("id")
    private int id;

    @SerializedName("text")
    private String text;

    @SerializedName("thrdList")
    private ArrayList<Thrd> thrdList = new ArrayList<>();

    public Thrd() {
    }

    public Thrd(int id, String text, ArrayList<Thrd> thrdList) {
        this.id = id;
        this.text = text;
        this.thrdList = thrdList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Thrd> getThrdList() {
        return thrdList;
    }

    public void setThrdList(ArrayList<Thrd> thrdList) {
        this.thrdList = thrdList;
    }
}
