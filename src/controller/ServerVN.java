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
public class ServerVN {

    public static ServerSocket serverVN;
    public static Socket socketVN;
    public static ObjectInputStream receiveDataClient;
    public static ObjectOutputStream sendDataClient;
    public static Account account = new Account();
    public static Thread threadServerVN;
    public static Thread sendclient;

    public static void communicateClient() {
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
                            dataSend = account.checkLogin(dataReceive);
                            System.out.println("xu ly login");

                        } else if ("check so du".equals(dataReceive.getMessage())) {
                            dataSend = account.checkSoDu(dataReceive);
                            System.out.println("xu ly check so du");

                        } else if ("rut tien".equals(dataReceive.getMessage())) {
                            dataSend = account.rutTien(dataReceive);
                            System.out.println("xu ly rut tien");

                        } else if ("doi mat khau".equals(dataReceive.getMessage())) {
                            dataSend = account.doiMatKhau(dataReceive);
                            System.out.println("xu ly doi mat khau");
                        }
                        sendClient(dataSend);
                    }
                } catch (Exception e) {
                }
            }
        });

        threadServerVN.start();
    }

    public static void sendClient(Data dataSend) {
        sendclient = new Thread(new Runnable() {
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
        sendclient.start();
    }

    public static void main(String[] args) {
        ServerVN vn = new ServerVN();
        vn.communicateClient();
    }
}
