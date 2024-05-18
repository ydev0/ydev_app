package com.ydev00.controller;

import spark.Route;

import com.google.gson.Gson;

import com.ydev00.model.thread.Thrd;
import com.ydev00.model.user.User;
import com.ydev00.dao.ThreadDAO;

import java.util.List;

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
        response.type("application.json");

        List<Thrd> thrdList;

        ThreadDAO threadDAO = new ThreadDAO(dbConn);

        thrdList = (List<Thrd>) threadDAO.getAll();

        if(thrdList.isEmpty()) {
            return gson.toJson(new Thrd());
        }

        return gson.toJson(thrdList, List.class);
    };

    public Route getThreadsByUser = (request, response) -> {
        response.type("application.json");

        List<Thrd> thrdList;

        ThreadDAO threadDAO = new ThreadDAO(dbConn);

        thrdList = (List<Thrd>) threadDAO.getByUser(gson.fromJson("user", User.class));

        return gson.toJson(new Thrd());
    };

    public Route delete = (request, response) -> {
        return "carlos";
    };
}
