/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Roles;
import Model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ROG
 */
public class UsersDAO {
    private Connection conn;
    
    public UsersDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<Users> getAll() {
        List<Users> userList = new ArrayList<>();
            String sql = "SELECT users.user_id, users.username, users.password, users.roles_id, roles.roles_name "
                       + "FROM users "
                       + "LEFT JOIN roles ON roles.roles_id = users.roles_id";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                int user_id = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int roles_id = rs.getInt("roles_id");
                String roles_name = rs.getString("roles_name");
                
                Roles role = new Roles(roles_id, roles_name);
                Users user = new Users(user_id, username, password, role);
                
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    
    public void getById(int user_id) {
        String sql = "SELECT users.user_id, users.username, users.password, users.roles_id, roles.roles_name"
                   + "FROM users"
                   + "LEFT JOIN roles ON roles.roles_id = users.roles_id WHERE users.user_id = ?";
        
        try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, user_id);
                ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int usr_id = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int roles_id = rs.getInt("roles_id");
                String roles_name = rs.getString("roles_name");
                
                Roles role = new Roles(roles_id, roles_name);
                Users user = new Users(usr_id, username, password, role);
            } else {
                throw new SQLException("Data user tidak ditemukan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert(Users user) {
        String sql = "INSERT INTO users (username, password, roles_id)"
                   + "VALUES (?, ?, ?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getRoles().getRolesId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menambah pengguna, data pengguna tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pengguna Berhasil Ditambahkan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(Users user) {
        String sql = "UPDATE users SET username = ?, password = ?, roles_id = ?  WHERE user_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getRoles().getRolesId());
            pstmt.setInt(4, user.getUserId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan pengguna, data pengguna tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pengguna Berhasil Disimpan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int user_id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menghapus pengguna, data pengguna tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pengguna Berhasil Dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Users login(String username, String password) {
        String sql = "SELECT users.user_id, users.username, users.password, users.roles_id, roles.roles_name "
                   + "FROM users "
                   + "LEFT JOIN roles ON users.roles_id = roles.roles_id "
                   + "WHERE users.username = ? AND users.password = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int roles_id = rs.getInt("roles_id");
                String roles_name = rs.getString("roles_name");
                int user_id = rs.getInt("user_id");
                String usrname = rs.getString("username");
                String pass = rs.getString("password");
                
                Roles role = new Roles(roles_id, roles_name);
                Users user = new Users(user_id, usrname, pass, role);
                
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
