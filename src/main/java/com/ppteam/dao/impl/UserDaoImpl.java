package com.ppteam.dao.impl;

import com.ppteam.dao.*;
import com.ppteam.entity.User;
import jdk.nashorn.internal.scripts.JD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{


    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(User.class, jdbcTemplate,"id");
    }
}
