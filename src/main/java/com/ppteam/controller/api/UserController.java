package com.ppteam.controller.api;

import com.ppteam.controller.message.Message;
import com.ppteam.dao.UserDao;
import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/api/user",method = RequestMethod.GET)
    public ResponseEntity getUser(@RequestParam(value = "id",required = false) Integer id,
                                  @RequestParam(value = "username",required = false) String username,
                                  @RequestParam(value = "from_context",required = false) Boolean isFromContext){
        User u;
        try{
            if(isFromContext!=null && isFromContext){
                String name=getUsernameFromContext();
                if(name==null){
                    throw new Exception();
                }
                u=userDao.getByUsername(getUsernameFromContext());
            }
            else{
                if(id!=null)
                    u=userDao.get(id);
                else
                    u=userDao.getByUsername(username);
            }
            if(u==null){
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(u,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    public static String getUsernameFromContext(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        if(auth instanceof AnonymousAuthenticationToken){
            return null;
        }
        else{
            return ((UserDetails)auth.getPrincipal()).getUsername();
        }
    }
}
