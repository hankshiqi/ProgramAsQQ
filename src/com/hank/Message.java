package com.hank;

import java.io.Serializable;

public class Message implements Serializable {
    private String sender;
    private String reciver;
    private String content;
    public String mesType;
    public byte[] filebyte;
    public String reciverPath;
    public boolean online=true;

    public String getReciverPath() {
        return reciverPath;
    }

    public void setReciverPath(String reciverPath) {
        this.reciverPath = reciverPath;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getGeshi() {
        return reciverPath;
    }

    public void setGeshi(String geshi) {
        this.reciverPath = geshi;
    }

    public byte[] getFilebyte() {
        return filebyte;
    }

    public void setFilebyte(byte[] filebyte) {
        this.filebyte = filebyte;
    }

    public Message(String sender, String reciver, String content, String mesType) {
        this.sender = sender;
        this.reciver = reciver;
        this.content = content;
        this.mesType = mesType;
    }

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
