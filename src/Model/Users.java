/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ROG
 */
public class Users {
    private int user_id;
    private String username;
    private String password;
    private Roles roles;
    
    public Users(String username, String password, Roles roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    
    public Users(int user_id, String username, String password, Roles roles) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    
    public int getUserId() {
        return user_id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Roles getRoles() {
        return roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
