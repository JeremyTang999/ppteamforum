package com.ppteam.service.impl;

import com.ppteam.dao.UserSecurityDao;
import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.dao.exceptions.MoreThanOneResultException;
import com.ppteam.entity.UserSecurity;
import com.ppteam.service.SecurityService;
import com.ppteam.service.exceptions.UnvalidatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserSecurityDao userSecurityDao;

    @Override
    public UserSecurity getSecurity(int id) throws DaoQueryFailException,MoreThanOneResultException{
        UserSecurity us=userSecurityDao.get(id);
        us.setAnswer1(null);
        us.setAnswer2(null);
        us.setAnswer3(null);
        us.setPassword(null);
        return us;
    }

    @Override
    public void setSecurity(UserSecurity oldSecurity, UserSecurity newSecurity) throws DaoUpdateFailException,DaoQueryFailException,MoreThanOneResultException,UnvalidatedException{
        Map<String,String> oldQA=new HashMap<String,String>();
        Map<String,String> unvalidatedQA=new HashMap<String,String>();
        unvalidatedQA.put(oldSecurity.getQuestion1(),oldSecurity.getAnswer1());
        unvalidatedQA.put(oldSecurity.getQuestion2(),oldSecurity.getAnswer2());
        unvalidatedQA.put(oldSecurity.getQuestion3(),oldSecurity.getAnswer3());

        UserSecurity us=userSecurityDao.get(oldSecurity.getUserId());

        oldQA.put(us.getQuestion1(),us.getAnswer1());
        oldQA.put(us.getQuestion2(),us.getAnswer2());
        oldQA.put(us.getQuestion3(),us.getAnswer3());

        for(Map.Entry e1:oldQA.entrySet()){
            for(Map.Entry e2:unvalidatedQA.entrySet()){
                if(e2.getKey().equals(e1.getKey())){
                    if(!e2.getValue().equals(e1.getValue())){
                        throw  new UnvalidatedException();
                    }
                }
            }
        }

        userSecurityDao.update(newSecurity);

    }
}
