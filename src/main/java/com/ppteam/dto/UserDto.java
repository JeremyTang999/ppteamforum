package com.ppteam.dto;

import com.ppteam.entity.*;

public class UserDto {
    private Integer id;
    private String username;
    private Long registerTime;
    private String role;

    public UserDto() {
    }

    public UserDto(Integer id, String username, Long registerTime, String role) {
        this.id = id;
        this.username = username;
        this.registerTime = registerTime;
        this.role = role;
    }

    public UserDto(User user){
        id=user.getId()==null?null:new Integer(user.getId());
        username=user.getUsername()==null?null:new String(user.getUsername());
        registerTime=user.getRegisterTime()==null?null:new Long(user.getRegisterTime());
        role=user.getRole()==null?null:new String(user.getRole().toString());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
