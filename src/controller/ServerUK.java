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

    public static Account accountUK = new Account();
    public static ObjectOutputStream sendDataServerVN1;
    public static ObjectOutputStream sendDataServerUS1;
    public static boolean primaryUK;

    class ThreadSocket extends Thread {

        Socket socketUK = null;

        public ThreadSocket(Socket socketUK) {
            this.socketUK = socketUK;
        }

        public void run() {
            try {
                // nhận dữ liệu từ Client 
                ObjectInputStream receiveDataClientUK = new ObjectInputStream(socketUK.getInputStream());
                // gửi dữ liệu cho client
                ObjectOutputStream sendDataClientUK = new ObjectOutputStream(socketUK.getOutputStream());

                while (true) {
                    Data dataReceive = (Data) receiveDataClientUK.readObject();
                    System.out.println("xu ly data tu client");
                    Data dataSend = null;
                    
                    synchronized (dataReceive) {
                        if ("login".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.checkLogin(dataReceive);
                            if (dataSend == null) {
                                dataSend = accountUK.checkLoginThePhu(dataReceive);
                            }
                            System.out.println("xu ly login \n");

                        } else if ("login admin".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.checkLoginAdmin(dataReceive);
                            System.out.println("Xu ly login admin \n");

                        } else if ("check so du".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.checkSoDu(dataReceive);
                            System.out.println("xu ly check so du \n");

                        } else if ("rut tien".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.rutTien(dataReceive);
                            System.out.println("xu ly rut tien \n");

                        } else if ("doi mat khau".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.doiMatKhau(dataReceive);
                            System.out.println("xu ly doi mat khau \n");

                        } else if ("xem chi tiet the".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.getThongTinThe(dataReceive);
                            System.out.println("xu ly xem chi tiet the \n");

                        } else if ("nap tien the".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.napTien(dataReceive);
                            System.out.println("xu ly nap tien the \n");

                        } else if ("tao the chinh".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.taoTheChinh(dataReceive);
                            System.out.println("xu ly tao the chinh \n");

                        } else if ("tao the phu".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.taoThePhu(dataReceive);
                            System.out.println("xu ly tao the phu \n");

                        } else if ("xoa the".equals(dataReceive.getMessage())) {
                            dataSend = accountUK.xoaThe(dataReceive);
                            System.out.println("xu ly tao xoa the \n");
                        }
                        if ("primary".equals(dataReceive.getPrimary())) {
                            primaryUK = true;
                            dataReceive.setPrimary(null);
                            connectServerUS();
                            connectServerVN();
                        }
                        if (primaryUK) {
                            // send data processed for client
                            try {
                                sendDataClientUK.writeObject(dataSend);
                                System.out.println("gui data cho client");
                                sendDataClientUK.flush();
                            } catch (Exception e) {
                            }
                            // send data from Client for ServerVN, ServerUS process
                            sendServerUS(dataReceive);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public void communicateClient() {
        try {
            ServerSocket serverUK = new ServerSocket(8890);
            System.out.println("Server is starting.........");

            while (true) {
                // chap nhan ket noi
                new ThreadSocket(serverUK.accept()).start();

                System.out.println("Client UK has been connected");
            }
        } catch (Exception e) {
        }
    }

    public void connectServerVN() {
        try {
            Socket socket5 = new Socket("localhost", 8888);

            System.out.println("Connecting to server VN ....... \n");

            // gửi dữ liệu cho server VN
            sendDataServerVN1 = new ObjectOutputStream(socket5.getOutputStream());

            System.out.println("Connect Server VN success .......\n");
        } catch (Exception e) {
        }
    }

    public void sendServerVN(Data data) {
        try {

            sendDataServerVN1.writeObject(data);
            System.out.println("gui data cho Server VN");
            sendDataServerVN1.flush();

        } catch (Exception e) {
        }
    }

    public void connectServerUS() {
        try {
            Socket socket6 = new Socket("localhost", 8889);

            System.out.println("Connecting to server US ....... \n");

            // gửi dữ liệu cho server US
            sendDataServerUS1 = new ObjectOutputStream(socket6.getOutputStream());

            System.out.println("Connect Server US success .......\n");
        } catch (Exception e) {
        }
    }

    public void sendServerUS(Data data) {
        try {
            data.setLocation("bank_us");
            sendDataServerUS1.writeObject(data);
            System.out.println("gui data cho Server US");
            sendDataServerUS1.flush();

            data.setLocation("bank_vn");
            sendServerVN(data);

        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        ServerUK uk = new ServerUK();
        uk.communicateClient();
    }
}
