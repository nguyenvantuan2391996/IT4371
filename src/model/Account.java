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
import java.sql.SQLException;

/**
 *
 * @author tuannguyen
 */
public class Account extends ConnectDB {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public Data checkLoginAdmin(Data data) {
        Data dataSend = null;
        conn = openConnection(data.getLocation());

        String sql = "select *"
                + " from admin"
                + " where taikhoan = ? and matkhau = ?";

        try {
            stmt = conn.prepareStatement(sql);

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
        conn = openConnection(data.getLocation());

        String sql = "select *"
                + " from thechinh, thephu"
                + " where thechinh.mathechinh = thephu.mathechinh"
                + " and thechinh.mathechinh = ? and thechinh.matkhau = ?";
        try {
            stmt = conn.prepareStatement(sql);

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
        conn = openConnection(data.getLocation());

        String sql = "select *"
                + " from thephu"
                + " where mathephu = ? and matkhau = ?";

        try {
            stmt = conn.prepareStatement(sql);

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
        conn = openConnection(data.getLocation());

        String sql = "select *"
                + " from thechinh"
                + " where mathechinh = ?";

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.getMathechinh());

            rs = stmt.executeQuery();

            if (rs.next()) {
                dataSend = new Data();
                dataSend.setSodu(rs.getInt("sodu"));
                dataSend.setMessage("check so du ok");
            } else {
                dataSend = new Data();
                dataSend.setMessage("check so du that bai");
            }
        } catch (Exception e) {
            dataSend = new Data();
            dataSend.setMessage("check so du that bai");
        }

        return dataSend;
    }

    /**
     *
     * @param data
     * @return
     */
    public int getHanMuc(Data data) {
        int hanmuc = 0;
        conn = openConnection(data.getLocation());

        String sql = "select *"
                + " from thephu"
                + " where mathephu = ?";

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.getMathephu1());

            rs = stmt.executeQuery();
            if (rs.next()) {
                hanmuc = rs.getInt("hanmuc");
            }
        } catch (Exception e) {
        }

