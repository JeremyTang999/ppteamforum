package com.ppteam.entity;

import java.util.concurrent.ThreadPoolExecutor;

public enum Topic {
    SIM("sim"),REAL("real");

    private String value;

    Topic(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

    public static Topic fromString(String value){
        if(value==null)
            return null;
        if(value.equals("sim")){
            return SIM;
        }
        else if(value.equals("real")){
            return REAL;
        }
        else{
            return null;
        }
    }
}
