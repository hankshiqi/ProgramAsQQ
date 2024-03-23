package com.hank.QQclientview.service;

import com.hank.Message;
import com.hank.MessageType;
import com.hank.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//该类完成用户登录验证和用户注册等功能
public class UserClientService {
    public User user=new User();
    public Socket socket;
    public void doservice(String id,String psw) throws IOException, ClassNotFoundException {
        user.setId(id);
        user.setPsw(psw);
        socket=new Socket(InetAddress.getLocalHost(),9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(user);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message=(Message) objectInputStream.readObject();
        if(message.equals(MessageType.LOG_IN_SUCCESS)){
            new clientsocketThread(socket);
        }else {

        }
    }
}
