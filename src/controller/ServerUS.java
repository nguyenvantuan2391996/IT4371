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
public class ServerUS {

    public static Account accountUS = new Account();
    public static ObjectOutputStream sendDataServerVN;
    public static ObjectOutputStream sendDataServerUK1;
    public static boolean primaryUS;

    class ThreadSocket extends Thread {

        Socket socketUS = null;

        public ThreadSocket(Socket socketUS) {
            this.socketUS = socketUS;
        }

        public void run() {
            try {
                // nhận dữ liệu từ Client 
                ObjectInputStream receiveDataClientUS = new ObjectInputStream(socketUS.getInputStream());
                // gửi dữ liệu cho client
                ObjectOutputStream sendDataClientUS = new ObjectOutputStream(socketUS.getOutputStream());

                while (true) {
                    Data dataReceive = (Data) receiveDataClientUS.readObject();
                    System.out.println("xu ly data tu client");
                    Data dataSend = null;

                    synchronized (dataReceive) {
                        if ("login".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.checkLogin(dataReceive);
                            if (dataSend == null) {
                                dataSend = accountUS.checkLoginThePhu(dataReceive);
                            }
                            System.out.println("xu ly login \n");

                        } else if ("login admin".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.checkLoginAdmin(dataReceive);
                            System.out.println("Xu ly login admin \n");

                        } else if ("check so du".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.checkSoDu(dataReceive);
                            System.out.println("xu ly check so du \n");

                        } else if ("rut tien".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.rutTien(dataReceive);
                            System.out.println("xu ly rut tien \n");

                        } else if ("doi mat khau".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.doiMatKhau(dataReceive);
                            System.out.println("xu ly doi mat khau \n");

                        } else if ("xem chi tiet the".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.getThongTinThe(dataReceive);
                            System.out.println("xu ly xem chi tiet the \n");

                        } else if ("nap tien the".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.napTien(dataReceive);
                            System.out.println("xu ly nap tien the \n");

                        } else if ("tao the chinh".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.taoTheChinh(dataReceive);
                            System.out.println("xu ly tao the chinh \n");

                        } else if ("tao the phu".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.taoThePhu(dataReceive);
                            System.out.println("xu ly tao the phu \n");

                        } else if ("xoa the".equals(dataReceive.getMessage())) {
                            dataSend = accountUS.xoaThe(dataReceive);
                            System.out.println("xu ly tao xoa the \n");
                        }

                        if ("primary".equals(dataReceive.getPrimary())) {
                            primaryUS = true;
                            dataReceive.setPrimary(null);
                            connectServerUK();
                            connectServerVN();
                        }
                        if (primaryUS) {
                            // send data processed for client
                            try {
                                sendDataClientUS.writeObject(dataSend);
                                System.out.println("gui data cho client");
                                sendDataClientUS.flush();
                            } catch (Exception e) {
                            }
                            // send data from Client for ServerUK, ServerVN process
                            sendServerUK(dataReceive);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public void communicateClient() {
        try {
            ServerSocket serverUS = new ServerSocket(8889);
            System.out.println("Server is starting.........");

            while (true) {
                // chap nhan ket noi
                new ThreadSocket(serverUS.accept()).start();
                System.out.println("Client US has been connected");
            }
        } catch (Exception e) {
        }
    }

    public void connectServerVN() {
        try {
            Socket socket3 = new Socket("localhost", 8888);

            System.out.println("Connecting to server VN ....... \n");

            // gửi dữ liệu cho server VN
            sendDataServerVN = new ObjectOutputStream(socket3.getOutputStream());

            System.out.println("Connect Server VN success .......\n");
        } catch (Exception e) {
        }
    }

    public void sendServerVN(Data data) {
        try {

            sendDataServerVN.writeObject(data);
            System.out.println("gui data cho Server VN");
            sendDataServerVN.flush();

        } catch (Exception e) {
        }
    }

    public void connectServerUK() {
        try {
            Socket socket4 = new Socket("localhost", 8890);

            System.out.println("Connecting to server UK ....... \n");

            // gửi dữ liệu cho server
            sendDataServerUK1 = new ObjectOutputStream(socket4.getOutputStream());

            System.out.println("Connect Server UK success .......\n");
        } catch (Exception e) {
        }
    }

    public void sendServerUK(Data data) {
        try {
            data.setLocation("bank_uk");
            sendDataServerUK1.writeObject(data);
            System.out.println("gui data cho Server UK");
            sendDataServerUK1.flush();

            data.setLocation("bank_vn");
            sendServerVN(data);

        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        ServerUS us = new ServerUS();
        us.communicateClient();
    }
}
