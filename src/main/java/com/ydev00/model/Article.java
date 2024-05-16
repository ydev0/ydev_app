package com.ydev00.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Blob;
import java.util.ArrayList;

public class Article extends Thrd {
    @SerializedName("markdown")
    private Blob markdown;

    @SerializedName("title")
    private String title;

    public Article() {
        super();
    }

    public Article(int id, String text,  ArrayList<Thrd> thrdList, String title, Blob markdown) {
        super(id, text, thrdList);
        this.title = title;
        this.markdown = markdown;
    }

    public Blob getMarkdown() {
        return markdown;
    }

    public String getTitle() {
        return title;
    }

    public void setMarkdown(Blob markdown) {
        this.markdown = markdown;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
