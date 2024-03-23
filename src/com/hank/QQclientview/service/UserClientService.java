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
    public boolean checkservice(String id,String psw) throws IOException, ClassNotFoundException {
        boolean b=false;
        user.setId(id);
        user.setPsw(psw);
        socket=new Socket(InetAddress.getLocalHost(),9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(user);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message=(Message) objectInputStream.readObject();
        if(message.equals(MessageType.LOG_IN_SUCCESS)){
            clientsocketThread clientsocketThread = new clientsocketThread(socket);
            clientsocketThread.start();//启动客户端接收服务器端线程，为了扩展，将该线程放入集合中进行管理
            ManagerClientConnectServer.addtomap(id,clientsocketThread);
            b=true;
        }else {
            socket.close();
        }
        return b;
    }
}
