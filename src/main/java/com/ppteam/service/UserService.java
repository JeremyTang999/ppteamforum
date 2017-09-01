package com.ppteam.service;

import com.ppteam.dto.*;


public interface UserService {

    /**
     * 通过id获取用户
     * @param id
     * @return
     */
    UserDto getUser(int id);

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    UserDto getUser(String username);

    /**
     * 通过从security容器中获取用户名获取用户
     * @return
     */
    UserDto getUser();

    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    UserInfoDto getUserInfo(int id);

    /**
     * 通过从security容器中获取用户名修改用户信息
     * @param userInfoDto
     * @return
     */
    boolean setUserInfo(UserInfoDto userInfoDto);

    /**
     * 通过从security容器中获取用户名获取密保问题
     * @return
     */
    UserSecurityDto getQuestions();

    /**
     * 设置用户安全信息
     * @param userSecurityDto
     * @return
     */
    boolean setUserSecurity(UserSecurityDto userSecurityDto);

    /**
     * 注册
     * @param signUpDto
     * @return
     */
    boolean signUp(SignUpDto signUpDto);

    /**
     * 从security容器获取用户名
     * @return
     */
    String getUsernameFromSecurity();

    /**
     * 根据从security容器获取用户名获取id
     * @return
     */
    Integer getUserIdFromSecurity();
}
