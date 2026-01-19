package com.neeraj.auth.authservice.util;

public class RequestContext {
    private static final ThreadLocal<String> IP_HOLDER = new ThreadLocal<>();
    public static void setIp(String ip){
        IP_HOLDER.set(ip);
    }
    public static String getIp(){
        return IP_HOLDER.get();
    }
    public static void clear(){
        IP_HOLDER.remove();
    }

}
