package com.hank.QQserver;

import com.hank.Message;
import com.hank.MessageType;
import com.hank.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class QQservers {
    ServerSocket serverSocket=null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new QQservers();
    }
    public QQservers() throws IOException, ClassNotFoundException {
        serverSocket=new ServerSocket(9999);
        try {
            while(true){
                Socket socket = serverSocket.accept();//如果没有客户端来就会阻塞，直到下一个客户端来
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                User user=(User) ois.readObject();
                Message message=new Message();
                if(user.getPsw().equals("123456")&&user.getId().equals("100")){
                    message.setMesType(MessageType.LOG_IN_SUCCESS);
                    oos.writeObject(message);
                    ServerToClientThread STCthread
                            = new ServerToClientThread(socket, user.getId());
                    STCthread.start();
                    ManageSTCthread.addToSmap(user.getId(),STCthread);
                }else {
                    message.setMesType(MessageType.LOG_IN_FAIL);
                    oos.writeObject(message);
                    socket.close();//登录失败后就要关闭这个socket
                }
            }
        } finally {
            serverSocket.close();
        }
    }
}
