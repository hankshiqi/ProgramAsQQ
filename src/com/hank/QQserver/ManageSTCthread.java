package com.hank.QQserver;

import java.util.HashMap;

public class ManageSTCthread {
    public static HashMap<String,ServerToClientThread> map=new HashMap<>();
    public static void addToSmap(String uid,ServerToClientThread thread)
    {
        map.put(uid,thread);
    }
    public static ServerToClientThread getonethread(String uid){
        return map.get(uid);
    }
}
