package com.ydev00.model;

import java.io.File;
import java.util.ArrayList;

public class Article extends Thrd {
    private File markdown;

    public Article() {
        super();
    }

    public Article(int id, ArrayList<Thrd> thrdList, String content, File markdown) {
        super(id, thrdList, content);
        this.markdown = markdown;
    }

    public File getMarkdown() {
        return markdown;
    }

    public void setMarkdown(File markdown) {
        this.markdown = markdown;
    }
}
