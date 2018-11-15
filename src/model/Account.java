/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.Connection;
import connectDB.ConnectDB;
import entities.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author tuannguyen
 */
public class Account extends ConnectDB {

    private Connection connVN;
    private PreparedStatement stmt;
    private ResultSet rs;

    public Data checkLoginAdmin(Data data) {
        Data dataSend = null;
        connVN = openConnection(data.getLocation());

        String sql = "select *"
                + " from admin"
                + " where taikhoan = ? and matkhau = ?";

        try {
            stmt = connVN.prepareStatement(sql);

            stmt.setString(1, "root");
            stmt.setString(2, data.getMatkhau());

            rs = stmt.executeQuery();

            if (rs.next()) {
                dataSend = new Data();
                dataSend.setMessage("login admin ok");

            } else {
                dataSend = new Data();
                dataSend.setMessage("login that bai");
                
            }
        } catch (Exception e) {
            System.out.println("Connect DB fail !");
        }

        return dataSend;
    }

    /**
     * kiểm tra login tài khoản chính
     *
     * @param data
     * @return
     */
    public Data checkLogin(Data data) {
        Data dataSend = null;
        connVN = openConnection(data.getLocation());

        String sql = "select *"
                + " from thechinh, thephu"
                + " where thechinh.mathechinh = thephu.mathechinh"
                + " and thechinh.mathechinh = ? and thechinh.matkhau = ?";
        try {
            stmt = connVN.prepareStatement(sql);

            stmt.setInt(1, data.getMathechinh());
            stmt.setString(2, data.getMatkhau());

            rs = stmt.executeQuery();

            if (rs.next()) {
                dataSend = new Data();
                dataSend.setMathechinh(rs.getInt("mathechinh"));
                dataSend.setHoten(rs.getString("hoten"));
                dataSend.setMathephu1(rs.getInt("mathephu"));
                dataSend.setHoten1(rs.getString("hotenthephu"));

                rs.next();
                dataSend.setMathephu2(rs.getInt("mathephu"));
                dataSend.setHoten2(rs.getString("hotenthephu"));
                dataSend.setMessage("login the chinh ok");

            }
        } catch (Exception e) {
            System.out.println("Connect DB fail !");
        }

        return dataSend;
    }

    /**
     * kiểm tra login tài khoản phụ
     *
     * @param data
     * @return
     */
    public Data checkLoginThePhu(Data data) {
        Data dataSend = null;
        connVN = openConnection(data.getLocation());

        String sql = "select *"
                + " from thephu"
                + " where mathephu = ? and matkhau = ?";

        try {
            stmt = connVN.prepareStatement(sql);

            stmt.setInt(1, data.getMathephu1());
            stmt.setString(2, data.getMatkhau());

            rs = stmt.executeQuery();

            if (rs.next()) {
                dataSend = new Data();
                dataSend.setHoten1(rs.getString("hotenthephu"));
                dataSend.setMathechinh(rs.getInt("mathechinh"));
                dataSend.setMathephu1(rs.getInt("mathephu"));
                dataSend.setHanmuc(rs.getInt("hanmuc"));
                dataSend.setMessage("login the phu ok");
            } else {
                dataSend = new Data();
                dataSend.setMessage("login that bai");
            }
        } catch (Exception e) {
            System.out.println("Connect DB fail !");
        }

        return dataSend;
    }

    /**
     * kiểm tra số dư
     *
     * @param data
     * @return
     */
    public Data checkSoDu(Data data) {
        Data dataSend = null;
        connVN = openConnection(data.getLocation());

        String sql = "select *"
                + " from thechinh"
                + " where mathechinh = ?";

        try {
            stmt = connVN.prepareStatement(sql);

            stmt.setInt(1, data.getMathechinh());

            rs = stmt.executeQuery();

            if (rs.next()) {
                dataSend = new Data();
                dataSend.setSodu(rs.getInt("sodu"));
                dataSend.setMessage("check so du ok");
            }
        } catch (Exception e) {
            System.out.println("Connect DB fail !");
        }

        return dataSend;
    }

    /**
     * Rút tiền
     *
     * @param data
     * @return
     */
    public Data rutTien(Data data) {
        Data dataSend = checkSoDu(data);
        connVN = openConnection(data.getLocation());

        String sql = "update thechinh"
                + " set sodu = ?"
                + " where mathechinh = ?";

        String sqlHanMuc = "update thephu"
                + " set hanmuc = ?"
                + " where mathephu = ?";
        try {
            connVN.setAutoCommit(false);

            if (data.getHanmuc() != 0) {
                if (data.getHanmuc() - data.getSotienrut() >= 0) {

                    stmt = connVN.prepareStatement(sqlHanMuc);

                    stmt.setInt(1, data.getHanmuc() - data.getSotienrut());
                    stmt.setInt(2, data.getMathephu1());

                    stmt.executeUpdate();

                } else {
                    dataSend.setMessage("rut tien that bai");
                }
            }

            if (dataSend.getSodu() - data.getSotienrut() >= 0) {
                stmt = connVN.prepareStatement(sql);

                stmt.setInt(1, dataSend.getSodu() - data.getSotienrut());
                stmt.setInt(2, data.getMathechinh());

                stmt.executeUpdate();

                connVN.commit();
                dataSend.setMessage("rut tien ok");
            } else {

                dataSend.setMessage("rut tien that bai");
                connVN.rollback();
            }
        } catch (Exception e) {
        }
        return dataSend;
    }

    /**
     * Đổi mật khẩu
     *
     * @param data
     * @return
     */
    public Data doiMatKhau(Data data) {
        Data dataSend = null;
        connVN = openConnection(data.getLocation());

        String sql = "update thechinh"
                + " set matkhau = ?"
                + " where mathechinh = ? and matkhau = ?";

        String sqlThePhu = "update thephu"
                + " set matkhau = ?"
                + " where mathephu = ? and matkhau = ?";

        try {

            if (data.getMathephu1() != 0) {
                stmt = connVN.prepareStatement(sqlThePhu);
                stmt.setString(1, data.getMatkhaumoi());
                stmt.setInt(2, data.getMathephu1());

            } else {
                stmt = connVN.prepareStatement(sql);
                stmt.setString(1, data.getMatkhaumoi());
                stmt.setInt(2, data.getMathechinh());

            }
            stmt.setString(3, data.getMatkhau());

            stmt.executeUpdate();

            dataSend = new Data();
            dataSend.setMessage("doi mat khau ok");
        } catch (Exception e) {
            dataSend = new Data();
            dataSend.setMessage("doi mat khau that bai");
        }

        return dataSend;
    }

    public static void main(String[] args) {
        Data data = new Data();
        data.setMathephu1(888888888);
        data.setMatkhau("root");
        data.setMatkhaumoi("ngothanthhuy");
        data.setLocation("bank_vn");

        Account a = new Account();
        System.out.println(a.checkLoginAdmin(data));
    }
}
