package com.ppteam.service;

import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.dao.exceptions.MoreThanOneResultException;
import com.ppteam.entity.UserSecurity;
import com.ppteam.service.exceptions.UnvalidatedException;

public interface SecurityService {
    UserSecurity getSecurity(int id) throws DaoQueryFailException,MoreThanOneResultException;
    void setSecurity(UserSecurity oldSecurity,UserSecurity newSecurity)
            throws DaoUpdateFailException,DaoQueryFailException,MoreThanOneResultException,UnvalidatedException;
}
