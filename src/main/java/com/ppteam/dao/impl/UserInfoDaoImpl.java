package com.ppteam.dao.impl;

import com.ppteam.dao.*;
import com.ppteam.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements UserInfoDao{


    @Autowired
    public UserInfoDaoImpl(JdbcTemplate jdbcTemplate) {
        super(UserInfo.class,jdbcTemplate,"userId");
    }

}
