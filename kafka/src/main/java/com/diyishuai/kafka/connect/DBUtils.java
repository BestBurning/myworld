package com.diyishuai.kafka.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.DriverManager.*;

/**
 * @author Bruce
 * @date 16/9/26
 */
public class DBUtils {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn(){
        Connection conn = null;
        try {
            conn = getConnection("jdbc:mysql://localhost:3306/test","root", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException {
        Connection conn =
                getConnection("jdbc:mysql://localhost:3306/test?user=root&password=admin");
//        getConn();
    }

}
