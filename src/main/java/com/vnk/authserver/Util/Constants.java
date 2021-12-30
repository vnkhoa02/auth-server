package com.vnk.authserver.Util;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
    public static final String BASE_URL = "api/v1/";
    private static final Map<String, String> AUTH = new HashMap<>();
    public static String getConstant(String key){
        // roles
        AUTH.put("Admin", "Admin");
        AUTH.put("Manager", "Manager");
        AUTH.put("User", "User");

        // common
        AUTH.put("roles", "roles");
        AUTH.put("permissions", "permissions");
        AUTH.put("username", "username");

        if(AUTH.get(key) != null){
           return AUTH.get(key);
        }
        return null;
    }
}
