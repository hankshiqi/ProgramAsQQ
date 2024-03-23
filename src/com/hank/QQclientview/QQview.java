package com.hank.QQclientview;

import com.hank.Utility;

public class QQview {
    private String key;
    private boolean loop=true;

    public static void main(String[] args) {
        new QQview().mainMenu();
    }
    public void mainMenu(){
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
                    if(false){
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
                                    System.out.println("显示在线用户列表");
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    System.out.println("退出系统");
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
