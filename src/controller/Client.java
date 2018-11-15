/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import admin.QuanLyNganHang;
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
    public static QuanLyNganHang qlnh = new QuanLyNganHang();

    /**
     * Kết nối Client -> Server
     */
    public void connectServer(int port) {
        try {
            socketClient = new Socket("localhost", port);
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

    /**
     * Nhận dữ liệu từ Server -> Client
     */
    public static void receiveServer() {
        receiveServer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (true) {
                        Data dataReceive = (Data) receiveDataServer.readObject();
                        System.out.println("Client nhan dc data");
                        if ("login the chinh ok".equals(dataReceive.getMessage())) {
                            Main.login.setVisible(false);

                            tk.setVisible(true);
                            tk.thongtinthe.append("Mã thẻ         : " + dataReceive.getMathechinh() + "\n");
                            tk.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten() + "\n");
                            tk.thongtinthe.append("----------------------------------------" + "\n");
                            tk.thongtinthe.append("Mã thẻ phụ 1   : " + dataReceive.getMathephu1() + "\n");
                            tk.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten1() + "\n");
                            tk.thongtinthe.append("Mã thẻ phụ 2   : " + dataReceive.getMathephu2() + "\n");
                            tk.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten2() + "\n");

                        } else if ("login the phu ok".equals(dataReceive.getMessage())) {
                            Main.login.setVisible(false);

                            tk.setVisible(true);
                            tk.thongtinthe.append("Mã thẻ         : " + dataReceive.getMathechinh() + "\n");
                            tk.thongtinthe.append("----------------------------------------" + "\n");
                            tk.thongtinthe.append("Mã thẻ phụ     : " + dataReceive.getMathephu1() + "\n");
                            tk.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten1() + "\n");
                            tk.thongtinthe.append("Hạn mức        : " + dataReceive.getHanmuc());

                        } else if ("login admin ok".equals(dataReceive.getMessage())) {
                            MainAdmin.login.setVisible(false);

                            qlnh.setVisible(true);

                        } else if ("login that bai".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác !");

                        } else if ("check so du ok".equals(dataReceive.getMessage())) {
                            SoDu sd = new SoDu();
                            sd.setVisible(true);

                            sd.sodu.setText(String.valueOf(dataReceive.getSodu()));

                        } else if ("check so du that bai".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Tài khoản không tồn tại !");

                        } else if ("rut tien ok".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Rút tiền thành công !");
                            tk.rt.setVisible(false);
                            qlnh.rt.setVisible(false);

                        } else if ("rut tien that bai".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Rút tiền thất bại !");

                        } else if ("doi mat khau ok".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công !");
                            tk.rp.setVisible(false);

                        } else if ("doi mat khau that bai".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại !");

                        } else if ("xem chi tiet the ok".equals(dataReceive.getMessage())) {
                            qlnh.xct.thongtinthe.append("Mã thẻ         : " + dataReceive.getMathechinh() + "\n");
                            qlnh.xct.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten() + "\n");
                            qlnh.xct.thongtinthe.append("Số dư          : " + dataReceive.getSodu() + "\n");
                            qlnh.xct.thongtinthe.append("----------------------------------------" + "\n");
                            qlnh.xct.thongtinthe.append("Mã thẻ phụ 1   : " + dataReceive.getMathephu1() + "\n");
                            qlnh.xct.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten1() + "\n");
                            qlnh.xct.thongtinthe.append("Mã thẻ phụ 2   : " + dataReceive.getMathephu2() + "\n");
                            qlnh.xct.thongtinthe.append("Tên khách hàng : " + dataReceive.getHoten2() + "\n");

                        } else if ("xem chi tiet that bai".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Không tồn tại thẻ !");

                        } else if ("nap tien ok".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Nạp tiền thành công !");
                            qlnh.nt.setVisible(false);

                        } else if ("nap tien that bai".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Nạp tiền thất bại !");

                        } else if ("tao the chinh ok".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Tạo thẻ chính thành công !");

                        } else if ("tao the chinh that bai".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Tạo thẻ chính thất bại !");

                        } else if ("tao the phu ok".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Tạo thẻ phụ thành công !");

                        } else if ("tao the phu that bai".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Tạo thẻ phụ thất bại !");

                        } else if ("xoa the ok".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Xóa thẻ thành công !");

                        } else if ("xoa the that bai".equals(dataReceive.getMessage())) {
                            JOptionPane.showMessageDialog(null, "Xóa thẻ thất bại !");

                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        receiveServer.start();
    }

    /**
     * Gửi yêu cầu từ Client -> Server
     *
     * @param data
     */
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
