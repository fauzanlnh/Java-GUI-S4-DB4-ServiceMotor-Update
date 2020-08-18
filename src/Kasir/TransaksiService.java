/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kasir;

import Admin.MenuKasir;
import Class.DatabaseConnection;
import Class.Login;
import Class.LoginSession;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fauzanlh
 */
public class TransaksiService extends javax.swing.JFrame {

    /**
     * Creates new form TransaksiService
     */
    Connection koneksi;
    String KdJenisMotor, IdPegawai, KdTeknisi;

    public TransaksiService() {
        initComponents();
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "fauzan", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        CariNoFaktur.setSize(575, 440);
        CariNoFaktur.setLocationRelativeTo(null);
        getListTeknisi();
        setJDate();
        txtKasir.setText(LoginSession.getIdPegawai());

    }

    //FORM
    public void Clear() {
        btnCariNopol.setEnabled(true);
        cmbSparepart.removeAllItems();
        cmbSparepart.addItem("-");
        cmbService.removeAllItems();
        cmbService.addItem("-");
        cmbTeknisi.removeAllItems();
        cmbTeknisi.addItem("-");
        txtNoPol.setText("");
        txtNoFaktur.setText("");
        txtQtyJasa.setText("");
        txtQtySpare.setText("");
        txtTotalBayar.setText("0");
        txtTotalService.setText("0");
        txtTotalSparepart.setText("0");
        DefaultTableModel tableModel = (DefaultTableModel) tblService.getModel();
        tableModel.setRowCount(0);
        DefaultTableModel tableModel2 = (DefaultTableModel) tblSparepart.getModel();
        tableModel2.setRowCount(0);
        getListTeknisi();
    }

