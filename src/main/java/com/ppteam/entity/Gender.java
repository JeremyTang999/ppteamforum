package com.ppteam.entity;

import groovyjarjarcommonscli.GnuParser;

public enum Gender {
    MALE("male"),FEMALE("female"),OTHER("other"),SECRECY("secrecy");

    private String value;

    Gender(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

    public static Gender fromString(String value){
        if(value.equals("male")){
            return MALE;
        }
        else if(value.equals("female")){
            return FEMALE;
        }
        else if(value.equals("other")){
            return OTHER;
        }
        else{
            return SECRECY;
        }
    }
}
