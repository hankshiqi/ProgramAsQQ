package com.hank.QQserver;

import com.hank.Message;
import com.hank.MessageType;
import com.hank.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class ServerToClientThread extends Thread{
    public Socket socket;
    public String uid;

    public ServerToClientThread(Socket socket, String uid) {
        this.socket = socket;
        this.uid = uid;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public void run() {
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg=(Message)ois.readObject();
                if(msg.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_USER)){
                    String sendback = ManageSTCthread.returnOnlineUser();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    //构建message对象返回给客户端
                    Message messageback=new Message();
                    messageback.setMesType(MessageType.MESSAGE_RET_ONLIEN_USER);
                    messageback.setContent(sendback);
                    messageback.setReciver(msg.getSender());
                    objectOutputStream.writeObject(messageback);
                }else {

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
