package com.hank.QQclientview.service;

import java.util.HashMap;

public class ManagerClientConnectServer {
    //该类管理客户端到服务端的线程
    public static HashMap<String,clientsocketThread> map=new HashMap<>();
    public static void addtomap(String id,clientsocketThread clientsocketThread){
        map.put(id,clientsocketThread);
    }
    public static clientsocketThread getthread(String id){
        return map.get(id);
    }
}