    public void CariNoFaktur() {
        String kolom[] = {"NO", "No Faktur", "Id Customer", "No Polisi", "Status"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        int n = 0;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM T_Faktur WHERE STATUS = 'PROSES' "
                    + "AND No_Polisi LIKE '%" + txtCariNoPolisi.getText() + "%' "
                    + "ORDER BY Id_Faktur ASC";

            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String No_Faktur = rs.getString("Id_Faktur");
                String Id_Customer = rs.getString("Id_Customer");
                String No_Polisi = rs.getString("No_Polisi");
                String Status = rs.getString("Status");
                dtm.addRow(new String[]{no + "", No_Faktur, Id_Customer, No_Polisi, Status});
                no++;
                n = n + 1;
            }
            if (n == 0) {
                txtDataTidakDitemukan.setForeground(new java.awt.Color(51, 51, 51));
                txtDataTidakDitemukan.show(true);
                txtCariNoPolisi.requestFocus();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblFaktur.setModel(dtm);
    }

    public void setJDate() {
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
            String[] data = new String[5];
            data[0] = KdBarang;
            data[1] = Nama_Jasa;
            data[2] = HargaPerBarang;
            data[3] = qty;
            data[4] = TotalPerDetail;
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
            String[] data = new String[5];
            data[0] = KdBarang;
            data[1] = NamaSparepart;
            data[2] = HargaPerBarang;
            data[3] = qty;
            data[4] = TotalPerDetail;
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
            int ok = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == 0) {
                tableModel.removeRow(row);
            }
        }
    }

    public void hapusTableSparepart() {
        DefaultTableModel tableModel = (DefaultTableModel) tblSparepart.getModel();
        int row = tblSparepart.getSelectedRow();
        if (row >= 0) {
            int ok = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == 0) {
                tableModel.removeRow(row);
            }
        }
    }

    public void KlikTabelFaktur() {
        String getNoFaktur = tblFaktur.getValueAt(tblFaktur.getSelectedRow(), 1).toString();
        String getNoPolisi = tblFaktur.getValueAt(tblFaktur.getSelectedRow(), 3).toString();
        txtNoFaktur.setText(getNoFaktur);
        txtNoPol.setText(getNoPolisi);

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
        String kolom[] = {"Nomor Item", "Nama Item", "Harga", "Qty", "Total"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        String KdFaktur = txtNoFaktur.getText();
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT  T_Det_Pendaftaran_Sparepart.Id_Sparepart, T_Jenis_Sparepart.Nama_Sparepart, T_Det_Pendaftaran_Sparepart.Harga "
                    + "FROM T_Det_Pendaftaran_Sparepart,T_Jenis_Sparepart "
                    + "WHERE T_Jenis_Sparepart.Id_Sparepart = T_Det_Pendaftaran_Sparepart.Id_Sparepart AND "
                    + "Id_Faktur = '" + KdFaktur + "' ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Sparepart = rs.getString("Id_Sparepart");
                String Nama_Sparepart = rs.getString("Nama_Sparepart");
                String Harga = rs.getString("Harga");
                dtm.addRow(new String[]{Id_Sparepart, Nama_Sparepart, Harga, "", ""});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
        }
        tblSparepart.setModel(dtm);
    }

    public void TampilDetService() {
        String kolom[] = {"Nomor Item", "Nama Item", "Harga", "Qty", "Total"};

        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        String KdFaktur = txtNoFaktur.getText();
        int Total = 0;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT T_Det_Pendaftaran_Jasa.Id_Jasa, T_Jenis_Jasa.Nama_Jasa, T_Det_Pendaftaran_Jasa.Harga "
                    + "FROM T_Det_Pendaftaran_Jasa,T_Jenis_Jasa "
                    + "WHERE T_Jenis_Jasa.Id_Jasa = T_Det_Pendaftaran_Jasa.Id_Jasa AND "
                    + "Id_Faktur = '" + KdFaktur + "' ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Jasa = rs.getString("Id_Jasa");
                String Nama_Jasa = rs.getString("Nama_Jasa");
                String Harga = rs.getString("Harga");
                dtm.addRow(new String[]{Id_Jasa, Nama_Jasa, Harga, "", ""});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
        }
        tblService.setModel(dtm);
    }

    public void TampiDataFaktur() {
        String kolom[] = {"NO", "No Faktur", "Id Customer", "No Polisi", "Status"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM T_Faktur WHERE STATUS = 'PROSES' "
                    + "ORDER BY Id_Faktur ASC";
            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String No_Faktur = rs.getString("Id_Faktur");
                String Id_Customer = rs.getString("Id_Customer");
                String No_Polisi = rs.getString("No_Polisi");
                String Status = rs.getString("Status");
                dtm.addRow(new String[]{no + "", No_Faktur, Id_Customer, No_Polisi, Status});
                no++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
        }
        tblFaktur.setModel(dtm);
    }

    public String getKdJenisMotor() {
        String NoPolisi = txtNoPol.getText();
        try {
            String SelectKD = "SELECT * FROM T_Motor, T_Tipe WHERE No_Polisi = '" + NoPolisi + "' AND T_Motor.Id_Tipe = T_Tipe.Id_Tipe";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                KdJenisMotor = rs.getString("Id_Jenis");

            }
        } catch (SQLException e) {

        }
        return KdJenisMotor;
    }

    public String getKdTeknisi() {
        String NamaTeknisi = cmbTeknisi.getSelectedItem().toString();
        try {
            String SelectKD = "SELECT * FROM T_Pegawai WHERE Nama_Pegawai = '" + NamaTeknisi + "' AND Bagian = 'TEKNISI'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                KdTeknisi = rs.getString("Id_Pegawai");
            }
        } catch (SQLException e) {

        }
        return KdTeknisi;
    }

    //HITUNG
    public void setTotalSparepart() {
        txtTotalSparepart.setText("0");
        int TotalBayar = 0;
        for (int i = 0; i < tblSparepart.getRowCount(); i++) {
            if (tblSparepart.getRowCount() > 0) {
                if (tblSparepart.getValueAt(i, 4).toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "QTY DAN TOTAL SPAREPART MASIH KOSONG");
                } else {
                    int TotalPerDetail = Integer.parseInt(tblSparepart.getValueAt(i, 4) + "");
                    TotalBayar = TotalBayar + TotalPerDetail;
                }

            } else if (tblSparepart.getValueAt(i, 3).equals("")) {
                JOptionPane.showMessageDialog(null, "ISI QTY SPAREPART TERLBIH DAHULU");
            }
        }
        txtTotalSparepart.setText("" + TotalBayar);
    }

    public void setTotalService() {
        txtTotalService.setText("0");
        int TotalBayar = 0;
        for (int i = 0; i < tblService.getRowCount(); i++) {
            if (tblService.getRowCount() > 0) {
                if (tblService.getValueAt(i, 4).toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "QTY DAN TOTAL SERVICE MASIH KOSONG");
                } else {
                    int TotalPerDetail = Integer.parseInt(tblService.getValueAt(i, 4) + "");
                    TotalBayar = TotalBayar + TotalPerDetail;
                }
            } else if (tblService.getValueAt(i, 3).equals("")) {
                JOptionPane.showMessageDialog(null, "ISI QTY SERVICE TERLBIH DAHULU");
            }
        }
        txtTotalService.setText("" + TotalBayar);
    }

    public void setTotalBayar() {
        int TotalService = Integer.parseInt(txtTotalService.getText());
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
        String TotJasa = txtTotalService.getText();
        String TotBayar = txtTotalBayar.getText();
        int BerhasilDetSparepart = 0, BerhasilDetService = 0, BerhasilUpdateStok = 0;
        String tanggalMySQL = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tanggalMySQL);
        String Tanggal = String.valueOf(fm.format(txtTanggal.getDate()));

        //VALIDASI FORM
        if (KdFaktur.equals("") || txtNoPol.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "NO POLISI TIDAK BOLEH KOSONG");
            btnCariNopol.requestFocus();
        } else if (cmbTeknisi.getSelectedItem().equals("-")) {
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
                        InsertDetSparepart = ("INSERT INTO T_Det_Faktur_Sparepart (Id_Faktur, Id_Sparepart, Harga, Qty, Total_Per_Detail) VALUES("
                                + "'" + KdFaktur + "',"
                                + "'" + tSparepart.getValueAt(i, 0) + "',"
                                + "'" + tSparepart.getValueAt(i, 2) + "',"
                                + "'" + tSparepart.getValueAt(i, 3) + "',"
                                + "'" + tSparepart.getValueAt(i, 4) + "')");
                        BerhasilDetSparepart = stmt.executeUpdate(InsertDetSparepart);
                        UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = Stok - '" + tSparepart.getValueAt(i, 3) + "' WHERE Id_Sparepart = '" + tSparepart.getValueAt(i, 0) + "'";
                        BerhasilUpdateStok = stmt.executeUpdate(UpdateStok);
                        System.out.println(InsertDetSparepart);
                    }
                } else if (tSparepart.getRowCount() < 1) {
                    BerhasilDetSparepart = 1;
                    BerhasilUpdateStok = 1;
                }

                //INSERT TABEL DETAIL FAKTUR SERVICE
                if (tService.getRowCount() > 0) {
                    for (int i = 0; i < tService.getRowCount(); i++) {
                        InsertDetService = ("INSERT INTO T_Det_Faktur_Jasa (Id_Faktur, Id_Jasa, Harga, Qty, Total_Per_Detail) VALUES("
                                + "'" + KdFaktur + "',"
                                + "'" + tService.getValueAt(i, 0) + "',"
                                + "'" + tService.getValueAt(i, 2) + "',"
                                + "'" + tService.getValueAt(i, 3) + "',"
                                + "'" + tService.getValueAt(i, 4) + "')");
                        BerhasilDetService = stmt.executeUpdate(InsertDetService);
                        System.out.println(InsertDetService);
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
                    Clear();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CariNoFaktur = new javax.swing.JFrame();
        mainPanel1 = new javax.swing.JPanel();
        PanelDirectory1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblNoPol3 = new javax.swing.JLabel();
        btnPilihFaktur = new javax.swing.JButton();
        txtCariNoPolisi = new javax.swing.JTextField();
        tblDetSparepart1 = new javax.swing.JScrollPane();
        tblFaktur = new javax.swing.JTable();
        btnCancelCari = new javax.swing.JButton();
        txtDataTidakDitemukan = new javax.swing.JLabel();
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
        btnCariNopol = new javax.swing.JButton();
        txtNoPol = new javax.swing.JTextField();
        txtNoFaktur = new javax.swing.JTextField();
        lblDetServices1 = new javax.swing.JLabel();
        tblDetServices1 = new javax.swing.JScrollPane();
        tblSparepart = new javax.swing.JTable();
        tblDetSparepart2 = new javax.swing.JScrollPane();
        tblService = new javax.swing.JTable();
        lblDetSparepart2 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        lblService15 = new javax.swing.JLabel();
        lblService20 = new javax.swing.JLabel();
        txtTotalService = new javax.swing.JLabel();
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

        CariNoFaktur.setUndecorated(true);

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));

        PanelDirectory1.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory1.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Kasir/Transaksi/Cari No Faktur");

        javax.swing.GroupLayout PanelDirectory1Layout = new javax.swing.GroupLayout(PanelDirectory1);
        PanelDirectory1.setLayout(PanelDirectory1Layout);
        PanelDirectory1Layout.setHorizontalGroup(
            PanelDirectory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel10)
                .addContainerGap(207, Short.MAX_VALUE))
        );
        PanelDirectory1Layout.setVerticalGroup(
            PanelDirectory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectory1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(39, 39, 39))
        );

        lblNoPol3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol3.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol3.setText("No Polisi");

        btnPilihFaktur.setBackground(new java.awt.Color(240, 240, 240));
        btnPilihFaktur.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnPilihFaktur.setForeground(new java.awt.Color(51, 51, 51));
        btnPilihFaktur.setText("Submit");
        btnPilihFaktur.setBorder(null);
        btnPilihFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihFakturActionPerformed(evt);
            }
        });

        txtCariNoPolisi.setBackground(new java.awt.Color(255, 255, 255));
        txtCariNoPolisi.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtCariNoPolisi.setForeground(new java.awt.Color(51, 51, 51));
        txtCariNoPolisi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        txtCariNoPolisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariNoPolisiKeyReleased(evt);
            }
        });

        tblFaktur.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblFaktur.setForeground(new java.awt.Color(0, 0, 0));
        tblFaktur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "No Faktur", "Id Customer", "No Polisi", "Status"
            }
        ));
        tblDetSparepart1.setViewportView(tblFaktur);

        btnCancelCari.setBackground(new java.awt.Color(240, 240, 240));
        btnCancelCari.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnCancelCari.setForeground(new java.awt.Color(51, 51, 51));
        btnCancelCari.setText("Cancel");
        btnCancelCari.setBorder(null);
        btnCancelCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelCariActionPerformed(evt);
            }
        });

        txtDataTidakDitemukan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
        txtDataTidakDitemukan.setText("Data Yang Dicari Tidak Ditemukan");

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelDirectory1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainPanel1Layout.createSequentialGroup()
                                .addComponent(btnCancelCari, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPilihFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNoPol3)
                                .addComponent(txtCariNoPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tblDetSparepart1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDataTidakDitemukan)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelDirectory1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNoPol3)
                .addGap(18, 18, 18)
                .addComponent(txtCariNoPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tblDetSparepart1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(txtDataTidakDitemukan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPilihFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelCari, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout CariNoFakturLayout = new javax.swing.GroupLayout(CariNoFaktur.getContentPane());
        CariNoFaktur.getContentPane().setLayout(CariNoFakturLayout);
        CariNoFakturLayout.setHorizontalGroup(
            CariNoFakturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CariNoFakturLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        CariNoFakturLayout.setVerticalGroup(
            CariNoFakturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transaksi Service");

        mainPanel3.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel3.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory3.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory3.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Kasir/Transaksi Service");

        javax.swing.GroupLayout PanelDirectory3Layout = new javax.swing.GroupLayout(PanelDirectory3);
        PanelDirectory3.setLayout(PanelDirectory3Layout);
        PanelDirectory3Layout.setHorizontalGroup(
            PanelDirectory3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addContainerGap(464, Short.MAX_VALUE))
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

        btnCariNopol.setBackground(new java.awt.Color(240, 240, 240));
        btnCariNopol.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCariNopol.setForeground(new java.awt.Color(51, 51, 51));
        btnCariNopol.setText("Cari");
        btnCariNopol.setBorder(null);
        btnCariNopol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariNopolActionPerformed(evt);
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
                "No", "Kode Sparepart", "Nama Sparepart", "Harga"
            }
        ));
        tblSparepart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblSparepartKeyReleased(evt);
            }
        });
        tblDetServices1.setViewportView(tblSparepart);

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

        btnClear.setBackground(new java.awt.Color(240, 240, 240));
        btnClear.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(51, 51, 51));
        btnClear.setText("Clear");
        btnClear.setBorder(null);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        lblService15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService15.setForeground(new java.awt.Color(51, 51, 51));
        lblService15.setText("Total Service");

        lblService20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService20.setForeground(new java.awt.Color(51, 51, 51));
        lblService20.setText("Rp.");

        txtTotalService.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalService.setForeground(new java.awt.Color(51, 51, 51));
        txtTotalService.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalService.setText("0");

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

        javax.swing.GroupLayout mainPanel3Layout = new javax.swing.GroupLayout(mainPanel3);
        mainPanel3.setLayout(mainPanel3Layout);
        mainPanel3Layout.setHorizontalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addComponent(PanelDirectory3, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tblDetSparepart2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(mainPanel3Layout.createSequentialGroup()
                                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
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
                                                .addComponent(txtTotalService, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                                .addGap(6, 6, 6)
                                .addComponent(btnCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNoPol8)
                                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblService3)
                                    .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblService1)
                            .addComponent(txtQtyJasa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblSparepart1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbSparepart, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblService2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambahSparepart, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(txtQtySpare))
                        .addGap(58, 58, 58))))
        );
        mainPanel3Layout.setVerticalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(PanelDirectory3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSparepart1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNoPol9)
                                        .addComponent(lblNoPol8)
                                        .addComponent(lblService3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQtyJasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addComponent(lblDetServices1))
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblService2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQtySpare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTambahSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                    .addComponent(txtTotalService))
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
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnHapusServices, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(mainPanel3Layout.createSequentialGroup()
                            .addComponent(lblService1)
                            .addGap(69, 69, 69))
                        .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariNoPolisiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariNoPolisiKeyReleased
        if (!txtCariNoPolisi.getText().equals("")) {
            txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
            CariNoFaktur();
        } else if (txtCariNoPolisi.getText().equals("")) {
            txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
            TampiDataFaktur();
        }
    }//GEN-LAST:event_txtCariNoPolisiKeyReleased

    private void btnPilihFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihFakturActionPerformed
        KlikTabelFaktur();
        getListService();
        getListSparepart();
        TampilDetSparepart();
        TampilDetService();
        CariNoFaktur.dispose();
    }//GEN-LAST:event_btnPilihFakturActionPerformed

    private void btnCancelCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelCariActionPerformed
        CariNoFaktur.dispose();
        txtCariNoPolisi.setText("");
    }//GEN-LAST:event_btnCancelCariActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Data Yang Dimasukkan Benar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            InsertDataDetFaktur();
        }

    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnHapusServicesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusServicesMouseClicked
        hapusTableService();
    }//GEN-LAST:event_btnHapusServicesMouseClicked

    private void btnHapusSparepartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusSparepartMouseClicked
        hapusTableSparepart();
    }//GEN-LAST:event_btnHapusSparepartMouseClicked

    private void btnTambahSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSparepartActionPerformed
        setTableSparepart();
        setTotalSparepart();
        setTotalService();
        setTotalBayar();
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnTambahSparepartActionPerformed

    private void btnTambahServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahServiceActionPerformed
        setTableService();
        setTotalSparepart();
        setTotalService();
        setTotalBayar();
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnTambahServiceActionPerformed

    private void btnCariNopolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariNopolActionPerformed
        CariNoFaktur.show();
        TampiDataFaktur();
    }//GEN-LAST:event_btnCariNopolActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        Clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblSparepartKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblSparepartKeyReleased
        int Total = 0;
        int Qty = 0;
        String getHarga = tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 2).toString();
        String getQty = tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 3).toString();
        try {
            Qty = Integer.parseInt(getQty);
        } catch (NumberFormatException e) {

        }

        int Harga = Integer.parseInt(getHarga);
        Total = Qty * Harga;
        tblSparepart.setValueAt(Total, tblSparepart.getSelectedRow(), 4);
        setTotalSparepart();
        setTotalService();
        setTotalBayar();

    }//GEN-LAST:event_tblSparepartKeyReleased

    private void tblServiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblServiceKeyReleased
        int Total = 0;
        int Qty = 0;
        String getHarga = tblService.getValueAt(tblService.getSelectedRow(), 2).toString();
        String getQty = tblService.getValueAt(tblService.getSelectedRow(), 3).toString();
        try {
            Qty = Integer.parseInt(getQty);
        } catch (NumberFormatException e) {

        }
        int Harga = Integer.parseInt(getHarga);
        Total = Qty * Harga;
        tblService.setValueAt(Total, tblService.getSelectedRow(), 4);
        setTotalSparepart();
        setTotalService();
        setTotalBayar();
    }//GEN-LAST:event_tblServiceKeyReleased

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        setTotalSparepart();
        setTotalService();
        setTotalBayar();
    }//GEN-LAST:event_btnHitungActionPerformed

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
            java.util.logging.Logger.getLogger(TransaksiService.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransaksiService.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransaksiService.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransaksiService.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransaksiService().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame CariNoFaktur;
    private javax.swing.JPanel PanelDirectory1;
    private javax.swing.JPanel PanelDirectory3;
    private javax.swing.JButton btnCancelCari;
    private javax.swing.JButton btnCariNopol;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHapusServices;
    private javax.swing.JButton btnHapusSparepart;
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnPilihFaktur;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnTambahService;
    private javax.swing.JButton btnTambahSparepart;
    private javax.swing.JComboBox<String> cmbService;
    private javax.swing.JComboBox<String> cmbSparepart;
    private javax.swing.JComboBox<String> cmbTeknisi;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblDetServices1;
    private javax.swing.JLabel lblDetSparepart2;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblNoPol3;
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
    private javax.swing.JScrollPane tblDetSparepart1;
    private javax.swing.JScrollPane tblDetSparepart2;
    private javax.swing.JTable tblFaktur;
    private javax.swing.JTable tblService;
    private javax.swing.JTable tblSparepart;
    private javax.swing.JTextField txtCariNoPolisi;
    private javax.swing.JLabel txtDataTidakDitemukan;
    public javax.swing.JLabel txtKasir;
    private javax.swing.JTextField txtNoFaktur;
    private javax.swing.JTextField txtNoPol;
    private javax.swing.JTextField txtQtyJasa;
    private javax.swing.JTextField txtQtySpare;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JLabel txtTotalBayar;
    private javax.swing.JLabel txtTotalService;
    private javax.swing.JLabel txtTotalSparepart;
    // End of variables declaration//GEN-END:variables
}
