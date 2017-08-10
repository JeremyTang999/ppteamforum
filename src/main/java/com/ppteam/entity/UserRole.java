package com.ppteam.entity;

public enum UserRole {
    IN_AUDIT("in_audit"),USER("user"),ADMIN("admin");
    private String value;

    UserRole(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

    public static UserRole fromString(String value) {
        if(value.equals("admin")){
            return ADMIN;
        }
        else if(value.equals("user")){
            return USER;
        }
        else{
            return IN_AUDIT;
        }
    }
}
