package com.hank.QQclientview.service;

import com.hank.Message;
import com.hank.MessageType;
import com.hank.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class clientsocketThread extends Thread{
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public clientsocketThread(Socket socket) {
        this.socket = socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message o = (Message)objectInputStream.readObject();
                if(o.getMesType().equals(MessageType.MESSAGE_RET_ONLIEN_USER)){
                    System.out.println("====在线用户列表====");
                    String[] list=o.getContent().split(" ");
                    for(int i=0;i< list.length;i++){
                        System.out.println(list[i]);
                    }
                }else {

                }
            } catch (Exception e) {
                try {
                    throw new Exception(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
