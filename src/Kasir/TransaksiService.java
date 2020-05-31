/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kasir;

import Class.DatabaseConnection;
import Class.Login;
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
    String KdJenisMotor, KdTeknisi;

    public TransaksiService() {
        initComponents();
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        CariNoFaktur.setSize(575, 440);
        CariNoFaktur.setLocationRelativeTo(null);
        getListTeknisi();
        setJDate();
    }

    //FORM
    public void Clear() {
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
            String query = "SELECT * FROM T_Jasa WHERE Nama_Jasa = '" + Nama_Jasa + "'";
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
        } else if (cmbService.getSelectedIndex() == 0 && !txtQtyJasa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ISI CMB SERVICE SERVICE DENGAN BENAR");
            cmbService.requestFocus();
        } else if (cmbService.getSelectedIndex() != 0 && txtQtyJasa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ISI QTY SERVICE SERVICE DENGAN BENAR");
            cmbService.requestFocus();
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
            String query = "SELECT * FROM t_sparepart WHERE nama_sparepart = '" + NamaSparepart + "'";
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
            String SelectKD = "SELECT * FROM T_Sparepart WHERE Id_Jenis = '" + KdJenisMotor + "'";
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
            String SelectKD = "SELECT * FROM T_Jasa WHERE Id_Jenis = '" + KdJenisMotor + "'";
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
            String SelectKD = "SELECT * FROM T_Teknisi";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                String Nama_Teknisi = rs.getString("Nama_Teknisi");
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
            query = "SELECT  T_Det_Pendaftaran_Sparepart.Id_Sparepart, T_Sparepart.Nama_Sparepart, T_Det_Pendaftaran_Sparepart.Harga "
                    + "FROM T_Det_Pendaftaran_Sparepart,T_Sparepart "
                    + "WHERE T_Sparepart.Id_Sparepart = T_Det_Pendaftaran_Sparepart.Id_Sparepart AND "
                    + "Id_Faktur = '" + KdFaktur + "' ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Sparepart = rs.getString("Id_Sparepart");
                String Nama_Sparepart = rs.getString("Nama_Sparepart");
                String Harga = rs.getString("Harga");
                dtm.addRow(new String[]{Id_Sparepart, Nama_Sparepart, Harga, "", ""});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
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
            query = "SELECT T_Det_Pendaftaran_Jasa.Id_Jasa, T_Jasa.Nama_Jasa, T_Det_Pendaftaran_Jasa.Harga "
                    + "FROM T_Det_Pendaftaran_Jasa,T_Jasa "
                    + "WHERE T_Jasa.Id_Jasa = T_Det_Pendaftaran_Jasa.Id_Jasa AND "
                    + "Id_Faktur = '" + KdFaktur + "' ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Jasa = rs.getString("Id_Jasa");
                String Nama_Jasa = rs.getString("Nama_Jasa");
                String Harga = rs.getString("Harga");
                dtm.addRow(new String[]{Id_Jasa, Nama_Jasa, Harga, "", ""});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
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
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
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
        String KdTeknisi = cmbTeknisi.getSelectedItem().toString();
        try {
            String SelectKD = "SELECT * FROM T_Teknisi WHERE Nama_Teknisi = '" + KdTeknisi + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                KdTeknisi = rs.getString("Id_Teknisi");

            }
        } catch (SQLException e) {

        }
        return KdTeknisi;
    }

    //HITUNG
    public void setTotalSparepart() {
        txtTotalSparepart.setText("0");
        for (int i = 0; i < tblSparepart.getRowCount(); i++) {
            int TotalBayar = 0;
            if (tblSparepart.getValueAt(i, 3).equals("")) {
                JOptionPane.showMessageDialog(null, "ISI QTY SPAREPART TERLBIH DAHULU");
            } else if (tblSparepart.getRowCount() > 0) {
                int TotalPerDetail = Integer.parseInt(tblSparepart.getValueAt(i, 4) + "");
                TotalBayar = TotalBayar + TotalPerDetail;
                txtTotalSparepart.setText("" + TotalBayar);
            }
        }
    }

    public void setTotalService() {
        txtTotalService.setText("0");
        for (int i = 0; i < tblService.getRowCount(); i++) {
            int TotalBayar = 0;
            if (tblService.getValueAt(i, 3).equals("")) {
                JOptionPane.showMessageDialog(null, "ISI QTY SERVICE TERLBIH DAHULU");
            } else if (tblService.getRowCount() > 0) {
                int TotalPerDetail = Integer.parseInt(tblService.getValueAt(i, 4) + "");
                TotalBayar = TotalBayar + TotalPerDetail;
                txtTotalService.setText("" + TotalBayar);
            }
        }
    }

    public void setTotalBayar() {
        int TotalService = Integer.parseInt(txtTotalService.getText());
        int TotalSparepart = Integer.parseInt(txtTotalSparepart.getText());
        int TotalBayar = TotalService + TotalSparepart;
        txtTotalBayar.setText("" + TotalBayar);
    }

    //INSERT * UPDATE
    public void InsertDataDetFaktur() {
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
        if (cmbTeknisi.getSelectedItem().equals("-")) {
            JOptionPane.showMessageDialog(null, "HARAP ISI NAMA TEKNISI");
            btnHitung.requestFocus();
        } else if (tblSparepart.getRowCount() < 1 && tblService.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "HARAP ISI PEMBELIAN SPAREPART ATAU PEMAKAIAN JASA SERVICE");
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
                        String InsertDetSparepart = ("INSERT INTO T_Det_Faktur_Sparepart (Id_Faktur, Id_Sparepart, Harga, Qty, Total_Per_Detail) VALUES("
                                + "'" + KdFaktur + "',"
                                + "'" + tSparepart.getValueAt(i, 0) + "',"
                                + "'" + tSparepart.getValueAt(i, 2) + "',"
                                + "'" + tSparepart.getValueAt(i, 3) + "',"
                                + "'" + tSparepart.getValueAt(i, 4) + "')");
                        BerhasilDetSparepart = stmt.executeUpdate(InsertDetSparepart);
                        String UpdateStok = "UPDATE T_Sparepart SET Stok = Stok - '" + tSparepart.getValueAt(i, 3) + "' WHERE Id_Sparepart = '" + tSparepart.getValueAt(i, 0) + "'";
                        BerhasilUpdateStok = stmt.executeUpdate(UpdateStok);
                        System.out.println(InsertDetSparepart);
                    }
                }
                //INSERT TABEL DETAIL FAKTUR SERVICE
                if (tService.getRowCount() > 0) {
                    for (int i = 0; i < tService.getRowCount(); i++) {
                        String InsertDetService = ("INSERT INTO T_Det_Faktur_Jasa (Id_Faktur, Id_Jasa, Harga, Qty, Total_Per_Detail) VALUES("
                                + "'" + KdFaktur + "',"
                                + "'" + tService.getValueAt(i, 0) + "',"
                                + "'" + tService.getValueAt(i, 2) + "',"
                                + "'" + tService.getValueAt(i, 3) + "',"
                                + "'" + tService.getValueAt(i, 4) + "')");
                        BerhasilDetService = stmt.executeUpdate(InsertDetService);
                        System.out.println(InsertDetService);
                    }
                }
                //UPDATE TABEL FAKTUR
                String UpdateFaktur = "UPDATE T_FAKTUR SET Id_Teknisi = '" + getKdTeknisi() + "', Id_Kasir = '" + IdKasir + "', Tanggal = '" + Tanggal + "',Status = 'BERES', "
                        + "Total_Sparepart = '" + TotSparepart + "', Total_Jasa = '" + TotJasa + "', Total_Bayar = '" + TotBayar + "' WHERE Id_Faktur = '" + KdFaktur + "'";
                int BerhasilUpdFaktur = stmt.executeUpdate(UpdateFaktur);

                //VALIDASI BERHASIL INSERT DAN UPDATE
                if (BerhasilDetService == 0 && tService.getRowCount() > 0) {
                    JOptionPane.showMessageDialog(null, "QTY Det Service Tidak Boleh Kosong");
                } else if (BerhasilDetSparepart == 0 && tSparepart.getRowCount() > 0) {
                    JOptionPane.showMessageDialog(null, "QTY Det Sparepart Tidak Boleh Kosong");
                } else if (BerhasilDetService == 1 && BerhasilDetSparepart == 1 && BerhasilUpdFaktur == 1) {
                    Clear();
                    JOptionPane.showMessageDialog(null, "Data Faktur Berhasil Dimasukkan");
                } else if (BerhasilDetService == 1 && BerhasilDetSparepart == 1) {
                    Clear();
                    JOptionPane.showMessageDialog(null, "Data Faktur Berhasil Dimasukkan");
                } else if (BerhasilDetService == 1 && BerhasilUpdFaktur == 1) {
                    Clear();
                    JOptionPane.showMessageDialog(null, "Data Faktur Berhasil Dimasukkan");
                } else if (BerhasilDetService == 1 && BerhasilUpdFaktur == 1 && BerhasilDetSparepart == 1 && BerhasilUpdateStok == 1) {
                    Clear();
                    JOptionPane.showMessageDialog(null, "Data Faktur Berhasil Dimasukkan");
                } else if (BerhasilUpdFaktur == 1 && BerhasilDetSparepart == 1 && BerhasilUpdateStok == 1) {
                    Clear();
                    JOptionPane.showMessageDialog(null, "Data Faktur Berhasil Dimasukkan");
                } else if (BerhasilDetService == 0 && BerhasilUpdFaktur == 0 && BerhasilDetSparepart == 0 && BerhasilUpdateStok == 0) {
                    JOptionPane.showMessageDialog(null, "Data Faktur Gagal Dimasukkan");
                } else {
                    Clear();
                    JOptionPane.showMessageDialog(null, "Data Faktur Berhasil Dimasukkan");
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
        sidePanel = new javax.swing.JPanel();
        pnlPendataran = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlKeluar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnlTransaksi = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnlHome = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlLaporan = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        PanelDirectory = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblNoPol = new javax.swing.JLabel();
        lblService = new javax.swing.JLabel();
        cmbService = new javax.swing.JComboBox<>();
        lblSparepart = new javax.swing.JLabel();
        cmbSparepart = new javax.swing.JComboBox<>();
        lblNoPol1 = new javax.swing.JLabel();
        closePanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        minimizePanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnHapusService = new javax.swing.JButton();
        btnHapusSparepart = new javax.swing.JButton();
        btnTambahSparepart = new javax.swing.JButton();
        btnTambahService = new javax.swing.JButton();
        btnCariNopol = new javax.swing.JButton();
        txtNoFaktur = new javax.swing.JTextField();
        txtNoPol = new javax.swing.JTextField();
        lblDetServices = new javax.swing.JLabel();
        tblDetServices = new javax.swing.JScrollPane();
        tblSparepart = new javax.swing.JTable();
        tblDetSparepart = new javax.swing.JScrollPane();
        tblService = new javax.swing.JTable();
        lblNoPol2 = new javax.swing.JLabel();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        lblSparepart1 = new javax.swing.JLabel();
        cmbTeknisi = new javax.swing.JComboBox<>();
        lblDetSparepart1 = new javax.swing.JLabel();
        lblService1 = new javax.swing.JLabel();
        txtQtyJasa = new javax.swing.JTextField();
        txtQtySpare = new javax.swing.JTextField();
        lblService2 = new javax.swing.JLabel();
        lblService15 = new javax.swing.JLabel();
        lblService16 = new javax.swing.JLabel();
        lblService17 = new javax.swing.JLabel();
        lblService18 = new javax.swing.JLabel();
        lblService19 = new javax.swing.JLabel();
        lblService20 = new javax.swing.JLabel();
        txtTotalService = new javax.swing.JLabel();
        txtTotalSparepart = new javax.swing.JLabel();
        lblSparepart2 = new javax.swing.JLabel();
        txtTotalBayar = new javax.swing.JLabel();
        txtKasir = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        sidePanel.setBackground(new java.awt.Color(0, 102, 204));

        pnlPendataran.setBackground(new java.awt.Color(30, 130, 234));
        pnlPendataran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPendataranMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Register.png"))); // NOI18N
        jLabel1.setText("Pendaftaran");
        jLabel1.setIconTextGap(30);

        javax.swing.GroupLayout pnlPendataranLayout = new javax.swing.GroupLayout(pnlPendataran);
        pnlPendataran.setLayout(pnlPendataranLayout);
        pnlPendataranLayout.setHorizontalGroup(
            pnlPendataranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPendataranLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        pnlPendataranLayout.setVerticalGroup(
            pnlPendataranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPendataranLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pnlKeluar.setBackground(new java.awt.Color(30, 130, 234));
        pnlKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlKeluarMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Logout White.png"))); // NOI18N
        jLabel2.setText("Keluar");
        jLabel2.setIconTextGap(30);

        javax.swing.GroupLayout pnlKeluarLayout = new javax.swing.GroupLayout(pnlKeluar);
        pnlKeluar.setLayout(pnlKeluarLayout);
        pnlKeluarLayout.setHorizontalGroup(
            pnlKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKeluarLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlKeluarLayout.setVerticalGroup(
            pnlKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKeluarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pnlTransaksi.setBackground(new java.awt.Color(30, 130, 234));
        pnlTransaksi.setPreferredSize(new java.awt.Dimension(303, 58));
        pnlTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTransaksiMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Cash Register.png"))); // NOI18N
        jLabel3.setText("Transaksi");
        jLabel3.setIconTextGap(30);

        javax.swing.GroupLayout pnlTransaksiLayout = new javax.swing.GroupLayout(pnlTransaksi);
        pnlTransaksi.setLayout(pnlTransaksiLayout);
        pnlTransaksiLayout.setHorizontalGroup(
            pnlTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTransaksiLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTransaksiLayout.setVerticalGroup(
            pnlTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTransaksiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pnlHome.setBackground(new java.awt.Color(30, 130, 234));
        pnlHome.setPreferredSize(new java.awt.Dimension(303, 58));
        pnlHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlHomeMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Home.png"))); // NOI18N
        jLabel5.setText("Home");
        jLabel5.setIconTextGap(30);

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pnlLaporan.setBackground(new java.awt.Color(30, 130, 234));
        pnlLaporan.setRequestFocusEnabled(false);
        pnlLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlLaporanMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Report White.png"))); // NOI18N
        jLabel4.setText("Laporan");
        jLabel4.setIconTextGap(30);

        javax.swing.GroupLayout pnlLaporanLayout = new javax.swing.GroupLayout(pnlLaporan);
        pnlLaporan.setLayout(pnlLaporanLayout);
        pnlLaporanLayout.setHorizontalGroup(
            pnlLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLaporanLayout.setVerticalGroup(
            pnlLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/SS_Support.png"))); // NOI18N
        jLabel9.setText("    Service Motor");

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPendataran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .addComponent(pnlHome, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .addComponent(pnlLaporan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel9)
                .addGap(40, 40, 40)
                .addComponent(pnlHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlPendataran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        PanelDirectory.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Kasir/Transaksi Service");

        javax.swing.GroupLayout PanelDirectoryLayout = new javax.swing.GroupLayout(PanelDirectory);
        PanelDirectory.setLayout(PanelDirectoryLayout);
        PanelDirectoryLayout.setHorizontalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectoryLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDirectoryLayout.setVerticalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectoryLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(39, 39, 39))
        );

        lblNoPol.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol.setText("No Polisi");

        lblService.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService.setForeground(new java.awt.Color(51, 51, 51));
        lblService.setText("Service");

        cmbService.setBackground(new java.awt.Color(255, 255, 255));
        cmbService.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbService.setForeground(new java.awt.Color(51, 51, 51));
        cmbService.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));

        lblSparepart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSparepart.setForeground(new java.awt.Color(51, 51, 51));
        lblSparepart.setText("Sparepart");

        cmbSparepart.setBackground(new java.awt.Color(255, 255, 255));
        cmbSparepart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbSparepart.setForeground(new java.awt.Color(51, 51, 51));
        cmbSparepart.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));

        lblNoPol1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol1.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol1.setText("No Faktur");

        closePanel.setBackground(new java.awt.Color(0, 102, 204));
        closePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closePanelMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("X");

        javax.swing.GroupLayout closePanelLayout = new javax.swing.GroupLayout(closePanel);
        closePanel.setLayout(closePanelLayout);
        closePanelLayout.setHorizontalGroup(
            closePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, closePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );
        closePanelLayout.setVerticalGroup(
            closePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, closePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        minimizePanel.setBackground(new java.awt.Color(0, 102, 204));
        minimizePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizePanelMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("_");

        javax.swing.GroupLayout minimizePanelLayout = new javax.swing.GroupLayout(minimizePanel);
        minimizePanel.setLayout(minimizePanelLayout);
        minimizePanelLayout.setHorizontalGroup(
            minimizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, minimizePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );
        minimizePanelLayout.setVerticalGroup(
            minimizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, minimizePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        btnHapusService.setBackground(new java.awt.Color(240, 240, 240));
        btnHapusService.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHapusService.setForeground(new java.awt.Color(51, 51, 51));
        btnHapusService.setText("Hapus");
        btnHapusService.setBorder(null);
        btnHapusService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusServiceActionPerformed(evt);
            }
        });

        btnHapusSparepart.setBackground(new java.awt.Color(240, 240, 240));
        btnHapusSparepart.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHapusSparepart.setForeground(new java.awt.Color(51, 51, 51));
        btnHapusSparepart.setText("Hapus");
        btnHapusSparepart.setBorder(null);
        btnHapusSparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusSparepartActionPerformed(evt);
            }
        });

        btnTambahSparepart.setBackground(new java.awt.Color(240, 240, 240));
        btnTambahSparepart.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTambahSparepart.setForeground(new java.awt.Color(51, 51, 51));
        btnTambahSparepart.setText("Tambah");
        btnTambahSparepart.setBorder(null);
        btnTambahSparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSparepartActionPerformed(evt);
            }
        });

        btnTambahService.setBackground(new java.awt.Color(240, 240, 240));
        btnTambahService.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTambahService.setForeground(new java.awt.Color(51, 51, 51));
        btnTambahService.setText("Tambah");
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

        txtNoFaktur.setEditable(false);
        txtNoFaktur.setBackground(new java.awt.Color(255, 255, 255));
        txtNoFaktur.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoFaktur.setForeground(new java.awt.Color(51, 51, 51));
        txtNoFaktur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtNoPol.setEditable(false);
        txtNoPol.setBackground(new java.awt.Color(255, 255, 255));
        txtNoPol.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoPol.setForeground(new java.awt.Color(51, 51, 51));
        txtNoPol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblDetServices.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDetServices.setForeground(new java.awt.Color(51, 51, 51));
        lblDetServices.setText("List Sparepart");

        tblSparepart.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblSparepart.setForeground(new java.awt.Color(0, 0, 0));
        tblSparepart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomor Item", "Nama Item", "Harga", "Qty", "Total"
            }
        ));
        tblSparepart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblSparepartKeyReleased(evt);
            }
        });
        tblDetServices.setViewportView(tblSparepart);

        tblService.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblService.setForeground(new java.awt.Color(0, 0, 0));
        tblService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomor Item", "Nama Item", "Harga", "Qty", "Total"
            }
        ));
        tblService.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblServiceKeyReleased(evt);
            }
        });
        tblDetSparepart.setViewportView(tblService);

        lblNoPol2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol2.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol2.setText("Tanggal");

        txtTanggal.setBackground(new java.awt.Color(255, 255, 255));
        txtTanggal.setForeground(new java.awt.Color(51, 51, 51));

        lblSparepart1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSparepart1.setForeground(new java.awt.Color(51, 51, 51));
        lblSparepart1.setText("Teknisi");

        cmbTeknisi.setBackground(new java.awt.Color(255, 255, 255));
        cmbTeknisi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbTeknisi.setForeground(new java.awt.Color(51, 51, 51));

        lblDetSparepart1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDetSparepart1.setForeground(new java.awt.Color(51, 51, 51));
        lblDetSparepart1.setText("List Service");

        lblService1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService1.setForeground(new java.awt.Color(51, 51, 51));
        lblService1.setText("Qty");

        txtQtyJasa.setBackground(new java.awt.Color(255, 255, 255));
        txtQtyJasa.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtQtyJasa.setForeground(new java.awt.Color(51, 51, 51));
        txtQtyJasa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtQtySpare.setBackground(new java.awt.Color(255, 255, 255));
        txtQtySpare.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtQtySpare.setForeground(new java.awt.Color(51, 51, 51));
        txtQtySpare.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblService2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService2.setForeground(new java.awt.Color(51, 51, 51));
        lblService2.setText("Qty");

        lblService15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService15.setForeground(new java.awt.Color(51, 51, 51));
        lblService15.setText("Total Service");

        lblService16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService16.setForeground(new java.awt.Color(51, 51, 51));
        lblService16.setText("Total Spare Part");

        lblService17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService17.setForeground(new java.awt.Color(51, 51, 51));
        lblService17.setText("Total Bayar");

        lblService18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService18.setForeground(new java.awt.Color(51, 51, 51));
        lblService18.setText("Rp.");

        lblService19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService19.setForeground(new java.awt.Color(51, 51, 51));
        lblService19.setText("Rp.");

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

        lblSparepart2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSparepart2.setForeground(new java.awt.Color(51, 51, 51));
        lblSparepart2.setText("Kasir");

        txtTotalBayar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalBayar.setForeground(new java.awt.Color(51, 51, 51));
        txtTotalBayar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalBayar.setText("0");

        txtKasir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtKasir.setForeground(new java.awt.Color(51, 51, 51));
        txtKasir.setText("1");

        btnClear.setBackground(new java.awt.Color(240, 240, 240));
        btnClear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(51, 51, 51));
        btnClear.setText("Clear");
        btnClear.setBorder(null);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnSubmit.setBackground(new java.awt.Color(240, 240, 240));
        btnSubmit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(51, 51, 51));
        btnSubmit.setText("Submit");
        btnSubmit.setBorder(null);
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(lblNoPol)
                                        .addGap(65, 65, 65)
                                        .addComponent(lblNoPol2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSparepart2)
                                    .addComponent(txtKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNoPol1)
                                    .addComponent(lblService))
                                .addGap(81, 81, 81)
                                .addComponent(lblService1)
                                .addGap(41, 41, 41)
                                .addComponent(lblSparepart))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lblService15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblService16, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(lblService17, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(lblService18, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTotalBayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(lblService19, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTotalSparepart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(lblService20, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTotalService, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtQtyJasa, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTambahSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtQtySpare, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblService2))
                                        .addGap(52, 52, 52)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSparepart1)
                                            .addComponent(cmbTeknisi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnHapusSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblDetSparepart1)
                                    .addComponent(lblDetServices)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(btnHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(tblDetSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnHapusService, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(40, Short.MAX_VALUE))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNoPol2)
                                .addComponent(lblNoPol)
                                .addComponent(lblSparepart2))
                            .addComponent(lblNoPol1))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtKasir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblService)
                    .addComponent(lblService1)
                    .addComponent(lblSparepart)
                    .addComponent(lblService2)
                    .addComponent(lblSparepart1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQtyJasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQtySpare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTeknisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(lblDetServices)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDetSparepart1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tblDetSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusService, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblService15)
                            .addComponent(lblService20)
                            .addComponent(txtTotalService))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblService16)
                            .addComponent(lblService19)
                            .addComponent(txtTotalSparepart))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblService18)
                            .addComponent(lblService17)
                            .addComponent(txtTotalBayar)))
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlPendataranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPendataranMouseClicked
        dispose();
        PendaftaranService ps = new PendaftaranService();
        ps.show();
    }//GEN-LAST:event_pnlPendataranMouseClicked

    private void pnlKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKeluarMouseClicked
        dispose();
        Login ps = new Login();
        ps.show();
    }//GEN-LAST:event_pnlKeluarMouseClicked

    private void pnlTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTransaksiMouseClicked
        dispose();
        TransaksiService ps = new TransaksiService();
        ps.show();
    }//GEN-LAST:event_pnlTransaksiMouseClicked

    private void pnlHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHomeMouseClicked
        dispose();
        HomeKasir hk = new HomeKasir();
        hk.show();
    }//GEN-LAST:event_pnlHomeMouseClicked

    private void pnlLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLaporanMouseClicked
        dispose();
        Laporan ps = new Laporan();
        ps.show();
    }//GEN-LAST:event_pnlLaporanMouseClicked

    private void closePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closePanelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closePanelMouseClicked

    private void minimizePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizePanelMouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minimizePanelMouseClicked

    private void btnCariNopolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariNopolActionPerformed
        CariNoFaktur.show();
        TampiDataFaktur();
    }//GEN-LAST:event_btnCariNopolActionPerformed

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
    }//GEN-LAST:event_btnCancelCariActionPerformed

    private void tblServiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblServiceKeyReleased
        // TODO add your handling code here:
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
    }//GEN-LAST:event_tblServiceKeyReleased

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

    }//GEN-LAST:event_tblSparepartKeyReleased

    private void btnTambahServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahServiceActionPerformed
        setTableService();
    }//GEN-LAST:event_btnTambahServiceActionPerformed

    private void btnTambahSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSparepartActionPerformed
        setTableSparepart();
    }//GEN-LAST:event_btnTambahSparepartActionPerformed

    private void btnHapusServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusServiceActionPerformed
        hapusTableService();
        setTotalService();
        setTotalBayar();
    }//GEN-LAST:event_btnHapusServiceActionPerformed

    private void btnHapusSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusSparepartActionPerformed
        hapusTableSparepart();
        setTotalSparepart();
        setTotalBayar();
    }//GEN-LAST:event_btnHapusSparepartActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        Clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Data Yang Dimasukkan Benar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            InsertDataDetFaktur();
        }

    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        // TODO add your handling code here:
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
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JPanel PanelDirectory1;
    private javax.swing.JButton btnCancelCari;
    private javax.swing.JButton btnCariNopol;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHapusService;
    private javax.swing.JButton btnHapusSparepart;
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnPilihFaktur;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnTambahService;
    private javax.swing.JButton btnTambahSparepart;
    private javax.swing.JPanel closePanel;
    private javax.swing.JComboBox<String> cmbService;
    private javax.swing.JComboBox<String> cmbSparepart;
    private javax.swing.JComboBox<String> cmbTeknisi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblDetServices;
    private javax.swing.JLabel lblDetSparepart1;
    private javax.swing.JLabel lblNoPol;
    private javax.swing.JLabel lblNoPol1;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblNoPol3;
    private javax.swing.JLabel lblService;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblService15;
    private javax.swing.JLabel lblService16;
    private javax.swing.JLabel lblService17;
    private javax.swing.JLabel lblService18;
    private javax.swing.JLabel lblService19;
    private javax.swing.JLabel lblService2;
    private javax.swing.JLabel lblService20;
    private javax.swing.JLabel lblSparepart;
    private javax.swing.JLabel lblSparepart1;
    private javax.swing.JLabel lblSparepart2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel minimizePanel;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlKeluar;
    private javax.swing.JPanel pnlLaporan;
    private javax.swing.JPanel pnlPendataran;
    private javax.swing.JPanel pnlTransaksi;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JScrollPane tblDetServices;
    private javax.swing.JScrollPane tblDetSparepart;
    private javax.swing.JScrollPane tblDetSparepart1;
    private javax.swing.JTable tblFaktur;
    private javax.swing.JTable tblService;
    private javax.swing.JTable tblSparepart;
    private javax.swing.JTextField txtCariNoPolisi;
    private javax.swing.JLabel txtDataTidakDitemukan;
    private javax.swing.JLabel txtKasir;
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
