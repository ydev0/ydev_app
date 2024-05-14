package com.ydev00.controller;

import com.google.gson.Gson;
import com.ydev00.model.Article;
import spark.Route;

import java.sql.Connection;

public class ArticleController {
    private Connection dbConn;
    private Gson gson;

    public ArticleController(Connection dbConn) {
        this.dbConn = dbConn;
        this.gson = new Gson();
    }

    public Route create = (request, response) -> {
        return new Article();
    };
}
