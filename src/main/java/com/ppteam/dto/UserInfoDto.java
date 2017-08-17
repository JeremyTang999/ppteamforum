package com.ppteam.dto;

import com.ppteam.entity.Gender;
import com.ppteam.entity.UserInfo;

public class UserInfoDto {
    private Integer userId;
    private String gender;
    private String avatarPath;
    private String personalSignature;

    public UserInfoDto() {
    }

    public UserInfoDto(Integer userId, String gender, String avatarPath, String personalSignature) {
        this.userId = userId;
        this.gender = gender;
        this.avatarPath = avatarPath;
        this.personalSignature = personalSignature;
    }
    public UserInfoDto(UserInfo userInfo){
        userId=new Integer(userInfo.getUserId());
        gender=new String(userInfo.getGender().toString());
        avatarPath=userInfo.getAvatarPath()==null?null:new String(userInfo.getAvatarPath());
        personalSignature=userInfo.getPersonalSignature()==null?null:new String(userInfo.getPersonalSignature());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
    }
}
