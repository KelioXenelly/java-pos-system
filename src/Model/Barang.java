/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ROG
 */
public class Barang {
    private int barang_id;
    private String kode_barang;
    private String nama_barang;
    
    public Barang(String kode_barang, String nama_barang) { 
        this.kode_barang = kode_barang;
        this.nama_barang = nama_barang;
    }
    
    public Barang(int barang_id, String kode_barang, String nama_barang) {
        this.barang_id = barang_id;
        this.kode_barang = kode_barang;
        this.nama_barang = nama_barang;
    }
    
    public int getBarangId() {
        return barang_id;
    }
    
    public String getKodeBarang() {
        return kode_barang;
    }
    
    public void setKodeBarang(String kode_barang) {
        this.kode_barang = kode_barang;
    }
    
    public String getNamaBarang() {
        return nama_barang;
    }
    
    public void setNamaBarang(String nama_barang) {
        this.nama_barang = nama_barang;
    }
}
