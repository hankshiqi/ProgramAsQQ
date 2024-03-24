package com.hank.QQclientview.service;

import com.hank.Message;
import com.hank.MessageType;
import com.hank.User;

import java.io.*;
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
        if(message.mesType.equals(MessageType.LOG_IN_SUCCESS)){
            clientsocketThread clientsocketThread = new clientsocketThread(socket);
            clientsocketThread.setDaemon(true);
            clientsocketThread.start();//启动客户端接收服务器端线程，为了扩展，将该线程放入集合中进行管理
            ManagerClientConnectServer.addtomap(id,clientsocketThread);
            b=true;
        }else {
            socket.close();
        }
        return b;
    }
    public void getOnlineuser() throws IOException {
        Message message = new Message();
        message.setSender(user.getId());
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_USER);
        clientsocketThread thread=ManagerClientConnectServer.getthread(user.getId());
        Socket socket1=thread.getSocket();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket1.getOutputStream());
        objectOutputStream.writeObject(message);
    }
    public void sentExitmsg() throws IOException {
        Message message = new Message();
        message.setSender(user.getId());
        message.setMesType(MessageType.MESSAGE_READY_EXIT);
        clientsocketThread thread=ManagerClientConnectServer.getthread(user.getId());
        Socket socket1=thread.getSocket();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket1.getOutputStream());
        objectOutputStream.writeObject(message);
    }
    public void sentmsgToOtherOne(String reciver,String msgstr)throws IOException{
        Message message=new Message();
        message.setSender(user.getId());
        message.setContent(msgstr);
        message.setReciver(reciver);
        message.setMesType(MessageType.MESSAGE_TO_OTHER_ONE);
        clientsocketThread thread=ManagerClientConnectServer.getthread(user.getId());
        Socket socket1 = thread.getSocket();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket1.getOutputStream());
        objectOutputStream.writeObject(message);
    }
    public void sentmsgToEverybody(String msgstr)throws IOException{
        Message message = new Message();
        message.setSender(user.getId());
        message.setContent(msgstr);
        message.setMesType(MessageType.MESSAGE_TO_EVERYONE);
        clientsocketThread thread=ManagerClientConnectServer.getthread(user.getId());
        Socket socket1 = thread.getSocket();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket1.getOutputStream());
        objectOutputStream.writeObject(message);
    }
    public void sendfile(String reciver,String senderPath,String geshi) throws Exception {
        FileInputStream inputStream = new FileInputStream("E:\\baidudownload2\\分享资料\\qq1.png");
        byte[] file=new byte[(int) new File("E:\\baidudownload2\\分享资料\\qq1.png").length()];
        inputStream.read(file);
        Message message = new Message();
        message.setGeshi(geshi);
        message.setSender(user.getId());
        message.setReciver(reciver);
        message.setMesType(MessageType.MESSAGE_OFFILE);
        message.setFilebyte(file);
        clientsocketThread thread=ManagerClientConnectServer.getthread(user.getId());
        Socket socket1 = thread.getSocket();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket1.getOutputStream());
        objectOutputStream.writeObject(message);

    }
}
