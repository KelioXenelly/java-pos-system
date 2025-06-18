/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ROG
 */
public class Roles {
    private int roles_id;
    private String roles_name;
    
    public Roles(String roles_name) {
        this.roles_name = roles_name;
    }
    
    public Roles(int roles_id, String roles_name) { 
        this.roles_id = roles_id;
        this.roles_name = roles_name;
    }
    
    public int getRolesId() {
        return roles_id;
    }
    
    public String getRolesName() {
        return roles_name;
    }
    
    public void setRolesName(String roles_name) {
        this.roles_name = roles_name;
    }
}
