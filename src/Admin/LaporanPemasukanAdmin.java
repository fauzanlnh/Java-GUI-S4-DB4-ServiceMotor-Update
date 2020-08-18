/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Kasir.*;
import Class.DatabaseConnection;
import Class.Login;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Fauzanlh
 */
public class LaporanPemasukanAdmin extends javax.swing.JFrame {

    /**
     * Creates new form LaporanPemasukkanService
     */
    Connection koneksi;
    String TanggalAwal, TanggalAkhir, Tahun;
    String KdJenisMotor, IdPegawai, KdFaktur, NoPol;
    int KdTeknisi;

    public LaporanPemasukanAdmin() {
        initComponents();
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "fauzan", "10118227_Fauzanlukmanulhakim_servicemotoryamaha");
        showData();
        setJDate();
    }

    //SELECT FROM DB
    public void showData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblPemasukan.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nomor Faktur", "ID Customer", "Total Service", "Total Sparepart", "Total Bayar"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null, query2;
        int No = 1;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT T_Faktur.Id_Faktur, T_Faktur.Id_Customer, T_Faktur.Total_Jasa, T_Faktur.Total_Sparepart, T_Faktur.Total_Bayar "
                    + "FROM T_Faktur "
                    + "WHERE STATUS = 'BERES'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Faktur = rs.getString("Id_Faktur");
                String Id_Customer = rs.getString("Id_Customer");
                String Total_Jasa = rs.getString("Total_Jasa");
                String Total_Sparepart = rs.getString("Total_Sparepart");
                String Total_Bayar = rs.getString("Total_Bayar");
                dtm.addRow(new String[]{"" + No, Id_Faktur, Id_Customer, Total_Jasa, Total_Sparepart, Total_Bayar});
                No = No + 1;
            }
            query2 = "SELECT SUM(T_Faktur.Total_Bayar) AS Total_Pemasukan,COUNT(Id_Faktur) AS BanyakService "
                    + "FROM T_Faktur "
                    + "WHERE STATUS = 'BERES'";
            ResultSet rs2 = stmt.executeQuery(query2);
            if (rs2.next()) {
                String Total_Pemasukan = rs2.getString("Total_Pemasukan");
                String BanyakService = rs2.getString("BanyakService");
                if (Total_Pemasukan == null) {
                    Total_Pemasukan = "0";
                }
                txtTotalPemasukan.setText(Total_Pemasukan);
                txtTotalService.setText(BanyakService);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblPemasukan.setModel(dtm);
    }

    public void cariData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblPemasukan.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nomor Faktur", "ID Customer", "Total Service", "Total Sparepart", "Total Bayar"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null, query2;
        int No = 1;
        String tanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tanggalLahir);
        String tglAwal = String.valueOf(fm.format(txtTglAwal.getDate()));
        String tglAkhir = String.valueOf(fm.format(txtTglAkhir.getDate()));
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT T_Faktur.Id_Faktur, T_Faktur.Id_Customer, T_Faktur.Total_Jasa, T_Faktur.Total_Sparepart, T_Faktur.Total_Bayar "
                    + "FROM T_Faktur "
                    + "WHERE STATUS = 'BERES' AND Tanggal BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Faktur = rs.getString("Id_Faktur");
                String Id_Customer = rs.getString("Id_Customer");
                String Total_Jasa = rs.getString("Total_Jasa");
                String Total_Sparepart = rs.getString("Total_Sparepart");
                String Total_Bayar = rs.getString("Total_Bayar");
                dtm.addRow(new String[]{"" + No, Id_Faktur, Id_Customer, Total_Jasa, Total_Sparepart, Total_Bayar});
                No = No + 1;
            }
            query2 = "SELECT SUM(T_Faktur.Total_Bayar) AS Total_Pemasukan,COUNT(Id_Faktur) AS BanyakService "
                    + "FROM T_Faktur "
                    + "WHERE STATUS = 'BERES' AND Tanggal BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "'";
            ResultSet rs2 = stmt.executeQuery(query2);
            if (rs2.next()) {
                String Total_Pemasukan = rs2.getString("Total_Pemasukan");
                String BanyakService = rs2.getString("BanyakService");
                if (Total_Pemasukan == null) {
                    Total_Pemasukan = "0";
                }
                txtTotalPemasukan.setText(Total_Pemasukan);
                txtTotalService.setText(BanyakService);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblPemasukan.setModel(dtm);
    }

    public void setJDate() {
        Calendar today = Calendar.getInstance();
        txtTglAwal.setDate(today.getTime());
        txtTglAkhir.setDate(today.getTime());
    }

    // ------------------------------------------------JFRAME POP UP-----------------------------//
    //FORM
    public void setJDate2() {
        Calendar today = Calendar.getInstance();
        txtTanggal.setDate(today.getTime());
    }

    //FORM TABLE
    public void setTableService() {
        DefaultTableModel tableModel = (DefaultTableModel) tblService.getModel();
        String KdBarang = null;
        int Harga = 0;
        String Nama_Jasa = cmbService.getSelectedItem().toString();
        String qty = txtQtyJasa.getText();
        try {
            String query = "SELECT * FROM T_Jenis_Jasa WHERE Nama_Jasa = '" + Nama_Jasa + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                KdBarang = rs.getString("Id_Jasa");
                Harga = rs.getInt("Harga_Jasa");
            }
        } catch (SQLException e) {

        }

        if (!txtQtyJasa.getText().equals("") && !cmbService.getSelectedItem().equals("-")) {
            int Qty = Integer.parseInt(qty);
            int Total = Qty * Harga;
            String HargaPerBarang = String.valueOf(Harga);
            String TotalPerDetail = String.valueOf(Total);
            String[] data = new String[6];
            data[0] = "";
            data[1] = KdBarang;
            data[2] = Nama_Jasa;
            data[3] = HargaPerBarang;
            data[4] = qty;
            data[5] = TotalPerDetail;
            tableModel.addRow(data);
            cmbService.setSelectedIndex(0);
            txtQtyJasa.setText("");
        } else if (cmbService.getSelectedIndex() == 0 && !txtQtyJasa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ISI CMB SERVICE SERVICE DENGAN BENAR");
            cmbService.requestFocus();
        } else if (cmbService.getSelectedIndex() != 0 && txtQtyJasa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ISI QTY SERVICE SERVICE DENGAN BENAR");
            txtQtyJasa.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "ISI JASA SERVICE DENGAN BENAR");
            txtQtyJasa.requestFocus();
        }
    }

    public void setTableSparepart() {
        DefaultTableModel tableModel = (DefaultTableModel) tblSparepart.getModel();
        String KdBarang = null;
        int Harga = 0;
        String NamaSparepart = cmbSparepart.getSelectedItem().toString();
        String qty = txtQtySpare.getText();
        try {
            String query = "SELECT * FROM T_Jenis_Sparepart WHERE Nama_Sparepart = '" + NamaSparepart + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                KdBarang = rs.getString("Id_Sparepart");
                Harga = rs.getInt("Harga_Sparepart");
            }
        } catch (SQLException e) {

        }

        if (!txtQtySpare.getText().equals("") && !cmbSparepart.getSelectedItem().equals("-")) {
            int Qty = Integer.parseInt(qty);
            int Total = Qty * Harga;
            String HargaPerBarang = String.valueOf(Harga);
            String TotalPerDetail = String.valueOf(Total);
            int no = tblSparepart.getRowCount() + 1;
            String No = Integer.toString(no);
            String[] data = new String[6];
            data[0] = "";
            data[1] = KdBarang;
            data[2] = NamaSparepart;
            data[3] = HargaPerBarang;
            data[4] = qty;
            data[5] = TotalPerDetail;
            tableModel.addRow(data);
            cmbSparepart.setSelectedIndex(0);
            txtQtySpare.setText("");
        } else if (cmbSparepart.getSelectedIndex() == 0 && !txtQtySpare.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ISI CMB SPAREPART SERVICE DENGAN BENAR");
            cmbSparepart.requestFocus();
        } else if (cmbSparepart.getSelectedIndex() != 0 && txtQtySpare.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ISI QTY SPAREPART SERVICE DENGAN BENAR");
            txtQtySpare.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "ISI JASA SPAREPART DENGAN BENAR");
            cmbSparepart.requestFocus();
        }
    }

    public void hapusTableService() {
        DefaultTableModel tableModel = (DefaultTableModel) tblService.getModel();
        int row = tblService.getSelectedRow();
        if (row >= 0) {
            tableModel.removeRow(row);
        }
    }

    public void hapusTableSparepart() {
        DefaultTableModel tableModel = (DefaultTableModel) tblSparepart.getModel();
        int row = tblSparepart.getSelectedRow();
        if (row >= 0) {
            tableModel.removeRow(row);
        }
    }

    //GET ATAU SELECT DARI DB
    public void getListSparepart() {
        if (!txtNoPol.getText().equals("")) {
            getKdJenisMotor();
        }
        cmbSparepart.removeAllItems();
        cmbSparepart.addItem("-");
        try {
            String SelectKD = "SELECT * FROM T_Jenis_Sparepart WHERE Id_Jenis = '" + KdJenisMotor + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                String NamaSparepart = rs.getString("Nama_Sparepart");
                cmbSparepart.addItem(NamaSparepart);
            }
        } catch (SQLException e) {

        }
    }

    public void getListService() {
        if (!txtNoPol.getText().equals("")) {
            getKdJenisMotor();
        }
        cmbService.removeAllItems();
        cmbService.addItem("-");
        try {
            String SelectKD = "SELECT * FROM T_Jenis_Jasa WHERE Id_Jenis = '" + KdJenisMotor + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                String Nama_Jasa = rs.getString("Nama_Jasa");
                cmbService.addItem(Nama_Jasa);
            }
        } catch (SQLException e) {

        }
    }

    public void getListTeknisi() {
        cmbTeknisi.removeAllItems();
        cmbTeknisi.addItem("-");
        try {
            String SelectKD = "SELECT * FROM T_Pegawai WHERE Bagian = 'TEKNISI'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                String Nama_Teknisi = rs.getString("Nama_Pegawai");
                cmbTeknisi.addItem(Nama_Teknisi);
            }
        } catch (SQLException e) {

        }
    }

    public void TampilDetSparepart() {
        String kolom[] = {"ID Det", "Kode Sparepart", "Nama Sparepart", "Harga", "Qty", "Total"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        KdFaktur = txtNoFaktur.getText();
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT T_Det_Faktur_Sparepart.Id_DetSparepart, T_Det_Faktur_Sparepart.Id_Sparepart, T_Jenis_Sparepart.Nama_Sparepart, T_Det_Faktur_Sparepart.Harga,T_Det_Faktur_Sparepart.Qty, T_Det_Faktur_Sparepart.Total_Per_Detail "
                    + "FROM T_Det_Faktur_Sparepart,T_Jenis_Sparepart "
                    + "WHERE T_Jenis_Sparepart.Id_Sparepart = T_Det_Faktur_Sparepart.Id_Sparepart AND "
                    + "Id_Faktur = '" + getKdFaktur() + "' ";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            while (rs.next()) {
                String Id_DetSparepart = rs.getString("Id_DetSparepart");
                String Id_Sparepart = rs.getString("Id_Sparepart");
                String Nama_Sparepart = rs.getString("Nama_Sparepart");
                String Harga = rs.getString("Harga");
                String Qty = rs.getString("Qty");
                String Total = rs.getString("Total_Per_Detail");
                dtm.addRow(new String[]{Id_DetSparepart, Id_Sparepart, Nama_Sparepart, Harga, Qty, Total});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
        }
        tblSparepart.setModel(dtm);

    }

    public void TampilDetService() {
        String kolom[] = {"ID Det", "Nomor Item", "Nama Item", "Harga", "Qty", "Total"};

        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        KdFaktur = txtNoFaktur.getText();
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT T_Det_Faktur_Jasa.Id_DetJasa, T_Det_Faktur_Jasa.Id_Jasa, T_Jenis_Jasa.Nama_Jasa, T_Det_Faktur_Jasa.Harga ,T_Det_Faktur_Jasa.Qty, T_Det_Faktur_Jasa.Total_Per_Detail "
                    + "FROM T_Det_Faktur_Jasa,T_Jenis_Jasa "
                    + "WHERE T_Jenis_Jasa.Id_Jasa = T_Det_Faktur_Jasa.Id_Jasa AND "
                    + "Id_Faktur = '" + getKdFaktur() + "' ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_DetJasa = rs.getString("Id_DetJasa");
                String Id_Jasa = rs.getString("Id_Jasa");
                String Nama_Jasa = rs.getString("Nama_Jasa");
                String Harga = rs.getString("Harga");
                String Qty = rs.getString("Qty");
                String Total = rs.getString("Total_Per_Detail");
                dtm.addRow(new String[]{Id_DetJasa, Id_Jasa, Nama_Jasa, Harga, Qty, Total});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
        }
        tblService.setModel(dtm);
    }

    public int getKdTeknisi() {
        String NamaTeknisi = cmbTeknisi.getSelectedItem().toString();
        try {
            String SelectKD = "SELECT * FROM T_Pegawai WHERE Nama_Pegawai = '" + NamaTeknisi + "' AND Bagian = 'TEKNISI'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                KdTeknisi = rs.getInt("Id_Pegawai");
            }
        } catch (SQLException e) {

        }
        return KdTeknisi;
    }

    public void setTotal() {
        String IdTeknisi = "";
        try {
            Statement st = koneksi.createStatement();
            String Query = "SELECT * FROM T_Faktur WHERE Id_Faktur ='" + getKdFaktur() + "'";
            ResultSet rs = st.executeQuery(Query);
            if (rs.next()) {
                txtTotalSparepart.setText(rs.getString("Total_Sparepart"));
                txtTotalService1.setText(rs.getString("Total_Jasa"));
                txtTotalBayar.setText(rs.getString("Total_Bayar"));
                txtKasir.setText(rs.getString("Id_Kasir"));
                IdTeknisi = (rs.getString("Id_Teknisi"));
            }
            String Query2 = "SELECT Nama_Pegawai FROM T_Faktur, T_Pegawai "
                    + "WHERE T_Pegawai.Id_Pegawai = T_Faktur.Id_Teknisi  AND Id_Faktur ='" + getKdFaktur() + "' AND Id_Teknisi = '" + IdTeknisi + "' AND Bagian ='TEKNISI'";
            ResultSet rs2 = st.executeQuery(Query2);
            if (rs2.next()) {
                cmbTeknisi.setSelectedItem(rs2.getString("Nama_Pegawai"));
            }
        } catch (SQLException e) {

        }
    }

    public String getKdJenisMotor() {
        try {
            String SelectKD = "SELECT * FROM T_Motor, T_Tipe WHERE No_Polisi = '" + getNoPol() + "' AND T_Motor.Id_Tipe = T_Tipe.Id_Tipe";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                KdJenisMotor = rs.getString("Id_Jenis");

            }
        } catch (SQLException e) {

        }
        return KdJenisMotor;
    }

    public String getKdFaktur() {
        return KdFaktur = tblPemasukan.getValueAt(tblPemasukan.getSelectedRow(), 1).toString();
    }

    public String getNoPol() {
        try {
            Statement st = koneksi.createStatement();
            String Query = "SELECT * FROM T_Faktur WHERE Id_Faktur ='" + getKdFaktur() + "'";
            ResultSet rs = st.executeQuery(Query);
            while (rs.next()) {
                NoPol = rs.getString("No_Polisi");
            }
        } catch (SQLException e) {

        }
        return NoPol;
    }

    //HITUNG
    public void setTotalSparepart() {
        txtTotalSparepart.setText("0");
        int TotalBayar = 0;
        if (tblSparepart.getRowCount() > 0) {
            for (int i = 0; i < tblSparepart.getRowCount(); i++) {
                if (tblSparepart.getValueAt(i, 4).equals("")) {
                    JOptionPane.showMessageDialog(null, "ISI QTY SPAREPART TERLBIH DAHULU");
                }
                int TotalPerDetail = Integer.parseInt(tblSparepart.getValueAt(i, 5) + "");
                TotalBayar = TotalBayar + TotalPerDetail;
            }
        }
        txtTotalSparepart.setText("" + TotalBayar);
    }

    public void setTotalService() {
        txtTotalService1.setText("0");
        int TotalBayar = 0;
        if (tblService.getRowCount() > 0) {
            for (int i = 0; i < tblService.getRowCount(); i++) {
                if (tblService.getValueAt(i, 4).equals("")) {
                    JOptionPane.showMessageDialog(null, "ISI QTY SERVICE TERLBIH DAHULU");
                } else {
                    int TotalPerDetail = Integer.parseInt(tblService.getValueAt(i, 5) + "");
                    TotalBayar = TotalBayar + TotalPerDetail;
                }
            }
        }
        txtTotalService1.setText("" + TotalBayar);
    }

    public void setTotalBayar() {
        int TotalService = Integer.parseInt(txtTotalService1.getText());
        int TotalSparepart = Integer.parseInt(txtTotalSparepart.getText());
        int TotalBayar = TotalService + TotalSparepart;
        txtTotalBayar.setText("" + TotalBayar);
    }

    //INSERT * UPDATE
    public void InsertDataDetFaktur() {
        //TIPE DATA
        String InsertDetSparepart = null, UpdateStok = null, InsertDetService = null;

        //GET FROM JFRAME
        DefaultTableModel tSparepart = (DefaultTableModel) tblSparepart.getModel();
        DefaultTableModel tService = (DefaultTableModel) tblService.getModel();
        String KdFaktur = txtNoFaktur.getText().toUpperCase();
        String IdKasir = txtKasir.getText();
        String TotSparepart = txtTotalSparepart.getText();
        String TotJasa = txtTotalService1.getText();
        String TotBayar = txtTotalBayar.getText();
        int BerhasilDetSparepart = 0, BerhasilDetService = 0, BerhasilUpdateStok = 0;
        String tanggalMySQL = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tanggalMySQL);
        String Tanggal = String.valueOf(fm.format(txtTanggal.getDate()));

        //VALIDASI FORM
        if (cmbTeknisi.getSelectedItem().equals("-")) {
            JOptionPane.showMessageDialog(null, "NAMA TEKNISI TIDAK BOLEH KOSONG");
            btnHitung.requestFocus();
        } else if (tblSparepart.getRowCount() < 1 && tblService.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "PEMBELIAN SPAREPART ATAU PEMAKAIAN JASA SERVICE TIDAK BOLEH KOSONG");
            cmbService.requestFocus();
        } else if (txtTotalSparepart.getText().equals("0") && txtTotalService.getText().equals("0") && (tSparepart.getRowCount() > 0 || tService.getRowCount() > 0)) {
            JOptionPane.showMessageDialog(null, "TEKAN TOMBOL HITUNG");
            btnHitung.requestFocus();
        } else {
            try {
                Statement stmt = koneksi.createStatement();
                //INSERT TABEL DETAIL FAKTUR SPAREPART
                if (tSparepart.getRowCount() > 0) {
                    for (int i = 0; i < tSparepart.getRowCount(); i++) {
                        if (tSparepart.getValueAt(i, 0).toString().equals("")) {
                            InsertDetSparepart = ("INSERT INTO T_Det_Faktur_Sparepart (Id_Faktur, Id_Sparepart, Harga, Qty, Total_Per_Detail) VALUES("
                                    + "'" + KdFaktur + "',"
                                    + "'" + tSparepart.getValueAt(i, 1) + "',"
                                    + "'" + tSparepart.getValueAt(i, 3) + "',"
                                    + "'" + tSparepart.getValueAt(i, 4) + "',"
                                    + "'" + tSparepart.getValueAt(i, 5) + "')");
                            BerhasilDetSparepart = stmt.executeUpdate(InsertDetSparepart);
                            UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = Stok - '" + tSparepart.getValueAt(i, 4) + "' WHERE Id_Sparepart = '" + tSparepart.getValueAt(i, 1) + "'";
                            System.out.println(InsertDetSparepart);
                        } else {
                            String getQtyLama = "SELECT * FROM T_Det_Faktur_Sparepart WHERE Id_DetSparepart = '" + tSparepart.getValueAt(i, 0) + "'";
                            int QtyLama = 0, QtyBaru = 0;
                            ResultSet rs = stmt.executeQuery(getQtyLama);
                            if (rs.next()) {
                                QtyLama = rs.getInt("Qty");
                            }
                            QtyBaru = Integer.valueOf(tSparepart.getValueAt(i, 4).toString()) - QtyLama;
                            String UpdateDetSparepart = "UPDATE T_Det_Faktur_Sparepart SET Qty = '" + tSparepart.getValueAt(i, 4) + "', Total_Per_Detail = '" + tSparepart.getValueAt(i, 5) + "' "
                                    + "WHERE Id_DetSparepart = '" + tSparepart.getValueAt(i, 0) + "'";
                            UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = Stok - '" + QtyBaru + "' WHERE Id_Sparepart = '" + tSparepart.getValueAt(i, 1) + "'";
                            BerhasilDetSparepart = stmt.executeUpdate(UpdateDetSparepart);
                        }
                        BerhasilUpdateStok = stmt.executeUpdate(UpdateStok);
                    }
                } else if (tSparepart.getRowCount() < 1) {
                    BerhasilDetSparepart = 1;
                    BerhasilUpdateStok = 1;
                }

                //INSERT TABEL DETAIL FAKTUR SERVICE
                if (tService.getRowCount() > 0) {
                    for (int i = 0; i < tService.getRowCount(); i++) {
                        if (tService.getValueAt(i, 0).toString().equals("")) {
                            InsertDetService = ("INSERT INTO T_Det_Faktur_Jasa (Id_Faktur, Id_Jasa, Harga, Qty, Total_Per_Detail) VALUES("
                                    + "'" + KdFaktur + "',"
                                    + "'" + tService.getValueAt(i, 1) + "',"
                                    + "'" + tService.getValueAt(i, 3) + "',"
                                    + "'" + tService.getValueAt(i, 4) + "',"
                                    + "'" + tService.getValueAt(i, 5) + "')");
                            BerhasilDetService = stmt.executeUpdate(InsertDetService);
                            System.out.println(InsertDetService);
                        } else {
                            String UpdateDetJasa = "UPDATE T_Det_Faktur_Jasa SET Qty = '" + tService.getValueAt(i, 4) + "', Total_Per_Detail = '" + tService.getValueAt(i, 5) + "'"
                                    + "WHERE Id_DetJasa = '" + tService.getValueAt(i, 0) + "'";
                            BerhasilDetService = stmt.executeUpdate(UpdateDetJasa);
                            System.out.println(BerhasilDetService);
                        }
                    }
                } else if (tService.getRowCount() < 1) {
                    BerhasilDetService = 1;
                }

                //UPDATE TABEL FAKTUR
                String UpdateFaktur = "UPDATE T_FAKTUR SET Id_Teknisi = '" + getKdTeknisi() + "', Id_Kasir = '" + IdKasir + "', Tanggal = '" + Tanggal + "',Status = 'BERES', "
                        + "Total_Sparepart = '" + TotSparepart + "', Total_Jasa = '" + TotJasa + "', Total_Bayar = '" + TotBayar + "' WHERE Id_Faktur = '" + KdFaktur + "'";
                int BerhasilUpdFaktur = stmt.executeUpdate(UpdateFaktur);

                //VALIDASI BERHASIL INSERT DAN UPDATE
                if (BerhasilDetSparepart == 1 && BerhasilUpdateStok == 1 && BerhasilDetService == 1 && BerhasilUpdFaktur == 1) {
                    JOptionPane.showMessageDialog(null, "DATA FAKTUR BERHASIL DIMASUKKAN");
                    ubahFaktur.dispose();
                    showData();
                    try {
                        koneksi.commit();
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                } else if (BerhasilDetSparepart == 0) {
                    JOptionPane.showMessageDialog(null, "DATA DET FAKTUR SPAREPART GAGAL DIMASUKKAN");
                    System.out.println(InsertDetSparepart);
                } else if (BerhasilUpdateStok == 0) {
                    JOptionPane.showMessageDialog(null, "DATA STOK SPAREPART GAGAL DI UBAH");
                    System.out.println(UpdateStok);
                } else if (BerhasilDetService == 0) {
                    JOptionPane.showMessageDialog(null, "DATA DET FAKTUR SERVICE GAGAL DIMASUKKAN");
                    System.out.println(InsertDetService);
                } else if (BerhasilUpdFaktur == 0) {
                    JOptionPane.showMessageDialog(null, "UPDATE FAKTUR GAGAL DI UBAH");
                    System.out.println(UpdateFaktur);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
            }
        }
    }

    public void DeleteAll() {
        String IdFaktur = tblPemasukan.getValueAt(tblPemasukan.getSelectedRow(), 1).toString();
        DefaultTableModel tSparepart = (DefaultTableModel) tblSparepart.getModel();
        int BerhasilUpdateStok = 0;
        try {
            Statement stmt = koneksi.createStatement();
            for (int i = 0; i < tSparepart.getRowCount(); i++) {
                String UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = Stok + '" + tSparepart.getValueAt(i, 4) + "' WHERE Id_Sparepart = '" + tSparepart.getValueAt(i, 1) + "'";
                BerhasilUpdateStok = stmt.executeUpdate(UpdateStok);
            }
            String DeleteFaktur = "DELETE FROM t_faktur WHERE Id_Faktur = '" + IdFaktur + "'";
            int BDeleteFaktur = stmt.executeUpdate(DeleteFaktur);
            if (BDeleteFaktur > 0 && BerhasilUpdateStok > 0) {
                JOptionPane.showMessageDialog(null, "Data Faktur Berhasil Dihapus");
                showData();
            } else if (BerhasilUpdateStok < 1) {
                JOptionPane.showMessageDialog(null, "DATA STOK GAGAL DIUBAH");
            } else if (BDeleteFaktur < 1) {
                JOptionPane.showMessageDialog(null, "DATA FAKTUR GAGAL DIHAPUS");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteDetSparepart() {
        DefaultTableModel tSparepart = (DefaultTableModel) tblSparepart.getModel();
        try {
            Statement stmt = koneksi.createStatement();
            String Delete = "DELETE FROM T_Det_Faktur_Sparepart "
                    + "WHERE Id_DetSparepart = '" + tSparepart.getValueAt(tblSparepart.getSelectedRow(), 0).toString() + "'";
            int berhasil = stmt.executeUpdate(Delete);
            String UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = Stok + '" + tSparepart.getValueAt(tblSparepart.getSelectedRow(), 4) + "' "
                    + "WHERE Id_Sparepart = '" + tSparepart.getValueAt(tblSparepart.getSelectedRow(), 1) + "'";
            int bUPdate = stmt.executeUpdate(UpdateStok);
            System.out.println(Delete);
            hapusTableSparepart();
            setTotalSparepart();
            setTotalService();
            setTotalBayar();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteDetService() {
        DefaultTableModel tService = (DefaultTableModel) tblService.getModel();
        try {
            Statement stmt = koneksi.createStatement();
            String Delete = "DELETE FROM T_Det_Faktur_Jasa WHERE Id_DetJasa = '" + tService.getValueAt(tblService.getSelectedRow(), 0).toString() + "'";
            stmt.executeUpdate(Delete);
            int berhasil = stmt.executeUpdate(Delete);
            System.out.println(Delete);
            hapusTableService();
            setTotalSparepart();
            setTotalService();
            setTotalBayar();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // ------------------------------------------------JFRAME POP UP-----------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ubahFaktur = new javax.swing.JFrame();
        mainPanel3 = new javax.swing.JPanel();
        PanelDirectory3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblNoPol8 = new javax.swing.JLabel();
        lblService3 = new javax.swing.JLabel();
        cmbService = new javax.swing.JComboBox<>();
        lblSparepart1 = new javax.swing.JLabel();
        cmbSparepart = new javax.swing.JComboBox<>();
        lblNoPol9 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        btnHapusServices = new javax.swing.JButton();
        btnHapusSparepart = new javax.swing.JButton();
        btnTambahSparepart = new javax.swing.JButton();
        btnTambahService = new javax.swing.JButton();
        txtNoPol = new javax.swing.JTextField();
        txtNoFaktur = new javax.swing.JTextField();
        lblDetServices1 = new javax.swing.JLabel();
        tblDetServices1 = new javax.swing.JScrollPane();
        tblSparepart = new javax.swing.JTable();
        tblDetSparepart2 = new javax.swing.JScrollPane();
        tblService = new javax.swing.JTable();
        lblDetSparepart2 = new javax.swing.JLabel();
        lblService15 = new javax.swing.JLabel();
        lblService20 = new javax.swing.JLabel();
        txtTotalService1 = new javax.swing.JLabel();
        txtTotalSparepart = new javax.swing.JLabel();
        lblService19 = new javax.swing.JLabel();
        lblService16 = new javax.swing.JLabel();
        lblService17 = new javax.swing.JLabel();
        lblService18 = new javax.swing.JLabel();
        txtTotalBayar = new javax.swing.JLabel();
        cmbTeknisi = new javax.swing.JComboBox<>();
        lblSparepart2 = new javax.swing.JLabel();
        lblSparepart3 = new javax.swing.JLabel();
        txtKasir = new javax.swing.JLabel();
        txtQtyJasa = new javax.swing.JTextField();
        lblService1 = new javax.swing.JLabel();
        lblService2 = new javax.swing.JLabel();
        txtQtySpare = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        lblNoPol2 = new javax.swing.JLabel();
        btnHitung = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        mainPanel1 = new javax.swing.JPanel();
        PanelDirectory = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPemasukan = new javax.swing.JTable();
        btnPrint1 = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtTotalPemasukan = new javax.swing.JLabel();
        txtTotalService = new javax.swing.JLabel();
        txtTglAwal = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        txtTglAkhir = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnDeleteAll = new javax.swing.JButton();

        ubahFaktur.setUndecorated(true);

        mainPanel3.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel3.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory3.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory3.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Laporan Pemasukan/ Ubah Data");

        javax.swing.GroupLayout PanelDirectory3Layout = new javax.swing.GroupLayout(PanelDirectory3);
        PanelDirectory3.setLayout(PanelDirectory3Layout);
        PanelDirectory3Layout.setHorizontalGroup(
            PanelDirectory3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addContainerGap(360, Short.MAX_VALUE))
        );
        PanelDirectory3Layout.setVerticalGroup(
            PanelDirectory3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectory3Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(39, 39, 39))
        );

        lblNoPol8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol8.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol8.setText("No Faktur");

        lblService3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService3.setForeground(new java.awt.Color(51, 51, 51));
        lblService3.setText("Service");

        cmbService.setBackground(new java.awt.Color(255, 255, 255));
        cmbService.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbService.setForeground(new java.awt.Color(51, 51, 51));
        cmbService.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));

        lblSparepart1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSparepart1.setForeground(new java.awt.Color(51, 51, 51));
        lblSparepart1.setText("Sparepart");

        cmbSparepart.setBackground(new java.awt.Color(255, 255, 255));
        cmbSparepart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbSparepart.setForeground(new java.awt.Color(51, 51, 51));
        cmbSparepart.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));

        lblNoPol9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol9.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol9.setText("No Polisi");

        btnSubmit.setBackground(new java.awt.Color(240, 240, 240));
        btnSubmit.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(51, 51, 51));
        btnSubmit.setText("Submit");
        btnSubmit.setBorder(null);
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnHapusServices.setBackground(new java.awt.Color(240, 240, 240));
        btnHapusServices.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHapusServices.setForeground(new java.awt.Color(51, 51, 51));
        btnHapusServices.setText("Hapus");
        btnHapusServices.setBorder(null);
        btnHapusServices.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusServicesMouseClicked(evt);
            }
        });

        btnHapusSparepart.setBackground(new java.awt.Color(240, 240, 240));
        btnHapusSparepart.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHapusSparepart.setForeground(new java.awt.Color(51, 51, 51));
        btnHapusSparepart.setText("Hapus");
        btnHapusSparepart.setBorder(null);
        btnHapusSparepart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusSparepartMouseClicked(evt);
            }
        });

        btnTambahSparepart.setBackground(new java.awt.Color(240, 240, 240));
        btnTambahSparepart.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTambahSparepart.setForeground(new java.awt.Color(51, 51, 51));
        btnTambahSparepart.setText("+");
        btnTambahSparepart.setBorder(null);
        btnTambahSparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSparepartActionPerformed(evt);
            }
        });

        btnTambahService.setBackground(new java.awt.Color(240, 240, 240));
        btnTambahService.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTambahService.setForeground(new java.awt.Color(51, 51, 51));
        btnTambahService.setText("+");
        btnTambahService.setBorder(null);
        btnTambahService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahServiceActionPerformed(evt);
            }
        });

        txtNoPol.setEditable(false);
        txtNoPol.setBackground(new java.awt.Color(255, 255, 255));
        txtNoPol.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoPol.setForeground(new java.awt.Color(51, 51, 51));
        txtNoPol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtNoFaktur.setEditable(false);
        txtNoFaktur.setBackground(new java.awt.Color(255, 255, 255));
        txtNoFaktur.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoFaktur.setForeground(new java.awt.Color(51, 51, 51));
        txtNoFaktur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblDetServices1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDetServices1.setForeground(new java.awt.Color(51, 51, 51));
        lblDetServices1.setText("List Sparepart");

        tblSparepart.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblSparepart.setForeground(new java.awt.Color(51, 51, 51));
        tblSparepart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Det", "Kode Sparepart", "Nama Sparepart", "Harga", "Qty", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSparepart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblSparepartKeyReleased(evt);
            }
        });
        tblDetServices1.setViewportView(tblSparepart);
        if (tblSparepart.getColumnModel().getColumnCount() > 0) {
            tblSparepart.getColumnModel().getColumn(4).setResizable(false);
        }

        tblService.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblService.setForeground(new java.awt.Color(51, 51, 51));
        tblService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Service", "Nama Service", "Harga"
            }
        ));
        tblService.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblServiceKeyReleased(evt);
            }
        });
        tblDetSparepart2.setViewportView(tblService);

        lblDetSparepart2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDetSparepart2.setForeground(new java.awt.Color(51, 51, 51));
        lblDetSparepart2.setText("List Services");

        lblService15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService15.setForeground(new java.awt.Color(51, 51, 51));
        lblService15.setText("Total Service");

        lblService20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService20.setForeground(new java.awt.Color(51, 51, 51));
        lblService20.setText("Rp.");

        txtTotalService1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalService1.setForeground(new java.awt.Color(51, 51, 51));
        txtTotalService1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalService1.setText("0");

        txtTotalSparepart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalSparepart.setForeground(new java.awt.Color(51, 51, 51));
        txtTotalSparepart.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalSparepart.setText("0");

        lblService19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService19.setForeground(new java.awt.Color(51, 51, 51));
        lblService19.setText("Rp.");

        lblService16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService16.setForeground(new java.awt.Color(51, 51, 51));
        lblService16.setText("Total Spare Part");

        lblService17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService17.setForeground(new java.awt.Color(51, 51, 51));
        lblService17.setText("Total Bayar");

        lblService18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService18.setForeground(new java.awt.Color(51, 51, 51));
        lblService18.setText("Rp.");

        txtTotalBayar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalBayar.setForeground(new java.awt.Color(51, 51, 51));
        txtTotalBayar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalBayar.setText("0");

        cmbTeknisi.setBackground(new java.awt.Color(255, 255, 255));
        cmbTeknisi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbTeknisi.setForeground(new java.awt.Color(51, 51, 51));

        lblSparepart2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSparepart2.setForeground(new java.awt.Color(51, 51, 51));
        lblSparepart2.setText("Teknisi");

        lblSparepart3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSparepart3.setForeground(new java.awt.Color(51, 51, 51));
        lblSparepart3.setText("Kasir");

        txtKasir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtKasir.setForeground(new java.awt.Color(51, 51, 51));

        txtQtyJasa.setBackground(new java.awt.Color(255, 255, 255));
        txtQtyJasa.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtQtyJasa.setForeground(new java.awt.Color(51, 51, 51));
        txtQtyJasa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblService1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService1.setForeground(new java.awt.Color(51, 51, 51));
        lblService1.setText("Qty");

        lblService2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService2.setForeground(new java.awt.Color(51, 51, 51));
        lblService2.setText("Qty");

        txtQtySpare.setBackground(new java.awt.Color(255, 255, 255));
        txtQtySpare.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtQtySpare.setForeground(new java.awt.Color(51, 51, 51));
        txtQtySpare.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtTanggal.setBackground(new java.awt.Color(255, 255, 255));
        txtTanggal.setForeground(new java.awt.Color(51, 51, 51));

        lblNoPol2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol2.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol2.setText("Tanggal");

        btnHitung.setBackground(new java.awt.Color(240, 240, 240));
        btnHitung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHitung.setForeground(new java.awt.Color(51, 51, 51));
        btnHitung.setText("Hitung");
        btnHitung.setBorder(null);
        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(240, 240, 240));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(51, 51, 51));
        btnCancel.setText("Cancel");
        btnCancel.setBorder(null);
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanel3Layout = new javax.swing.GroupLayout(mainPanel3);
        mainPanel3.setLayout(mainPanel3Layout);
        mainPanel3Layout.setHorizontalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelDirectory3, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tblDetSparepart2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(mainPanel3Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnHapusServices, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanel3Layout.createSequentialGroup()
                                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(lblService15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblService16, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addComponent(lblService17, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                                .addComponent(lblService18, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtTotalBayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                                .addComponent(lblService19, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtTotalSparepart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                                .addComponent(lblService20, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtTotalService1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(mainPanel3Layout.createSequentialGroup()
                                        .addComponent(btnHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tblDetServices1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(mainPanel3Layout.createSequentialGroup()
                                        .addComponent(lblDetSparepart2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnHapusSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSparepart2)
                                    .addComponent(cmbTeknisi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSparepart3)
                                    .addComponent(txtKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNoPol2))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDetServices1)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNoPol9)
                                    .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNoPol8)
                                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblService3)
                                    .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblService1)
                            .addComponent(txtQtyJasa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblSparepart1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbSparepart, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblService2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambahSparepart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtQtySpare, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(111, 111, 111))))
        );
        mainPanel3Layout.setVerticalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(PanelDirectory3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(txtQtyJasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblNoPol9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblNoPol8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblService3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23)
                        .addComponent(lblDetServices1))
                    .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(mainPanel3Layout.createSequentialGroup()
                            .addComponent(lblService1)
                            .addGap(69, 69, 69))
                        .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblSparepart1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblService2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQtySpare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTambahSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(14, 14, 14)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addComponent(tblDetServices1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(lblDetSparepart2))
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanel3Layout.createSequentialGroup()
                                        .addComponent(lblSparepart3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnHapusSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblNoPol2)
                                .addGap(35, 35, 35))
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSparepart2)
                        .addGap(12, 12, 12)
                        .addComponent(cmbTeknisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)))
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addComponent(tblDetSparepart2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanel3Layout.createSequentialGroup()
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblService15)
                            .addComponent(lblService20)
                            .addComponent(txtTotalService1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblService16)
                            .addComponent(lblService19)
                            .addComponent(txtTotalSparepart))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblService18)
                            .addComponent(lblService17)
                            .addComponent(txtTotalBayar))
                        .addGap(20, 20, 20)))
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapusServices, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 50, Short.MAX_VALUE))
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(52, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout ubahFakturLayout = new javax.swing.GroupLayout(ubahFaktur.getContentPane());
        ubahFaktur.getContentPane().setLayout(ubahFakturLayout);
        ubahFakturLayout.setHorizontalGroup(
            ubahFakturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
        );
        ubahFakturLayout.setVerticalGroup(
            ubahFakturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Laporan Pemasukkan");

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel1.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Admin/Laporan Pemasukkan");

        javax.swing.GroupLayout PanelDirectoryLayout = new javax.swing.GroupLayout(PanelDirectory);
        PanelDirectory.setLayout(PanelDirectoryLayout);
        PanelDirectoryLayout.setHorizontalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectoryLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel8)
                .addContainerGap(397, Short.MAX_VALUE))
        );
        PanelDirectoryLayout.setVerticalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectoryLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(39, 39, 39))
        );

        tblPemasukan.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblPemasukan.setForeground(new java.awt.Color(0, 0, 0));
        tblPemasukan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "No Faktur", "ID Customer", "Total Service", "Total Sparepart", "Total Bayar"
            }
        ));
        jScrollPane2.setViewportView(tblPemasukan);

        btnPrint1.setBackground(new java.awt.Color(240, 240, 240));
        btnPrint1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPrint1.setForeground(new java.awt.Color(51, 51, 51));
        btnPrint1.setText("Print");
        btnPrint1.setBorder(null);
        btnPrint1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrint1MouseClicked(evt);
            }
        });

        btnCari.setBackground(new java.awt.Color(240, 240, 240));
        btnCari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCari.setForeground(new java.awt.Color(51, 51, 51));
        btnCari.setText("Cari");
        btnCari.setBorder(null);
        btnCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCariMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Total Pemasukkan :");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Total Service :");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Rp.");

        txtTotalPemasukan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalPemasukan.setForeground(new java.awt.Color(51, 51, 51));
        txtTotalPemasukan.setText("0");

        txtTotalService.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalService.setForeground(new java.awt.Color(51, 51, 51));
        txtTotalService.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalService.setText("0");

        txtTglAwal.setBackground(new java.awt.Color(255, 255, 255));
        txtTglAwal.setForeground(new java.awt.Color(51, 51, 51));

        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Tanggal Awal :");

        txtTglAkhir.setBackground(new java.awt.Color(255, 255, 255));
        txtTglAkhir.setForeground(new java.awt.Color(51, 51, 51));

        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Tanggal Akhir :");

        btnEdit.setBackground(new java.awt.Color(240, 240, 240));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(51, 51, 51));
        btnEdit.setText("Edit");
        btnEdit.setBorder(null);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDeleteAll.setBackground(new java.awt.Color(240, 240, 240));
        btnDeleteAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDeleteAll.setForeground(new java.awt.Color(51, 51, 51));
        btnDeleteAll.setText("Delete");
        btnDeleteAll.setBorder(null);
        btnDeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalService, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotalPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanel1Layout.createSequentialGroup()
                            .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(txtTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                    .addComponent(txtTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(9, 9, 9)
                        .addComponent(txtTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10)
                        .addGap(6, 6, 6)
                        .addComponent(txtTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTotalService))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19)
                            .addComponent(txtTotalPemasukan))))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
