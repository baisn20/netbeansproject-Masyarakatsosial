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

public class renbang {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010444";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_KEGIATAN, CEK_SASARAN, CEK_KETERANGAN=null;
    public boolean duplikasi=false;

    public renbang(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

    public void simpanrenbang01(String id, String kegiatan, String sasaran, String keterangan){
        try {
            String sqlsimpan="insert into renbang(idrenbang, kegiatan, sasaran, keterangan) VALUES"
                    + " ('"+id+"','"+kegiatan+"', '"+sasaran+"', '"+keterangan+"')";
            String sqlcari="select * from renbang where idrenbang='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Proker sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanrenbang02 (String id, String kegiatan, String sasaran, String keterangan){
        try {
            String sqlsimpan="insert into renbang(idrenbang, kegiatan, sasaran, keterangan) "
                    + "value (?, ?, ?, ?)";
            String sqlcari= "select * from renbang where idrenbang=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
                this.duplikasi=true;
                this.CEK_KEGIATAN=data.getString("kegiatan");           
                this.CEK_SASARAN=data.getString("sasaran");           
                this.CEK_KETERANGAN=data.getString("keterangan");               
            } else {
                this.duplikasi=false;
                this.CEK_KEGIATAN=null;
                this.CEK_SASARAN=null;
                this.CEK_KETERANGAN=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, kegiatan);
                perintah.setString(3, sasaran);
                perintah.setString(4, keterangan);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void ubahRenbang(String id, String kegiatan, String sasaran, String keterangan){
        try {
            String sqlubah="UPDATE renbang SET kegiatan=?, sasaran=?, keterangan=? WHERE idrenbang=?";        
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, kegiatan);
            perintah.setString(2, sasaran);
            perintah.setString(3, keterangan);
            perintah.setString(4, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void hapusRenbang(String id){
        try {
            String sqlhapus="delete from renbang where idrenbang=?";        
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id );
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataRenbang(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Renbang");
            modeltabel.addColumn("Kegiatan");
            modeltabel.addColumn("Sasaran");
            modeltabel.addColumn("Keterangan");
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
