/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import customer.Login;
import customer.SoDu;
import customer.TaiKhoan;
import entities.Data;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author tuannguyen
 */
public class Client {

    public static Socket socketClient;
    public static ObjectInputStream receiveDataServer;
    public static ObjectOutputStream sendDataServer;
    public static Thread receiveServer;
    public static Thread sendServer;
    public static TaiKhoan tk = new TaiKhoan();
    
    public void connectServer() {
        try {
            socketClient = new Socket("localhost", 8888);
            System.out.println("Connecting to server ....... \n");

            // gửi dữ liệu cho server
            sendDataServer = new ObjectOutputStream(socketClient.getOutputStream());
            // nhận dữ liệu từ server
            receiveDataServer = new ObjectInputStream(socketClient.getInputStream());

            System.out.println("Connect success .......\n");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Server isn't running ...\n");
        }
    }

    public static void receiveServer() {
        receiveServer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (true) {
                        Data dataReceive = (Data) receiveDataServer.readObject();
                        System.out.println("CLient nhan dc data");
                        if ("login ok".equals(dataReceive.getMessage())) {
                            Main.login.setVisible(false);

                            tk.setVisible(true);
                            tk.thongtinthe.append("Mã thẻ         : " + dataReceive.getMathechinh() + "\n");
                            tk.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten() + "\n");
                            tk.thongtinthe.append("----------------------------------------" + "\n");
                            tk.thongtinthe.append("Mã thẻ phụ 1   : " + dataReceive.getMathephu1()+ "\n");
                            tk.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten1()+ "\n");
                            tk.thongtinthe.append("Mã thẻ phụ 2   : " + dataReceive.getMathephu2()+ "\n");
                            tk.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten2()+ "\n");

                        } else if ("check so du ok".equals(dataReceive.getMessage())) {
                            SoDu sd = new SoDu();
                            sd.setVisible(true);

                            sd.sodu.setText(String.valueOf(dataReceive.getSodu()));
                        } else if ("rut tien ok".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Rut tien thanh cong !");
                            tk.rt.setVisible(false);
                            
                        } else if ("doi mat khau ok".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Doi mat khau thanh cong !");
                            tk.rp.setVisible(false);
                            
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        receiveServer.start();
    }

    public static void sendServer(Data data) {

        Thread sendServer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    sendDataServer.writeObject(data);
                    System.out.println("gui data cho server");
                    sendDataServer.flush();

                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Server isn't running ...\n");
                }
            }
        });
        sendServer.start();
    }
}