int Click = 0;
    private void btnPrint1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrint1MouseClicked
        String Berdasarkan = "Semua Pemasukkan", tglAwal = "", tglAkhir = "";
        try {
            //Koneksi Database
            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "fauzan", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
            //CETAK DATA
            HashMap parameter = new HashMap();
            //AMBIL FILE
            if (Click == 1) {
                String tanggalLahir = "yyyy-MM-dd";
                SimpleDateFormat fm = new SimpleDateFormat(tanggalLahir);
                tglAwal = String.valueOf(fm.format(txtTglAwal.getDate()));
                tglAkhir = String.valueOf(fm.format(txtTglAkhir.getDate()));
                Berdasarkan = "Tanggal";
            }
            parameter.put("bds", Berdasarkan);
            parameter.put("tglAwal", tglAwal);
            parameter.put("tglAkhir", tglAkhir);
            File file = new File("src/Report/LaporanPemasukkan.jasper");
            JasperReport jr = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameter, c);
            //AGAR TIDAK MENGCLOSE APLIKASi
            JasperViewer.viewReport(jp, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e);
        }
    }//GEN-LAST:event_btnPrint1MouseClicked

    private void btnCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCariMouseClicked
        cariData();
        Click = 1;
    }//GEN-LAST:event_btnCariMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        setJDate2();
        getKdFaktur();
        getNoPol();
        getKdJenisMotor();
        getListTeknisi();
        getListSparepart();
        getListService();
        TampilDetSparepart();
        TampilDetService();
        setTotal();
        txtNoFaktur.setText(getKdFaktur());
        txtNoPol.setText(getNoPol());
        ubahFaktur.show();
        ubahFaktur.setSize(710, 673);
        ubahFaktur.setLocationRelativeTo(null);
        try {
            koneksi.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Data Yang Dipilih Akan Dihapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            getKdFaktur();
            TampilDetSparepart();
            DeleteAll();
        }

    }//GEN-LAST:event_btnDeleteAllActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Data Yang Dimasukkan Benar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            InsertDataDetFaktur();
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnHapusServicesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusServicesMouseClicked
        int ok = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            if (tblService.getValueAt(tblService.getSelectedRow(), 0).toString().equals("")) {
                hapusTableService();
            } else {
                deleteDetService();
            }
        }
    }//GEN-LAST:event_btnHapusServicesMouseClicked

    private void btnHapusSparepartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusSparepartMouseClicked
        int ok = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            if (tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 0).toString().equals("")) {
                hapusTableSparepart();
            } else {
                deleteDetSparepart();
            }
        }
    }//GEN-LAST:event_btnHapusSparepartMouseClicked

    private void btnTambahSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSparepartActionPerformed
        setTableSparepart();
        setTotalSparepart();
        setTotalService();
        setTotalBayar();
        System.out.println(tblSparepart.getValueAt(0, 0).toString());
    }//GEN-LAST:event_btnTambahSparepartActionPerformed

    private void btnTambahServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahServiceActionPerformed
        setTableService();
        setTotalSparepart();
        setTotalService();
        setTotalBayar();
        System.out.println(tblService.getValueAt(0, 0).toString());
    }//GEN-LAST:event_btnTambahServiceActionPerformed

    private void tblSparepartKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblSparepartKeyReleased
        int Total = 0;
        int Qty = 0;
        String getHarga = tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 3).toString();
        String getQty = tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 4).toString();
        try {
            Qty = Integer.parseInt(getQty);
        } catch (NumberFormatException e) {

        }

        int Harga = Integer.parseInt(getHarga);
        Total = Qty * Harga;
        tblSparepart.setValueAt(Total, tblSparepart.getSelectedRow(), 5);
        setTotalSparepart();
        setTotalService();
        setTotalBayar();
    }//GEN-LAST:event_tblSparepartKeyReleased

    private void tblServiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblServiceKeyReleased
        int Total = 0;
        int Qty = 0;
        String getHarga = tblService.getValueAt(tblService.getSelectedRow(), 3).toString();
        String getQty = tblService.getValueAt(tblService.getSelectedRow(), 4).toString();
        try {
            Qty = Integer.parseInt(getQty);
        } catch (NumberFormatException e) {

        }
        int Harga = Integer.parseInt(getHarga);
        Total = Qty * Harga;
        tblService.setValueAt(Total, tblService.getSelectedRow(), 5);
        setTotalSparepart();
        setTotalService();
        setTotalBayar();
    }//GEN-LAST:event_tblServiceKeyReleased

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        setTotalSparepart();
        setTotalService();
        setTotalBayar();
    }//GEN-LAST:event_btnHitungActionPerformed

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        // TODO add your handling code here:
        try {
            koneksi.rollback();
            ubahFaktur.dispose();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LaporanPemasukanAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaporanPemasukanAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaporanPemasukanAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaporanPemasukanAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LaporanPemasukanAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JPanel PanelDirectory3;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnDeleteAll;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapusServices;
    private javax.swing.JButton btnHapusSparepart;
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnPrint1;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnTambahService;
    private javax.swing.JButton btnTambahSparepart;
    private javax.swing.JComboBox<String> cmbService;
    private javax.swing.JComboBox<String> cmbSparepart;
    private javax.swing.JComboBox<String> cmbTeknisi;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDetServices1;
    private javax.swing.JLabel lblDetSparepart2;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblNoPol8;
    private javax.swing.JLabel lblNoPol9;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblService15;
    private javax.swing.JLabel lblService16;
    private javax.swing.JLabel lblService17;
    private javax.swing.JLabel lblService18;
    private javax.swing.JLabel lblService19;
    private javax.swing.JLabel lblService2;
    private javax.swing.JLabel lblService20;
    private javax.swing.JLabel lblService3;
    private javax.swing.JLabel lblSparepart1;
    private javax.swing.JLabel lblSparepart2;
    private javax.swing.JLabel lblSparepart3;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel mainPanel3;
    private javax.swing.JScrollPane tblDetServices1;
    private javax.swing.JScrollPane tblDetSparepart2;
    private javax.swing.JTable tblPemasukan;
    private javax.swing.JTable tblService;
    private javax.swing.JTable tblSparepart;
    public javax.swing.JLabel txtKasir;
    private javax.swing.JTextField txtNoFaktur;
    private javax.swing.JTextField txtNoPol;
    private javax.swing.JTextField txtQtyJasa;
    private javax.swing.JTextField txtQtySpare;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private com.toedter.calendar.JDateChooser txtTglAkhir;
    private com.toedter.calendar.JDateChooser txtTglAwal;
    private javax.swing.JLabel txtTotalBayar;
    private javax.swing.JLabel txtTotalPemasukan;
    private javax.swing.JLabel txtTotalService;
    private javax.swing.JLabel txtTotalService1;
    private javax.swing.JLabel txtTotalSparepart;
    private javax.swing.JFrame ubahFaktur;
    // End of variables declaration//GEN-END:variables
}
