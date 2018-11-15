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
                dataSend.setMessage("login ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connect DB fail !");
        }

        return dataSend;
    }

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

    public Data rutTien(Data data) {
        Data dataSend = checkSoDu(data);
        connVN = openConnection(data.getLocation());

        String sql = "update thechinh"
                + " set sodu = ?"
                + " where mathechinh = ?";
        try {
            stmt = connVN.prepareStatement(sql);
            
            stmt.setInt(1, dataSend.getSodu() - data.getSotienrut());
            stmt.setInt(2, data.getMathechinh());
            
            stmt.executeUpdate();
            dataSend.setMessage("rut tien ok");
        } catch (Exception e) {
        }
        return dataSend;
    }
    
    public Data doiMatKhau(Data data) {
        Data dataSend = null;
        connVN = openConnection(data.getLocation());
        
        String sql = "update thechinh"
                + " set matkhau = ?"
                + " where mathechinh = ? and matkhau = ?";

        try {
            stmt = connVN.prepareStatement(sql);
            
            stmt.setString(1, data.getMatkhaumoi());
            stmt.setInt(2, data.getMathechinh());
            stmt.setString(3, data.getMatkhau());

            stmt.executeUpdate();
            
            dataSend = new Data();
            dataSend.setMessage("doi mat khau ok");
        } catch (Exception e) {
        }
        
        return dataSend;
    }

    public static void main(String[] args) {
        Data data = new Data();
        data.setMathechinh(123456789);
        data.setMatkhaumoi("1");
        data.setMatkhau("2");

        Account a = new Account();
        System.out.println(a.doiMatKhau(data));
    }
}
