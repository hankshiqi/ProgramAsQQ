package com.hank.QQserver;

import com.hank.Message;
import com.hank.MessageType;
import com.hank.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

import static com.hank.QQserver.QQservers.offlineMsg;

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
                Iterator<Message> iterator=offlineMsg.iterator();
                while (iterator.hasNext()){
                    Message message1=iterator.next();
                    if(message1.getReciver().equals(uid)){
                        checkmasg(message1);
                    }
                }
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg=(Message)ois.readObject();
                checkmasg(msg);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void checkmasg(Message msg) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        if(msg.isOnline())
        {
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
                    System.out.println("用户："+uid+"已经与服务器端断开连接"+now);
                }
            } else if (msg.getMesType().equals(MessageType.MESSAGE_TO_OTHER_ONE)) {
                ServerToClientThread target=ManageSTCthread.getonethread(msg.getReciver());
                if(target==null){
                    offlineMsg.add(msg);
                }else{
                    Socket targetsocket=target.getSocket();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(targetsocket.getOutputStream());
                    objectOutputStream.writeObject(msg);
                }
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
                if(target==null){
                    offlineMsg.add(msg);
                }else {
                    Socket targetsocket=target.getSocket();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(targetsocket.getOutputStream());
                    objectOutputStream.writeObject(msg);
                }

            }
        }
        else {
            offlineMsg.add(msg);
        }
    }
}
