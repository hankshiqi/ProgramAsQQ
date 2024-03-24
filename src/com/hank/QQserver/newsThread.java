package com.hank.QQserver;

import com.hank.Message;
import com.hank.MessageType;
import com.hank.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class newsThread implements Runnable{
    private boolean loop=true;
    @Override
    public void run() {
        while (loop)
        {
            System.out.println("请输入要广播的消息");
            String content= Utility.readString(200);
            if(content.equals("exit"))
                loop=false;
            for(Map.Entry<String,ServerToClientThread> each:ManageSTCthread.map.entrySet() ){
                ServerToClientThread cthread=each.getValue();
                Socket socket = cthread.getSocket();
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    Message message = new Message();
                    message.setMesType(MessageType.MESSAGE_NEWS);
                    message.setContent(content);
                    message.setSender("服务器");
                    objectOutputStream.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
