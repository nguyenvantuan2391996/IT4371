/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.Client.receiveDataServer;
import static controller.Client.sendDataServer;
import static controller.Client.socketClient;
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
public class ServerVN {

    public static ServerSocket serverVN;
    public static Socket socketVN;
    public static ObjectInputStream receiveDataClient;
    public static ObjectOutputStream sendDataClient;
    public static Account accountVN = new Account();
    public static Thread threadServerVN;
    public static Thread sendclientVN;

    public static Socket socket1;
    public static ObjectOutputStream sendDataServerUS;
    public static Thread sendServerUS;

    public static Socket socket2;
    public static ObjectOutputStream sendDataServerUK;
    public static Thread sendServerUK;

    public static boolean primaryVN;

    public void communicateClient() {
        threadServerVN = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    serverVN = new ServerSocket(8888);
                    System.out.println("Server is starting.........");

                    // chap nhan ket noi
                    socketVN = serverVN.accept();

                    System.out.println("Client Viet Nam has been connected");

                    // nhận dữ liệu từ Client 
                    receiveDataClient = new ObjectInputStream(socketVN.getInputStream());
                    // gửi dữ liệu cho client
                    sendDataClient = new ObjectOutputStream(socketVN.getOutputStream());

                    while (true) {
                        Data dataReceive = (Data) receiveDataClient.readObject();
                        System.out.println("xu ly data tu client");
                        Data dataSend = null;
                        if ("login".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.checkLogin(dataReceive);
                            if (dataSend == null) {
                                dataSend = accountVN.checkLoginThePhu(dataReceive);
                            }
                            System.out.println("xu ly login");

                        } else if ("login admin".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.checkLoginAdmin(dataReceive);
                            System.out.println("Xu ly login admin");

                        } else if ("check so du".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.checkSoDu(dataReceive);
                            System.out.println("xu ly check so du");

                        } else if ("rut tien".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.rutTien(dataReceive);
                            System.out.println("xu ly rut tien");

                        } else if ("doi mat khau".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.doiMatKhau(dataReceive);
                            System.out.println("xu ly doi mat khau");

                        } else if ("xem chi tiet the".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.getThongTinThe(dataReceive);
                            System.out.println("xu ly xem chi tiet the");

                        } else if ("nap tien the".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.napTien(dataReceive);
                            System.out.println("xu ly nap tien the");

                        } else if ("tao the chinh".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.taoTheChinh(dataReceive);
                            System.out.println("xu ly tao the chinh");

                        } else if ("tao the phu".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.taoThePhu(dataReceive);
                            System.out.println("xu ly tao the phu");

                        } else if ("xoa the".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.xoaThe(dataReceive);
                            System.out.println("xu ly tao xoa the");
                        }
                        if ("primary".equals(dataReceive.getPrimary())) {
                            primaryVN = true;
                            dataReceive.setPrimary(null);
                            connectServerUS();
                            connectServerUK();
                        }
                        if (primaryVN) {
                            sendClient(dataSend);
                            sendServerUS(dataReceive);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });

        threadServerVN.start();
    }

    public void sendClient(Data dataSend) {
        sendclientVN = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    sendDataClient.writeObject(dataSend);
                    System.out.println("gui data cho client");
                    sendDataClient.flush();
                } catch (Exception e) {
                }
            }
        });
        sendclientVN.start();
    }

    public void connectServerUS() {
        try {
            socket1 = new Socket("localhost", 8889);

            System.out.println("Connecting to server US ....... \n");

            // gửi dữ liệu cho server
            sendDataServerUS = new ObjectOutputStream(socket1.getOutputStream());

            System.out.println("Connect Server US success .......\n");
        } catch (Exception e) {
        }
    }

    public void sendServerUS(Data data) {
        sendServerUS = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    
                    data.setLocation("bank_us");
                    sendDataServerUS.writeObject(data);
                    System.out.println("gui data cho Server US");
                    sendDataServerUS.flush();
                    
                    data.setLocation("bank_uk");
                    sendServerUK(data);
                    
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        sendServerUS.start();
    }

    public void connectServerUK() {
        try {
            socket2 = new Socket("localhost", 8890);

            System.out.println("Connecting to server UK ....... \n");

            // gửi dữ liệu cho server UK
            sendDataServerUK = new ObjectOutputStream(socket2.getOutputStream());

            System.out.println("Connect Server UK success .......\n");
        } catch (Exception e) {
        }
    }

    public void sendServerUK(Data data) {
        sendServerUK = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    
                    sendDataServerUK.writeObject(data);
                    System.out.println("gui data cho Server UK");
                    sendDataServerUK.flush();

                } catch (Exception e) {
                }
            }
        });

        sendServerUK.start();
    }

    public static void main(String[] args) {
        ServerVN vn = new ServerVN();
        vn.communicateClient();
    }
}
