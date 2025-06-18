/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Etc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ROG
 */
public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/pos_system_db?useUnicode=true&characterEncoding=UTF-8";
    private static final String user = "root";
    private static final String password = "";
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi Berhasil");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() + ":" + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}
