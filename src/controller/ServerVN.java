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

    public static Account accountVN = new Account();
    public static ObjectOutputStream sendDataServerUS;
    public static ObjectOutputStream sendDataServerUK;
    public static boolean primaryVN;
    public static int a = 0;

    class ThreadSocket extends Thread {

        Socket socketVN = null;

        public ThreadSocket(Socket socketVN) {
            this.socketVN = socketVN;
        }

        public void run() {
            try {

                // nhận dữ liệu từ Client 
                ObjectInputStream receiveDataClient = new ObjectInputStream(socketVN.getInputStream());
                // gửi dữ liệu cho client
                ObjectOutputStream sendDataClient = new ObjectOutputStream(socketVN.getOutputStream());

                while (true) {
                    Data dataReceive = (Data) receiveDataClient.readObject();
                    System.out.println("xu ly data tu client");
                    Data dataSend = null;

//                    synchronized (dataReceive) {
                        if ("login".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.checkLogin(dataReceive);
                            if (dataSend == null) {
                                dataSend = accountVN.checkLoginThePhu(dataReceive);
                            }
                            System.out.println("Da xu ly login \n");

                        } else if ("login admin".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.checkLoginAdmin(dataReceive);
                            System.out.println("Da xu ly login admin \n");

                        } else if ("check so du".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.checkSoDu(dataReceive);
                            System.out.println("Da xu ly check so du \n");

                        } else if ("rut tien".equals(dataReceive.getMessage())) {
                            System.out.println("Dang xu ly rut tien \n");
                            Thread.sleep(10000);
                            dataSend = accountVN.rutTien(dataReceive);

                        } else if ("doi mat khau".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.doiMatKhau(dataReceive);
                            System.out.println("Da xu ly doi mat khau \n");

                        } else if ("xem chi tiet the".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.getThongTinThe(dataReceive);
                            System.out.println("Da xu ly xem chi tiet the \n");

                        } else if ("nap tien the".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.napTien(dataReceive);
                            System.out.println("Da xu ly nap tien the \n");

                        } else if ("tao the chinh".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.taoTheChinh(dataReceive);
                            System.out.println("Da xu ly tao the chinh \n");

                        } else if ("tao the phu".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.taoThePhu(dataReceive);
                            System.out.println("Da xu ly tao the phu \n");

                        } else if ("xoa the".equals(dataReceive.getMessage())) {
                            dataSend = accountVN.xoaThe(dataReceive);
                            System.out.println("Da xu ly tao xoa the \n");
                        }
                        if ("primary".equals(dataReceive.getPrimary())) {
                            primaryVN = true;
                            dataReceive.setPrimary(null);
                            connectServerUS();
                            connectServerUK();
                        }
                        if (primaryVN) {
                            // send data processed for client
                            try {
                                sendDataClient.writeObject(dataSend);
                                System.out.println("gui data cho client");
                                sendDataClient.flush();
                            } catch (Exception e) {
                            }
                            // send data from Client for ServerUS, ServerUK process
                            sendServerUS(dataReceive);
                        }
                    }
//                }
            } catch (Exception e) {
            }
        }
    }

    public void communicateClient() {
        try {
            ServerSocket serverVN = new ServerSocket(8888);
            System.out.println("Server is starting.........");

            while (true) {
                // chap nhan ket noi
                new ThreadSocket(serverVN.accept()).start();
                System.out.println("Client Viet Nam has been connected");
            }
        } catch (Exception e) {
        }
    }

    public void connectServerUS() {
        try {
            Socket socket1 = new Socket("localhost", 8889);

            System.out.println("Connecting to server US ....... \n");

            // gửi dữ liệu cho server
            sendDataServerUS = new ObjectOutputStream(socket1.getOutputStream());

            System.out.println("Connect Server US success .......\n");
        } catch (Exception e) {
        }
    }

    public void sendServerUS(Data data) {
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

    public void connectServerUK() {
        try {
            Socket socket2 = new Socket("localhost", 8890);

            System.out.println("Connecting to server UK ....... \n");

            // gửi dữ liệu cho server UK
            sendDataServerUK = new ObjectOutputStream(socket2.getOutputStream());

            System.out.println("Connect Server UK success .......\n");
        } catch (Exception e) {
        }
    }

    public void sendServerUK(Data data) {
        try {

            sendDataServerUK.writeObject(data);
            System.out.println("gui data cho Server UK");
            sendDataServerUK.flush();

        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        ServerVN vn = new ServerVN();
        vn.communicateClient();
    }
}
