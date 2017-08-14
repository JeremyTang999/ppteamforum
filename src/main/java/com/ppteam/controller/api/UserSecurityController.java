package com.ppteam.controller.api;


import com.ppteam.dao.*;
import com.ppteam.entity.*;
import com.ppteam.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserSecurityController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/api/user_security",method = RequestMethod.GET)
    public ResponseEntity getUserSecurity(){
        try {
            String name=UserController.getUsernameFromContext();
            if(name==null){
                throw new Exception();
            }
            User u=userDao.getByUsername(name);
            if(u==null){
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            int id=u.getId();
            UserSecurity userSecurity=securityService.getSecurity(id);
            if(userSecurity==null){
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(userSecurity,HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/api/user_security",method = RequestMethod.POST)
    public ResponseEntity postUserSecurty(@RequestBody Map<String,String> json){
        try {
            String name=UserController.getUsernameFromContext();
            if(name==null){
                throw new Exception();
            }
            User u=userDao.getByUsername(name);
            if(u==null){
                return new ResponseEntity(HttpStatus.NOT_FOUND);

            }
            int id=u.getId();
            UserSecurity oldSecurity=new UserSecurity(id,
                    json.get("oldPassword"),
                    json.get("oldQuestion1"),
                    json.get("oldAnswer1"),
                    json.get("oldQuestion2"),
                    json.get("oldAnswer2"),
                    json.get("oldQuestion3"),
                    json.get("oldAnswer3"));
            UserSecurity newSecurity=new UserSecurity(id,
                    json.get("newPassword"),
                    json.get("newQuestion1"),
                    json.get("newAnswer1"),
                    json.get("newQuestion2"),
                    json.get("newAnswer2"),
                    json.get("newQuestion3"),
                    json.get("newAnswer3"));
            securityService.setSecurity(oldSecurity,newSecurity);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

}
