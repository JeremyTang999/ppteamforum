package com.ppteam.dao.impl;

import com.ppteam.dao.UserSecurityDao;
import com.ppteam.entity.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityDaoImpl extends BaseDaoImpl<UserSecurity> implements UserSecurityDao {

    @Autowired
    public UserSecurityDaoImpl(JdbcTemplate jdbcTemplate) {
        super(UserSecurity.class, jdbcTemplate, "userId");
    }
}
