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

public class bansos {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010444";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_URAIAN, CEK_JENISBANTUAN, CEK_TAHUN=null;
    public boolean duplikasi=false;

    public bansos(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

    public void simpanbansos01(String id, String urai, String jnb, String thn){
        try {
            String sqlsimpan="insert into bansos(idbansos, uraian, jenisbantuan, tahun) VALUES ('"+id+"','"+urai+"', '"+jnb+"', '"+thn+"')";
            String sqlcari="select * from bansos where idbansos='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Bansos sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanbansos02(String id, String urai, String jnb, String thn){
        try {
            String sqlsimpan="insert into bansos(idbansos, uraian, jenisbantuan, tahun) value (?, ?, ?, ?)";
            String sqlcari= "select * from bansos where idbansos=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
                this.duplikasi=true;
                this.CEK_URAIAN=data.getString("uraian");           
                this.CEK_JENISBANTUAN=data.getString("jenisbantuan");           
                this.CEK_TAHUN=data.getString("tahun");               
            } else {
                this.duplikasi=false;
                this.CEK_URAIAN=null;
                this.CEK_JENISBANTUAN=null;
                this.CEK_TAHUN=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, urai);
                perintah.setString(3, jnb);
                perintah.setString(4, thn);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void ubahBansos(String id, String urai, String jnb, String thn){
        try {
            String sqlubah="UPDATE bansos SET uraian=?, jenisbantuan=?, tahun=? WHERE idbansos=?";        
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, urai);
            perintah.setString(2, jnb);
            perintah.setString(3, thn);
            perintah.setString(4, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void hapusBansos(String id){
        try {
            String sqlhapus="delete from bansos where idbansos=?";        
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id );
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataAdmin(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Bansos");
            modeltabel.addColumn("Uraian");
            modeltabel.addColumn("Jenis Bantuan");
            modeltabel.addColumn("Tahun");
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
