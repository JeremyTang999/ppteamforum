package com.ppteam.dao.impl;

import com.ppteam.dao.*;
import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.entity.User;
import com.ppteam.entity.UserRole;
import jdk.nashorn.internal.scripts.JD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(User.class, jdbcTemplate,"id");
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public User getByUsername(String username) throws DaoQueryFailException{
        List<User> l=jdbcTemplate.query("SELECT * FROM user where username=?", new Object[]{username},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getLong("register_time"),
                                UserRole.fromString(rs.getString("role"))
                        );
                    }
                });
        if(l.size()==1){
            return l.get(0);
        }
        else if(l.size()==0){
            return null;
        }
        else{
            throw new DaoQueryFailException();
        }
    }
}
