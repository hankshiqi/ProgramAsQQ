package com.hank.QQclientview.service;

import com.hank.Message;
import com.hank.MessageType;
import com.hank.User;

import java.io.*;
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
                if(o.getMesType().equals(MessageType.MESSAGE_RET_ONLIEN_USER)){//打印出来接收到来自服务器的在线用户列表
                    System.out.println("====在线用户列表====");
                    String[] list=o.getContent().split(" ");
                    for(int i=0;i< list.length;i++){
                        System.out.println("用户:"+list[i]);
                    }
                }else if(o.getMesType().equals(MessageType.MESSAGE_TO_OTHER_ONE)||o.getMesType().equals(MessageType.MESSAGE_TO_EVERYONE)){//收到来自服务器转发的来自另一个用户的消息
                    String sender=o.getSender();
                    String content=o.getContent();
                    System.out.println("用户("+sender+")说"+content);
                } else if (o.getMesType().equals(MessageType.MESSAGE_OFFILE)) {
                    String sender=o.getSender();
                    System.out.println("用户("+sender+")发来了文件");
                    byte[] bytes=o.getFilebyte();
                    //默认路径E:\testQQ
                    String path=o.getGeshi();
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
                    bufferedOutputStream.write(bytes);
                    bufferedOutputStream.close();
                    System.out.println("\n文件保存成功");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
