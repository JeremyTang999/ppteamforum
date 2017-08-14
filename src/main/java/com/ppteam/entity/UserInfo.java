package com.ppteam.entity;

public class UserInfo {
    private Integer userId;
    private Gender gender;
    private String avatarPath;
    private String personalSignature;

    public UserInfo() {
    }

    public UserInfo(Integer userId, Gender gender, String avatarPath, String personalSignature) {
        this.userId = userId;
        this.gender = gender;
        this.avatarPath = avatarPath;
        this.personalSignature = personalSignature;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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
