/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package masyarakatsosial;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class warga {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010444";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_NAMA, CEK_JK, CEK_KERJA, CEK_ALAMAT=null;
    public boolean duplikasi=false;

    public warga(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

    public void simpawarga01(String id, String nama, String jk, String kerja, String alamat){
        try {
            String sqlsimpan="insert into warga(idwarga, nama, jk, pekerjaan, alamat) VALUES"
                    + " ('"+id+"','"+nama+"', '"+jk+"', '"+kerja+"', '"+alamat+"')";
            String sqlcari="select * from warga where idwarga='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Warga sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanwarga02(String id, String nama, String jk, String kerja, String alamat){
        try {
            String sqlsimpan="insert into warga(idwarga, nama, jk, pekerjaan, alamat) "
                    + "value (?, ?, ?, ?, ?)";
            String sqlcari= "select * from warga where idwarga=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
                this.duplikasi=true;
                this.CEK_NAMA=data.getString("nama");           
                this.CEK_JK=data.getString("jk");           
                this.CEK_KERJA=data.getString("pekerjaan");           
                this.CEK_ALAMAT=data.getString("alamat");               
            } else {
                this.duplikasi=false;
                this.CEK_NAMA=null;
                this.CEK_JK=null;
                this.CEK_KERJA=null;
                this.CEK_ALAMAT=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, nama);
                perintah.setString(3, jk);
                perintah.setString(4, kerja);
                perintah.setString(5, alamat);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void ubahWarga(String id, String nama, String jk, String kerja, String alamat){
        try {
            String sqlubah="UPDATE warga SET nama=?, jk=?, pekerjaan=?, alamat=? WHERE idwarga=?";        
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, nama);
            perintah.setString(2, jk);
            perintah.setString(3, kerja);
            perintah.setString(4, alamat);
            perintah.setString(5, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void hapusWarga(String id){
        try {
            String sqlhapus="delete from warga where idwarga=?";        
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id );
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void tampilDataWarga(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Warga");
            modeltabel.addColumn("Nama Warga");
            modeltabel.addColumn("Jenis Kelamin");
            modeltabel.addColumn("Pekerjaan");
            modeltabel.addColumn("Alamat");
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }    
}
