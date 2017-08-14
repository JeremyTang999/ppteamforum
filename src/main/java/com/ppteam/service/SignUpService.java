package com.ppteam.service;

import com.ppteam.entity.*;
import com.ppteam.service.exceptions.SignUpException;
import org.springframework.stereotype.Service;



public interface SignUpService {

    public void signUp(User u,UserInfo ui,UserSecurity us) throws SignUpException;
}
