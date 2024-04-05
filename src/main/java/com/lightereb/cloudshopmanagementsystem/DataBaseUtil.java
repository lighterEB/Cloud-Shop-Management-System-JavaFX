package com.lightereb.cloudshopmanagementsystem;

import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moonlight
 * @Classname DataBaseUtil
 * @Description TODO
 * @Date 2024/4/5 23:26
 */
public class DataBaseUtil {

    private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "c##shopadmin";
    private static final String PASSWORD = "root321";
    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    static {
        try {
            // 1.导入驱动，加载具体驱动类
            Class.forName(DRIVER_NAME);
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (ClassNotFoundException e) {
            System.err.println("驱动加载失败");
        }catch (SQLException e) {
            System.err.println("数据库连接异常");
        }
    }


    public static Connection getConnection() {
        return con;
    }

    public static void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (con != null) {
                con.close();
                con = null;
            }
        }catch (SQLException e) {
            System.err.println("资源释放异常");
        }
    }
}
