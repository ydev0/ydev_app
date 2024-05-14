package com.ydev00.controller;

import spark.Route;

import com.google.gson.Gson;
import com.ydev00.model.Thrd;

import java.sql.Connection;

public class ThreadController {
    private Connection dbConn;
    private Gson gson;

    public ThreadController(Connection dbConn) {
        this.dbConn = dbConn;
        this.gson = new Gson();
    }

    public Route loadFeed = (request, response) -> {
        response.type("application.json");

        return gson.toJson(new Thrd());
    };

    public Route create = (request, response) -> {
        return gson.toJson(new Thrd());
    };
    public Route loadThread = (request, response) -> {
        return "car";
    };

    public Route getThreadsByUser = (request, response) -> {
        return gson.toJson(new Thrd());
    };
}
