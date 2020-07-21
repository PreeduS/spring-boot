package com.example.demo.enums;

public enum UserPermissions {
    USER_READ("USER:READ"),         // calls constructor with value "USER:READ"
    USER_WRITE("USER:WRITE");

    public final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
