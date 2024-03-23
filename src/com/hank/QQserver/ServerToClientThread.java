package com.hank.QQserver;

import com.hank.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
