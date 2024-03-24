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
    public boolean loop=true;

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

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
        while (loop){
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
                }else if(msg.getMesType().equals(MessageType.MESSAGE_READY_EXIT)){
                    if(msg.getSender().equals(uid)){
                        loop=false;
                        ManageSTCthread.map.remove(uid);
                        System.out.println("用户："+uid+"已经与服务器端断开连接");
                    }
                } else if (msg.getMesType().equals(MessageType.MESSAGE_TO_OTHER_ONE)) {
                    ServerToClientThread target=ManageSTCthread.getonethread(msg.getReciver());
                    Socket targetsocket=target.getSocket();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(targetsocket.getOutputStream());
                    objectOutputStream.writeObject(msg);
                } else if (msg.getMesType().equals(MessageType.MESSAGE_TO_EVERYONE)) {
                    for(Map.Entry<String,ServerToClientThread> each:ManageSTCthread.map.entrySet()){
                        if(!each.getKey().equals(msg.getSender())){
                            ServerToClientThread target= each.getValue();
                            Socket targetsocket=target.getSocket();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(targetsocket.getOutputStream());
                            objectOutputStream.writeObject(msg);
                        }
                    }
                } else if (msg.getMesType().equals(MessageType.MESSAGE_OFFILE)) {
                    ServerToClientThread target=ManageSTCthread.getonethread(msg.getReciver());
                    Socket targetsocket=target.getSocket();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(targetsocket.getOutputStream());
                    objectOutputStream.writeObject(msg);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
