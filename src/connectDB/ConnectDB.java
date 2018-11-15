/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectDB;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author tuannguyen
 */
public class ConnectDB {

    protected Connection conn;
    private String url;

    public Connection openConnection(String dbname) {
        url = "jdbc:mysql://localhost:3306/" + dbname + "?useUnicode=true&characterEncoding=utf-8";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(url, "root", "");
        } catch (Exception e) {
            System.out.println("Connect DB fail");
        }
        
        return conn;
    }

    public void setAutoCommit(Connection conn) throws SQLException {
        if (conn != null) {
            conn.setAutoCommit(false);
        }
    }

    public void setCommit(Connection conn) throws SQLException{
        if (conn != null) {
            conn.commit();
        }
    }

    public void rollBack(Connection conn) throws SQLException {
        if (conn != null) {
            conn.rollback();
        }
    }
}
