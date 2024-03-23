package com.hank.QQserver;

import com.hank.Message;
import com.hank.MessageType;
import com.hank.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class QQservers {
    public static ConcurrentHashMap<String,User> usermap=new ConcurrentHashMap<>();
    ServerSocket serverSocket=null;
    static {//静态代码块，在类加载时会执行一次
        usermap.put("影",new User("影","123456"));
        usermap.put("夜兰",new User("夜兰","123456"));
        usermap.put("仆人",new User("仆人","123456"));
        usermap.put("履刑者",new User("履刑者","123456"));
    }
    public boolean checkuser(User user){
        User temp=usermap.get(user.getId());
        return temp!=null&&temp.getPsw().equals(user.getPsw());
    }
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
                if(checkuser(user)){
                    System.out.println("服务器端与"+user.getId()+"连接成功");
                    message.setMesType(MessageType.LOG_IN_SUCCESS);
                    oos.writeObject(message);
                    ServerToClientThread STCthread
                            = new ServerToClientThread(socket, user.getId());
                    STCthread.start();
                    ManageSTCthread.addToSmap(user.getId(),STCthread);
                }else {
                    System.out.println("用户"+user.getId()+"验证失败!");
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
