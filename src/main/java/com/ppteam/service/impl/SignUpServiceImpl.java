package com.ppteam.service.impl;

import com.ppteam.dao.*;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.entity.*;
import com.ppteam.service.SignUpService;
import com.ppteam.service.exceptions.SignUpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserSecurityDao userSecurityDao;


    @Override
    @Transactional
    public void signUp(User u, UserInfo ui, UserSecurity us) throws SignUpException {
        try {
            int id=userDao.add(u);
            ui.setUserId(id);
            userInfoDao.add(ui);
            us.setUserId(id);
            userSecurityDao.add(us);
        }catch (Exception e){
            e.printStackTrace();
            throw  new SignUpException();
        }

    }
}
