/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Etc.DatabaseConnection;
import Model.Roles;
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
public class RolesDAO {
    private Connection conn = DatabaseConnection.getConnection();
    
    public List<Roles> getAll() {
        List<Roles> rolesList = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                int roles_id = rs.getInt("roles_id");
                String roles_name = rs.getString("roles_name");
                
                Roles role = new Roles(roles_id, roles_name);
                
                rolesList.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rolesList;
    }
    
    public void getById(int roles_id) {
        String sql = "SELECT * FROM roles WHERE roles_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, roles_id);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int role_id = rs.getInt("roles_id");
                String roles_name = rs.getString("roles_name");
                
                Roles role = new Roles(role_id, roles_name);
            } else {
                throw new SQLException("Data roles tidak ditemukan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert(Roles role) {
        String sql = "INSERT INTO roles (roles_name) VALUES (?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, role.getRolesName());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menambah roles, data roles tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Roles Berhasil Ditambahkan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(Roles role) {
        String sql = "UPDATE roles SET roles_name = ? WHERE roles_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, role.getRolesName());
            pstmt.setInt(2, role.getRolesId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan roles, data roles tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Roles Berhasil Disimpan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int roles_id) {
        String sql = "DELETE FROM roles WHERE roles_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, roles_id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menghapus roles, data roles tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Roles Berhasil Dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
