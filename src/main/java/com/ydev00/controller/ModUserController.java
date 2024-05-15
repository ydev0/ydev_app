package com.ydev00.controller;

import com.google.gson.Gson;
import com.ydev00.dao.ThreadDAO;
import spark.Route;

import com.ydev00.model.User;
import com.ydev00.model.Image;
import com.ydev00.model.Thrd;

import java.sql.Connection;

public class ModUserController {
    private Gson gson;
    private Connection dbConn;
    private User user;

    public ModUserController(Connection dbConn) {
        this.dbConn = dbConn;
        this.gson = new Gson();
    }

    public Route deletePost = (request, response) -> {
        response.type("application/json");

        Thrd thrd = gson.fromJson(request.body(), Thrd.class);

        ThreadDAO threadDAO = new ThreadDAO(dbConn);

        thrd = (Thrd) threadDAO.delete(thrd);


        return "carlos";
    };

    public Route deleteUser = (request, response) -> {

        return "carlos";
    };

}
