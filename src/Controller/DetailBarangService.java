/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.DetailBarang;
import java.sql.Connection;
import java.util.Date;
import javax.swing.JOptionPane;
/**
 *
 * @author ROG
 */
public class DetailBarangService {
    private Connection conn;
    private BarangDAO barangDAO;
    private DetailBarangDAO detailBarangDAO;
    private PembelianDAO pembelianDAO;
    
    public DetailBarangService(Connection conn) {
        this.barangDAO = new BarangDAO(conn);
        this.detailBarangDAO = new DetailBarangDAO(conn);
        this.pembelianDAO = new PembelianDAO(conn);
    }
    
    public void updateDataBarangDanDetail(DetailBarang detailBarang) {
        // Update Detail Barang
        detailBarangDAO.update(detailBarang);
        JOptionPane.showMessageDialog(null, "Data berhasil diubah.");
    }
}
