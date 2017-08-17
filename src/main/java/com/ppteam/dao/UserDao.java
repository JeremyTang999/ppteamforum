package com.ppteam.dao;

import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.dao.exceptions.MoreThanOneResultException;
import com.ppteam.entity.User;

public interface UserDao extends BaseDao<User>{

    User getByUsername(String username) throws MoreThanOneResultException;
}
