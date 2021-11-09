package com.bravo.user.utility;



public class SecurityUtil {

    public static boolean verifyPassword(String request, String backend){
        return request.equals(backend);
    }
}
