package com.ppteam.controller.api;

import com.ppteam.entity.*;
import com.ppteam.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @RequestMapping(value = "/api/sign_up_info",method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody  Map<String,String> json){
        try {
            User u=new User(null,json.get("username"),System.currentTimeMillis(), UserRole.IN_AUDIT);
            UserInfo ui=new UserInfo(null, Gender.fromString(json.get("gender")),null,null);
            UserSecurity us=new UserSecurity(
                    null,
                    json.get("password"),
                    json.get("question1"),
                    json.get("answer1"),
                    json.get("question2"),
                    json.get("answer2"),
                    json.get("question3"),
                    json.get("answer3")
            );
            signUpService.signUp(u,ui,us);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
