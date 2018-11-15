/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Data;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import model.Account;

/**
 *
 * @author tuannguyen
 */
public class ServerUK {

    public static ServerSocket serverUK;
    public static Socket socketUK;
    public static ObjectInputStream receiveDataClientUK;
    public static ObjectOutputStream sendDataClientUK;
    public static Account accountUK = new Account();
    public static Thread threadServerUK;
    public static Thread sendclientUK;

    public static void communicateClient() {
        threadServerUK = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    serverUK = new ServerSocket(8889);
                    System.out.println("Server is starting.........");

                    // chap nhan ket noi
                    socketUK = serverUK.accept();

                    System.out.println("Client US has been connected");

                    // nhận dữ liệu từ Client 
                    receiveDataClientUK = new ObjectInputStream(socketUK.getInputStream());
                    // gửi dữ liệu cho client
                    sendDataClientUK = new ObjectOutputStream(socketUK.getOutputStream());

                    while (true) {
                        Data dataReceive = (Data) receiveDataClientUK.readObject();
                        System.out.println("xu ly data tu client");
                        Data dataSend = null;
                        if ("login".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.checkLogin(dataReceive);
                            if (dataSend == null) {
                                dataSend = accountUK.checkLoginThePhu(dataReceive);
                            }
                            System.out.println("xu ly login");

                        } else if ("login admin".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.checkLoginAdmin(dataReceive);
                            System.out.println("Xu ly login admin");

                        } else if ("check so du".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.checkSoDu(dataReceive);
                            System.out.println("xu ly check so du");

                        } else if ("rut tien".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.rutTien(dataReceive);
                            System.out.println("xu ly rut tien");

                        } else if ("doi mat khau".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.doiMatKhau(dataReceive);
                            System.out.println("xu ly doi mat khau");

                        } else if ("xem chi tiet the".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.getThongTinThe(dataReceive);
                            System.out.println("xu ly xem chi tiet the");

                        } else if ("nap tien the".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.napTien(dataReceive);
                            System.out.println("xu ly nap tien the");

                        } else if ("tao the chinh".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.taoTheChinh(dataReceive);
                            System.out.println("xu ly tao the chinh");

                        } else if ("tao the phu".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.taoThePhu(dataReceive);
                            System.out.println("xu ly tao the phu");

                        } else if ("xoa the".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.xoaThe(dataReceive);
                            System.out.println("xu ly tao xoa the");
                        }

                        sendClient(dataSend);
                    }
                } catch (Exception e) {
                }
            }
        });

        threadServerUK.start();
    }

    public static void sendClient(Data dataSend) {
        sendclientUK = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    sendDataClientUK.writeObject(dataSend);
                    System.out.println("gui data cho client");
                    sendDataClientUK.flush();
                } catch (Exception e) {
                }
            }
        });
        sendclientUK.start();
    }

    public static void main(String[] args) {
        ServerUK uk = new ServerUK();
        uk.communicateClient();
    }
}