        return hanmuc;
    }

    public Data getInformation(Data data) {
        Data dataSend = null;
        conn = openConnection(data.getLocation());

        String sql = "select *"
                + " from thephu"
                + " where mathephu = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, data.getMathephu1());

            rs = stmt.executeQuery();
            if (rs.next()) {
                dataSend = new Data();
                dataSend.setHoten1(rs.getString("hotenthephu"));
            }
        } catch (Exception e) {
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
        Data dataInfor = getInformation(data);
        synchronized (data) {
            int hanmuc = getHanMuc(data);
            conn = openConnection(data.getLocation());

            String sql = "update thechinh"
                    + " set sodu = ?"
                    + " where mathechinh = ?";

            String sqlHanMuc = "update thephu"
                    + " set hanmuc = ?"
                    + " where mathephu = ?";
            data.setHanmuc(hanmuc);

            try {
                conn.setAutoCommit(false);

                if (data.getHanmuc() != 0) {
                    if (data.getHanmuc() - data.getSotienrut() >= 0) {

                        stmt = conn.prepareStatement(sqlHanMuc);

                        stmt.setInt(1, data.getHanmuc() - data.getSotienrut());
                        stmt.setInt(2, data.getMathephu1());

                        stmt.executeUpdate();

                        dataSend.setMathechinh(data.getMathechinh());
                        dataSend.setMathephu1(data.getMathephu1());
                        dataSend.setHoten1(dataInfor.getHoten1());
                        dataSend.setHanmuc(data.getHanmuc() - data.getSotienrut());
                    } else {
                        dataSend.setMathechinh(data.getMathechinh());
                        dataSend.setMathephu1(data.getMathephu1());
                        dataSend.setHoten1(dataInfor.getHoten1());
                        dataSend.setMessage("rut tien that bai");
                        dataSend.setHanmuc(hanmuc);
                        return dataSend;
                    }
                }

                if (dataSend.getSodu() - data.getSotienrut() >= 0) {
                    stmt = conn.prepareStatement(sql);

                    stmt.setInt(1, dataSend.getSodu() - data.getSotienrut());
                    stmt.setInt(2, data.getMathechinh());

                    stmt.executeUpdate();

                    conn.commit();
                    dataSend.setMessage("rut tien ok");
                } else {

                    dataSend.setMessage("rut tien that bai");
                    conn.rollback();
                }
            } catch (Exception e) {
            }
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
        conn = openConnection(data.getLocation());

        String sql = "update thechinh"
                + " set matkhau = ?"
                + " where mathechinh = ? and matkhau = ?";

        String sqlThePhu = "update thephu"
                + " set matkhau = ?"
                + " where mathephu = ? and matkhau = ?";

        try {

            if (data.getMathephu1() != 0) {
                stmt = conn.prepareStatement(sqlThePhu);
                stmt.setString(1, data.getMatkhaumoi());
                stmt.setInt(2, data.getMathephu1());

            } else {
                stmt = conn.prepareStatement(sql);
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

    public Data getThongTinThe(Data data) {
        Data dataSend = null;
        conn = openConnection(data.getLocation());

        String sql = "select *"
                + " from thechinh, thephu"
                + " where thechinh.mathechinh = thephu.mathechinh and thechinh.mathechinh = ?";

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.getMathechinh());

            rs = stmt.executeQuery();

            if (rs.next()) {
                dataSend = new Data();
                dataSend.setMathechinh(rs.getInt("mathechinh"));
                dataSend.setHoten(rs.getString("hoten"));
                dataSend.setSodu(rs.getInt("sodu"));
                dataSend.setMathephu1(rs.getInt("mathephu"));
                dataSend.setHoten1(rs.getString("hotenthephu"));

                rs.next();
                dataSend.setMathephu2(rs.getInt("mathephu"));
                dataSend.setHoten2(rs.getString("hotenthephu"));
                dataSend.setMessage("xem chi tiet the ok");
            } else {
                dataSend = new Data();
                dataSend.setMessage("xem chi tiet that bai");
            }
        } catch (Exception e) {
            System.out.println("Connect DB fail !");
        }

        return dataSend;
    }

    public Data napTien(Data data) {
        Data dataSend = checkSoDu(data);
        conn = openConnection(data.getLocation());

        String sql = "update thechinh"
                + " set sodu = ?"
                + " where mathechinh = ?";

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, dataSend.getSodu() + data.getSotiennap());
            stmt.setInt(2, data.getMathechinh());

            stmt.executeUpdate();
            dataSend.setMessage("nap tien ok");
        } catch (Exception e) {
            dataSend.setMessage("nap tien that bai");
        }

        return dataSend;
    }

    public Data taoTheChinh(Data data) {
        Data dataSend = null;
        conn = openConnection(data.getLocation());

        String sql = "insert into thechinh(mathechinh, matkhau, hoten, sodu)"
                + " values(?,?,?,?)";

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.getMathechinh());
            stmt.setString(2, data.getMatkhau());
            stmt.setString(3, data.getHoten());
            stmt.setInt(4, data.getSodu());

            stmt.executeUpdate();
            dataSend = new Data();
            dataSend.setMessage("tao the chinh ok");
        } catch (Exception e) {
            dataSend = new Data();
            dataSend.setMessage("tao the chinh that bai");
        }

        return dataSend;
    }

    public Data taoThePhu(Data data) {
        Data dataSend = null;
        conn = openConnection(data.getLocation());

        String sql = "insert into thephu(mathephu, mathechinh, matkhau, hotenthephu, hanmuc)"
                + " values(?,?,?,?,?)";

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.getMathephu1());
            stmt.setInt(2, data.getMathechinh());
            stmt.setString(3, data.getMatkhau());
            stmt.setString(4, data.getHoten());
            stmt.setInt(5, data.getHanmuc());

            stmt.executeUpdate();
            dataSend = new Data();
            dataSend.setMessage("tao the phu ok");
        } catch (Exception e) {
            dataSend = new Data();
            dataSend.setMessage("tao the phu that bai");
        }

        return dataSend;
    }

    public Data xoaThe(Data data) throws SQLException {
        Data dataSend = null;
        conn = openConnection(data.getLocation());

        String sqlXoaThePhu = "delete from thephu"
                + " where mathechinh = ?";

        String sqlXoaTheChinh = "delete from thechinh"
                + " where mathechinh = ?";

        try {

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sqlXoaThePhu);

            stmt.setInt(1, data.getMathechinh());

            stmt.executeUpdate();

            stmt = conn.prepareStatement(sqlXoaTheChinh);

            stmt.setInt(1, data.getMathechinh());

            stmt.executeUpdate();
            conn.setAutoCommit(true);

            dataSend = new Data();
            dataSend.setMessage("xoa the ok");
        } catch (Exception e) {
            dataSend = new Data();
            dataSend.setMessage("xoa the that bai");
            conn.rollback();
        }

        return dataSend;
    }

    public static void main(String[] args) {
        Data data = new Data();
        data.setMathechinh(123456789);
        data.setMatkhau("root");
        data.setMatkhaumoi("1");
        data.setSotiennap(1000000);
        data.setLocation("bank_vn");

        Account a = new Account();
        System.out.println(a.napTien(data));
    }
}
