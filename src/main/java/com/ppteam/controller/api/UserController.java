package com.ppteam.controller.api;

import com.ppteam.dao.UserInfoDao;
import com.ppteam.dto.*;
import com.ppteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ResponseEntity getUser(@RequestParam(value = "id",required = false)Integer id,
                                  @RequestParam(value = "from_context",required = false)Boolean fromContext,
                                  @RequestParam(value = "username",required = false)String username){
        UserDto user=null;
        if(id!=null){
            user=userService.getUser(id);
        }
        else if(fromContext!=null && fromContext){
            user=userService.getUser();
        }
        else if(username!=null){
            user=userService.getUser(username);
        }
        return user!=null ? new ResponseEntity(user, HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user_info",method = RequestMethod.GET)
    public ResponseEntity getUserInfo(@RequestParam int id){
        UserInfoDto userInfo=userService.getUserInfo(id);
        return userInfo!=null ? new ResponseEntity(userInfo,HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user_info",method = RequestMethod.PUT)
    public ResponseEntity putUserInfo(@RequestBody UserInfoDto userInfo){
        return new ResponseEntity(userService.setUserInfo(userInfo) ?
                HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user_security",method = RequestMethod.GET)
    public ResponseEntity getUserSecurity(){
        UserSecurityDto userSecurity=userService.getQuestions();
        return userSecurity!=null ? new ResponseEntity(userSecurity,HttpStatus.OK) :
                new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user_security",method = RequestMethod.PUT)
    public ResponseEntity putUserSecurity(@RequestBody UserSecurityDto userSecurity){
        return new ResponseEntity( userService.setUserSecurity(userSecurity) ?
                HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/sign_up_info",method = RequestMethod.POST)
    public ResponseEntity postSignUpInfo(@RequestBody SignUpDto signUpDto){
        return new ResponseEntity( userService.signUp(signUpDto) ?
                HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
