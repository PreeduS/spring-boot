package com.example.demo.enums;
import java.util.EnumSet;  
 



public enum AppRoles {
    USER(EnumSet.allOf(UserPermissions.class)),
    ADMIN(EnumSet.noneOf(AdminPermission.class));

 
    public final EnumSet<?> roles;

    AppRoles(EnumSet<?> roles){ 
        this.roles = roles;
        
    }

}

/*
public enum AppRoles {
    USER(UserPermissions.USER_READ, "USER_READ");

    
    public final String role;

    AppRoles(String role) {
        this.role = role;
    }
}
*/
/*
public class AppRoles {
  public static enum UserPermissions{
    USER_READ("USER:READ"),         //calls constructor with value "USER:READ"
    USER_WRITE("USER:WRITE");

    public final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
  }

}
*/