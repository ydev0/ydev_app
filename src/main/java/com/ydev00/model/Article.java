package com.ydev00.model;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.ArrayList;

public class Article extends Thrd {
    @SerializedName("markdown")
    private File markdown;

    @SerializedName("title")
    private String title;

    public Article() {
        super();
    }

    public Article(int id, ArrayList<Thrd> thrdList, String content, File markdown) {
        super(id, thrdList, content);
        this.title = title;
        this.markdown = markdown;
    }

    public File getMarkdown() {
        return markdown;
    }

    public String getTitle() {
        return title;
    }

    public void setMarkdown(File markdown) {
        this.markdown = markdown;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
