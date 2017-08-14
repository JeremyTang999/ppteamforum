package com.ppteam.controller.api;

import com.ppteam.controller.message.Message;
import com.ppteam.dao.UserInfoDao;
import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    @Autowired
    private UserInfoDao userInfoDao;

    @RequestMapping(value = "/api/user_info",method = RequestMethod.GET)
    public ResponseEntity getUserInfo(@RequestParam("id") int id){
        try {
            UserInfo u=userInfoDao.get(id);

            if(u==null){
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(u,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/user_info",method = RequestMethod.PUT)
    public ResponseEntity putUserInfo(@RequestBody UserInfo ui){
        try {
            userInfoDao.update(ui);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
