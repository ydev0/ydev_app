package com.ydev00.dao;

import java.sql.SQLException;
import java.util.List;
import com.ydev00.model.User;
import com.ydev00.model.UserAbstract;

public interface DAO {
    public Object create(Object obj);
    public Object get (Object obj) throws SQLException;
    public List<?> getAll();
}
