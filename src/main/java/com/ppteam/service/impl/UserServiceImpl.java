package com.ppteam.service.impl;

import com.ppteam.dao.UserDao;
import com.ppteam.dao.UserInfoDao;
import com.ppteam.dao.UserSecurityDao;
import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.dao.exceptions.MoreThanOneResultException;
import com.ppteam.dto.SignUpDto;
import com.ppteam.dto.UserDto;
import com.ppteam.dto.UserInfoDto;
import com.ppteam.dto.UserSecurityDto;
import com.ppteam.entity.*;
import com.ppteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserSecurityDao userSecurityDao;

    /**
     * 通过id获取用户
     *
     * @param id
     * @return
     */
    @Override
    public UserDto getUser(int id) {

        try {
            User user=userDao.get(id);
            return user==null ? null : new UserDto(userDao.get(id));
        } catch (DaoQueryFailException | MoreThanOneResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public UserDto getUser(String username) {
        try {
            User user=userDao.getByUsername(username);
            return user==null ? null : new UserDto(user);
        }catch (MoreThanOneResultException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过从security容器中获取用户名获取用户
     *
     * @return
     */
    @Override
    public UserDto getUser() {
        try {
            //return new UserDto(userDao.getByUsername(getUsernameFromSecurity()));
            String username=getUsernameFromSecurity();
            return username!=null ? new UserDto(userDao.getByUsername(username)) : null;
        }catch (MoreThanOneResultException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过id获取用户信息
     *
     * @param id
     * @return
     */
    @Override
    public UserInfoDto getUserInfo(int id) {
        try {
            return new UserInfoDto(userInfoDao.get(id));
        }catch (DaoQueryFailException | MoreThanOneResultException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过从security容器中获取用户名修改用户信息
     *
     * @param userInfoDto
     * @return
     */
    @Override
    public boolean setUserInfo(UserInfoDto userInfoDto) {
        Integer id=getUserIdFromSecurity();
        if(id==null){
            return false;
        }
        else {

            try {
                UserInfo userInfo= null;
                userInfo = new UserInfo(
                        id,
                        Gender.fromString(userInfoDto.getGender()),
                        userInfoDto.getAvatarPath(),
                        userInfoDto.getPersonalSignature()
                );
                userInfoDao.update(userInfo);
                return true;
            } catch (DaoUpdateFailException e) {
                e.printStackTrace();
                return false;
            }

        }
    }

    /**
     * 通过从security容器中获取用户名获取密保问题
     *
     * @return
     */
    @Override
    public UserSecurityDto getQuestions() {
        UserSecurityDto userSecurityDto=new UserSecurityDto();
        try {
            Integer id=getUserIdFromSecurity();
            UserSecurity userSecurity;
            if(id!=null && (userSecurity=userSecurityDao.get(id))!=null){
                userSecurityDto.setOldQuestion1(userSecurity.getQuestion1());
                userSecurityDto.setOldQuestion2(userSecurity.getQuestion2());
                userSecurityDto.setOldQuestion3(userSecurity.getQuestion3());
                return userSecurityDto;
            }
            return null;
        }catch (DaoQueryFailException | MoreThanOneResultException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置用户安全信息
     *
     * @param userSecurityDto
     * @return
     */
    @Override
    public boolean setUserSecurity(UserSecurityDto userSecurityDto) {
        try {
            Integer id=getUserIdFromSecurity();
            UserSecurity userSecurity;
            if(id!=null && (userSecurity=userSecurityDao.get(id))!=null && userSecurity.getUserId().equals(id)){
                if(questionsCompare(userSecurityDto,userSecurity)){
                    if(!userSecurityDto.getUpdate())
                        return true;

                    userSecurity.setPassword(userSecurityDto.getNewPassword());
                    userSecurity.setQuestion1(userSecurityDto.getNewQuestion1());
                    userSecurity.setAnswer1(userSecurityDto.getNewAnswer1());
                    userSecurity.setQuestion2(userSecurityDto.getNewQuestion2());
                    userSecurity.setAnswer2(userSecurityDto.getNewAnswer2());
                    userSecurity.setQuestion3(userSecurityDto.getNewQuestion3());
                    userSecurity.setAnswer3(userSecurityDto.getNewAnswer3());
                    userSecurityDao.update(userSecurity);
                    return true;
                }
            }
            return false;
        }catch (DaoUpdateFailException | DaoQueryFailException | MoreThanOneResultException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 注册
     *
     * @param signUpDto
     * @return
     */
    @Override
    @Transactional
    public boolean signUp(SignUpDto signUpDto) {
        try {
            User user=new User(
                    null,
                    signUpDto.getUsername(),
                    System.currentTimeMillis(),
                    UserRole.IN_AUDIT
            );
            int id=userDao.add(user);

            UserInfo userInfo=new UserInfo(
                    id,
                    Gender.fromString(signUpDto.getGender()),
                    "",
                    ""
            );

            userInfoDao.add(userInfo);

            UserSecurity userSecurity=new UserSecurity(
                    id,
                    signUpDto.getPassword(),
                    signUpDto.getQuestion1(),
                    signUpDto.getAnswer1(),
                    signUpDto.getQuestion2(),
                    signUpDto.getAnswer2(),
                    signUpDto.getQuestion3(),
                    signUpDto.getAnswer3()
            );

            userSecurityDao.add(userSecurity);

            return true;

        } catch (DaoUpdateFailException | MoreThanOneResultException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 对比问题答案
     * @param userSecurityDto
     * @param userSecurity
     * @return
     */
    private boolean questionsCompare(UserSecurityDto userSecurityDto,UserSecurity userSecurity){
        if(!userSecurityDto.getOldPassword().equals(userSecurity.getPassword()))
            return false;

        Map<String,String>
                oldQA=new HashMap<String,String>(),
                newQA=new HashMap<String,String>();

        oldQA.put(userSecurity.getQuestion1(),userSecurity.getAnswer1());
        oldQA.put(userSecurity.getQuestion2(),userSecurity.getAnswer2());
        oldQA.put(userSecurity.getQuestion3(),userSecurity.getAnswer3());

        newQA.put(userSecurityDto.getOldQuestion1(),userSecurityDto.getOldAnswer1());
        newQA.put(userSecurityDto.getOldQuestion2(),userSecurityDto.getOldAnswer2());
        newQA.put(userSecurityDto.getOldQuestion3(),userSecurityDto.getOldAnswer3());

        Set<Map.Entry<String,String>> entrySet1=oldQA.entrySet(),entrySet2=newQA.entrySet();

        System.out.println(oldQA);
        System.out.println(newQA);

        for(Map.Entry e1:entrySet1){
            for(Map.Entry e2:entrySet2){
                if(e2.getKey().equals(e1.getKey())){
                    if(!e2.getValue().equals(e1.getValue())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 从security容器中获取用户名
     * @return
     */
    @Override
    public String getUsernameFromSecurity(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth instanceof AnonymousAuthenticationToken){
            return null;
        }
        else {
            return ((UserDetails)auth.getPrincipal()).getUsername();
        }
    }

    /**
     * 从security容器获取用户名并据此从dao获取id
     * @return
     */
    @Override
    public Integer getUserIdFromSecurity(){
        String username=getUsernameFromSecurity();
        if(username==null){
            return null;
        }
        else {
            try {
                return userDao.getByUsername(username)!=null ?
                        userDao.getByUsername(username).getId():
                        null;
            }catch (MoreThanOneResultException e){
                e.printStackTrace();
                return null;
            }

        }
    }
}
