package com.ppteam.entity;

public class User {
    private Integer id;
    private String username;
    private Long registerTime;
    private UserRole role;

    public User() {
    }

    public User(Integer id, String username, Long registerTime, UserRole role) {
        this.id = id;
        this.username = username;
        this.registerTime = registerTime;
        this.role = role;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
