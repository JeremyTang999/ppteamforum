package com.ppteam.dto;

public class UserSecurityDto{
    private Integer userId;

    private Boolean update;

    private String oldPassword;
    private String oldQuestion1;
    private String oldAnswer1;
    private String oldQuestion2;
    private String oldAnswer2;
    private String oldQuestion3;
    private String oldAnswer3;
    
    private String newPassword;
    private String newQuestion1;
    private String newAnswer1;
    private String newQuestion2;
    private String newAnswer2;
    private String newQuestion3;
    private String newAnswer3;

    public UserSecurityDto() {
    }


    public UserSecurityDto(Integer userId, Boolean update, String oldPassword, String oldQuestion1, String oldAnswer1, String oldQuestion2, String oldAnswer2, String oldQuestion3, String oldAnswer3, String newPassword, String newQuestion1, String newAnswer1, String newQuestion2, String newAnswer2, String newQuestion3, String newAnswer3) {
        this.userId = userId;
        this.update = update;
        this.oldPassword = oldPassword;
        this.oldQuestion1 = oldQuestion1;
        this.oldAnswer1 = oldAnswer1;
        this.oldQuestion2 = oldQuestion2;
        this.oldAnswer2 = oldAnswer2;
        this.oldQuestion3 = oldQuestion3;
        this.oldAnswer3 = oldAnswer3;
        this.newPassword = newPassword;
        this.newQuestion1 = newQuestion1;
        this.newAnswer1 = newAnswer1;
        this.newQuestion2 = newQuestion2;
        this.newAnswer2 = newAnswer2;
        this.newQuestion3 = newQuestion3;
        this.newAnswer3 = newAnswer3;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getOldQuestion1() {
        return oldQuestion1;
    }

    public void setOldQuestion1(String oldQuestion1) {
        this.oldQuestion1 = oldQuestion1;
    }

    public String getOldAnswer1() {
        return oldAnswer1;
    }

    public void setOldAnswer1(String oldAnswer1) {
        this.oldAnswer1 = oldAnswer1;
    }

    public String getOldQuestion2() {
        return oldQuestion2;
    }

    public void setOldQuestion2(String oldQuestion2) {
        this.oldQuestion2 = oldQuestion2;
    }

    public String getOldAnswer2() {
        return oldAnswer2;
    }

    public void setOldAnswer2(String oldAnswer2) {
        this.oldAnswer2 = oldAnswer2;
    }

    public String getOldQuestion3() {
        return oldQuestion3;
    }

    public void setOldQuestion3(String oldQuestion3) {
        this.oldQuestion3 = oldQuestion3;
    }

    public String getOldAnswer3() {
        return oldAnswer3;
    }

    public void setOldAnswer3(String oldAnswer3) {
        this.oldAnswer3 = oldAnswer3;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewQuestion1() {
        return newQuestion1;
    }

    public void setNewQuestion1(String newQuestion1) {
        this.newQuestion1 = newQuestion1;
    }

    public String getNewAnswer1() {
        return newAnswer1;
    }

    public void setNewAnswer1(String newAnswer1) {
        this.newAnswer1 = newAnswer1;
    }

    public String getNewQuestion2() {
        return newQuestion2;
    }

    public void setNewQuestion2(String newQuestion2) {
        this.newQuestion2 = newQuestion2;
    }

    public String getNewAnswer2() {
        return newAnswer2;
    }

    public void setNewAnswer2(String newAnswer2) {
        this.newAnswer2 = newAnswer2;
    }

    public String getNewQuestion3() {
        return newQuestion3;
    }

    public void setNewQuestion3(String newQuestion3) {
        this.newQuestion3 = newQuestion3;
    }

    public String getNewAnswer3() {
        return newAnswer3;
    }

    public void setNewAnswer3(String newAnswer3) {
        this.newAnswer3 = newAnswer3;
    }
}
