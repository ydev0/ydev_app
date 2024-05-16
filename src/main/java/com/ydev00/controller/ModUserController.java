package com.ydev00.controller;

import com.google.gson.Gson;
import com.ydev00.dao.ThreadDAO;
import com.ydev00.dao.UserDAO;
import com.ydev00.model.ModUser;
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

    public Route login = (request, response) -> {
        response.type("application/json");

        ModUser user = gson.fromJson(request.body(), ModUser.class);

        user.setRoot(user.isRoot());

        return "carlos";
    };

    public Route deletePost = (request, response) -> {
        response.type("application/json");

        if(request.headers("root" ) == null) {
            response.status(403);
            return "Forbidden";
        }

        Thrd thrd = gson.fromJson(request.body(), Thrd.class);

        ThreadDAO threadDAO = new ThreadDAO(dbConn);

        thrd = (Thrd) threadDAO.delete(thrd);


        return "carlos";
    };

    public Route deleteUser = (request, response) -> {
        response.type("application/json");

        if(request.headers("root" ) == null) {
            response.status(403);
            return "Forbidden";
        }

        User user = gson.fromJson(request.body(), User.class);

        UserDAO userDAO = new UserDAO(dbConn);
        user = (User) userDAO.delete(user);

        return null;
    };

}
