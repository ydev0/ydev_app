package com.ydev00.dao;

import spark.Route;


import java.sql.Connection;

public class ModUserDAO extends UserDAO{
    public ModUserDAO(Connection dbConn) {
        super(dbConn);
    }

    public Object deleteUser(Object obj) {
        return null;
    }
    public Object deletePost(Object obj) {
        return null;
    }

}
