package com.hank.QQserver;

import java.util.HashMap;
import java.util.Map;

public class ManageSTCthread {
    public static HashMap<String,ServerToClientThread> map=new HashMap<>();
    public static void addToSmap(String uid,ServerToClientThread thread)
    {
        map.put(uid,thread);
    }
    public static ServerToClientThread getonethread(String uid){
        return map.get(uid);
    }
    public static String returnOnlineUser(){
        StringBuffer buf=new StringBuffer();
        for(Map.Entry<String, ServerToClientThread> each:ManageSTCthread.map.entrySet()){
            buf.append(each.getKey()+" ");
        }
        return buf.toString();
    }
}
