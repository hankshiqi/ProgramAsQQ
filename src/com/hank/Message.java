package com.hank;

import java.io.Serializable;

public class Message implements Serializable {
    private String sender;
    private String reciver;
    private String content;
    private String mesType;

    public Message(String sender, String reciver, String content, String mesType) {
        this.sender = sender;
        this.reciver = reciver;
        this.content = content;
        this.mesType = mesType;
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
