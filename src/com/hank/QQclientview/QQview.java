package com.hank.QQclientview;

import com.hank.QQclientview.service.UserClientService;
import com.hank.Utility;

import java.io.IOException;

public class QQview {
    private String key;
    private boolean loop=true;

    public static void main(String[] args) throws Exception {
        new QQview().mainMenu();
    }
    public void mainMenu() throws Exception {
        while (loop){
            System.out.println("=========欢迎登录网络通信系统=========");
            System.out.println("\t\t 1.登录系统");
            System.out.println("\t\t 9.退出系统");
            System.out.print("请输入你的选择:");
            key= Utility.readString(1);
            switch (key){
                case "1":
                    System.out.println("请输入用户名");
                    String userid=Utility.readString(50);
                    System.out.println("请输入密码");
                    String psw=Utility.readString(50);
                    UserClientService userservice=new UserClientService();
                    if(userservice.checkservice(userid,psw)){//这里要编写一个类，UserClientService用于用户分类及服务
                        System.out.println("=========网络通信系统二级菜单=========");
                        System.out.println("=========(用户 "+userid+")=========");
                        while (loop){
                            System.out.println("       1.显示在线用户列表");
                            System.out.println("       2.群发消息");
                            System.out.println("       3.私聊消息");
                            System.out.println("       4.发送文件");
                            System.out.println("       9.退出系统");
                            key=Utility.readString(1);
                            switch (key){
                                case "1":
//                                    System.out.println("显示在线用户列表"),用一个userclienservice中的方法来实现
                                    userservice.getOnlineuser();
                                    break;
                                case "2":
                                    System.out.println("请输入群发的消息内容");
                                    String content1=Utility.readString(2000);
                                    userservice.sentmsgToEverybody(content1);
                                    break;
                                case "3":
                                    System.out.println("请输入收消息的用户");
                                    String reciver=Utility.readString(50);
                                    System.out.println("请输入要发送消息的内容");
                                    String content=Utility.readString(2000);
                                    userservice.sentmsgToOtherOne(reciver,content);
                                    break;
                                case "4":
                                    System.out.println("请输入想要发送文件的用户");
                                    String reciver1=Utility.readString(50);
                                    System.out.println("请输入发送文件的完整路径");
                                    String sendPath=Utility.readString(200);
                                    System.out.println("请输入对方的接收地址");
                                    String gehsi=Utility.readString(50);
                                    userservice.sendfile(reciver1,sendPath,gehsi);
                                    break;
                                case "9":
//                                    System.out.println("退出系统");告知服务器端对应线程应该关闭
                                    userservice.sentExitmsg();
                                    loop=false;
                                    break;
                            }
                        }
                    }else{
                        System.out.println("用户名或密码错误，请重试！");
                    }
                    break;
                case "9":
                    loop=false;
                    break;
            }
        }
    }
}
