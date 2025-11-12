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

public class proker {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010444";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_PROKER, CEK_WAKTUPELAKSANA, CEK_PENANGGUNGJAWAB=null;
    public boolean duplikasi=false;

    public proker(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

    public void simpanproker01(String id, String prker, String wpls, String pnjwb){
        try {
            String sqlsimpan="insert into proker(idproker, proker, waktupelaksana, penanggungjawab) VALUES"
                    + " ('"+id+"','"+prker+"', '"+wpls+"', '"+pnjwb+"')";
            String sqlcari="select * from proker where idproker='"+id+"'";
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

    public void simpanproker02(String id, String prker, String wpls, String pnjwb){
        try {
            String sqlsimpan="insert into proker(idproker, proker, waktupelaksana, penanggungjawab) value (?, ?, ?, ?)";
            String sqlcari= "select * from proker where idproker=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
                this.duplikasi=true;
                this.CEK_PROKER=data.getString("proker");           
                this.CEK_WAKTUPELAKSANA=data.getString("waktupelaksana");           
                this.CEK_PENANGGUNGJAWAB=data.getString("penanggungjawab");               
            } else {
                this.duplikasi=false;
                this.CEK_PROKER=null;
                this.CEK_WAKTUPELAKSANA=null;
                this.CEK_PENANGGUNGJAWAB=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, prker);
                perintah.setString(3, wpls);
                perintah.setString(4, pnjwb);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void ubahProker(String id, String prker, String wpls, String pnjwb){
        try {
            String sqlubah="UPDATE proker SET proker=?, waktupelaksana=?, penanggungjawab=? WHERE idproker=?";        
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, prker);
            perintah.setString(2, wpls);
            perintah.setString(3, pnjwb);
            perintah.setString(4, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void hapusProker(String id){
        try {
            String sqlhapus="delete from proker where idproker=?";        
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id );
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataProker(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Proker");
            modeltabel.addColumn("Proker");
            modeltabel.addColumn("Waktu Pelaksana");
            modeltabel.addColumn("Penanggungjawab");
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
