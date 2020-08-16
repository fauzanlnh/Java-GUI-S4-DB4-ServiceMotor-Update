/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kasir;

import Class.DatabaseConnection;
import Class.Login;
import java.sql.*;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fauzanlh
 */
public class PendaftaranService extends javax.swing.JFrame {

    /**
     * Creates new form PendaftaranService
     */
    //Tipe Data
    Connection koneksi;
    String KdJenisMotor, KdTipeMotor, IdCustomer;

    public PendaftaranService() {
        initComponents();
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        CariNoPolisi.setSize(575, 440);
        CariNoPolisi.setLocationRelativeTo(null);
        TambahCustomer.setSize(625, 450);
        TambahCustomer.setLocationRelativeTo(null);
        SetKodeFaktur();
        GetListService();
    }

    //FORM
    public void Clear() {
        SetKodeFaktur();
        cmbSparepart.removeAllItems();
        cmbSparepart.addItem("-");
        cmbService.removeAllItems();
        cmbService.addItem("-");
        txtNoPol.setText("");
        DefaultTableModel tableModel = (DefaultTableModel) tblService.getModel();
        tableModel.setRowCount(0);
        DefaultTableModel tableModel2 = (DefaultTableModel) tblSparepart.getModel();
        tableModel2.setRowCount(0);
        btnCariNopol.setEnabled(true);
    }

    public void SetKodeFaktur() {
        int No_Faktur = 0;
        try {
            String SelectKD = "SELECT * FROM T_Faktur ORDER BY Id_Faktur DESC LIMIT 1";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                No_Faktur = rs.getInt("Id_Faktur");
            }
            No_Faktur = No_Faktur + 1;
            txtNoFaktur.setText("" + No_Faktur);
        } catch (SQLException e) {

        }
    }

    //FORM TABLE
    public void SetTableService() {
        DefaultTableModel tableModel = (DefaultTableModel) tblService.getModel();
        String KdJasa = null, Harga = null;
        String NamaJasa = cmbService.getSelectedItem().toString();
        try {
            String query = "SELECT * FROM T_Jenis_Jasa WHERE Nama_Jasa = '" + NamaJasa + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                KdJasa = rs.getString("Id_Jasa");
                Harga = rs.getString("Harga_Jasa");
            }
        } catch (SQLException e) {

        }
        if (!cmbService.getSelectedItem().equals("-") && !txtNoPol.getText().equals("")) {
            int no = tblService.getRowCount() + 1;
            String No = Integer.toString(no);
            String[] data = new String[4];
            data[0] = No;
            data[1] = KdJasa;
            data[2] = NamaJasa;
            data[3] = Harga;
            tableModel.addRow(data);
        } else if (cmbService.getSelectedIndex() == 0 && txtNoPol.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "NO POLISi BELUM DIISI");
            btnCariNopol.requestFocus();
        } else if (cmbService.getSelectedIndex() == 0 && !txtNoPol.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ISI CMB SERVICE DENGAN BENAR");
            cmbService.requestFocus();
        }
    }

    public void SetTableSparepart() {
        DefaultTableModel tableModel = (DefaultTableModel) tblSparepart.getModel();
        String KdBarang = null, Harga = null;
        String NamaSparepart = cmbSparepart.getSelectedItem().toString();
        try {
            String query = "SELECT * FROM T_Jenis_Sparepart WHERE nama_sparepart = '" + NamaSparepart + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                KdBarang = rs.getString("Id_Sparepart");
                Harga = rs.getString("Harga_Sparepart");
            }
        } catch (SQLException e) {

        }

        if (!cmbSparepart.getSelectedItem().equals("-") && !txtNoPol.getText().equals("")) {
            int no = tblSparepart.getRowCount() + 1;
            String No = Integer.toString(no);
            String[] data = new String[4];
            data[0] = No;
            data[1] = KdBarang;
            data[2] = NamaSparepart;
            data[3] = Harga;
            tableModel.addRow(data);
        } else if (cmbSparepart.getSelectedIndex() == 0 && txtNoPol.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "NO POLISi BELUM DIISI");
            btnCariNopol.requestFocus();
        } else if (cmbSparepart.getSelectedIndex() == 0 && !txtNoPol.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ISI CMB SPAREPART DENGAN BENAR");
            cmbService.requestFocus();
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

    public void klik() {
        String getNoPol = tblNoPolisi.getValueAt(tblNoPolisi.getSelectedRow(), 2).toString();

        txtNoPol.setText(getNoPol);
    }

    //GET ATAU SELECT DARI DB
    public void GetListService() {
        if (!txtNoPol.getText().equals("")) {
            GetKdJenisMotor();
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

    public void GetListSparepart() {
        if (!txtNoPol.getText().equals("")) {
            GetKdJenisMotor();
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

    public void GetTipeMotor() {
        try {
            String SelectKD = "SELECT * FROM T_Tipe";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            while (rs.next()) {
                String TipeMotor = rs.getString("Nama_Tipe");
                cmbTipeMotor.addItem(TipeMotor);
            }
        } catch (SQLException e) {

        }
    }

    public void TampilDataCustomer() {
        String kolom[] = {"NO", "Nama Customer", "No Polisi", "No Rangka", "No Mesin", "Tipe Motor"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT T_Customer.Nama_Customer, T_Motor.No_Polisi, T_Motor.No_Mesin, T_Motor.No_Rangka, T_Tipe.Nama_Tipe "
                    + "FROM T_Motor, T_Customer, T_Tipe "
                    + "WHERE T_Motor.Id_Customer = T_Customer.Id_Customer AND T_Tipe.Id_Tipe = T_Motor.Id_Tipe "
                    + "ORDER BY Nama_Customer ASC";
            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String Nama_Customer = rs.getString("Nama_Customer");
                String No_Polisi = rs.getString("No_Polisi");
                String No_Mesin = rs.getString("No_Mesin");
                String No_Rangka = rs.getString("No_Rangka");
                String Nama_Tipe = rs.getString("Nama_Tipe");
                dtm.addRow(new String[]{no + "", Nama_Customer, No_Polisi, No_Mesin, No_Rangka, Nama_Tipe});
                no++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
        }
        tblNoPolisi.setModel(dtm);
    }

    public void CariDataCustomer() {
        String kolom[] = {"NO", "Nama Customer", "No Polisi", "No Rangka", "No Mesin", "Tipe Motor"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        int n = 0;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT T_Customer.Nama_Customer, T_Motor.No_Polisi, T_Motor.No_Mesin, T_Motor.No_Rangka, T_Tipe.Nama_Tipe "
                    + "FROM T_Motor, T_Customer, T_Tipe "
                    + "WHERE T_Motor.Id_Customer = T_Customer.Id_Customer AND T_Tipe.Id_Tipe = T_Motor.Id_Tipe "
                    + "AND No_Polisi LIKE '%" + txtCariNopol.getText() + "%' "
                    + "ORDER BY Nama_Customer ASC;";

            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String Nama_Customer = rs.getString("Nama_Customer");
                String No_Polisi = rs.getString("No_Polisi");
                String No_Mesin = rs.getString("No_Mesin");
                String No_Rangka = rs.getString("No_Rangka");
                String Nama_Tipe = rs.getString("Nama_Tipe");
                dtm.addRow(new String[]{no + "", Nama_Customer, No_Polisi, No_Mesin, No_Rangka, Nama_Tipe});
                no++;
                n = n + 1;
            }
            if (n == 0) {
                txtDataTidakDitemukan.setForeground(new java.awt.Color(51, 51, 51));
                txtDataTidakDitemukan.show(true);
                btnNewCustomer.requestFocus();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASEF" + ex);
        }
        tblNoPolisi.setModel(dtm);
    }

    public String GetKdJenisMotor() {
        //MATIC,GIGI, SPORT
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

    public String GetKdTipeMotor() {
        //MIO, NMAX, DLL.
        String TipeMotor = cmbTipeMotor.getSelectedItem().toString();
        try {
            String SelectKD = "SELECT * FROM T_Tipe WHERE Nama_Tipe = '" + TipeMotor + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            if (rs.next()) {
                KdTipeMotor = rs.getString("Id_Tipe");
            }
        } catch (SQLException e) {

        }
        return KdTipeMotor;
    }

    public String GetIdCustomer() {
        String NoPolisi = txtNoPol.getText().toUpperCase();
        try {
            //GET VALUE ID CUSTOMER
            String SelectIDCust = "SELECT * FROM T_Motor WHERE No_Polisi = '" + NoPolisi + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectIDCust);
            while (rs.next()) {
                IdCustomer = rs.getString("Id_Customer");
            }
        } catch (SQLException e) {

        }
        return IdCustomer;

    }

    //INSERT * UPDATE
    //INSERT JFRAME PENDAFTARAN CUSTOMER
    public void InsertDataCustomer() {
        String IdCustomer = txtIdCustomer.getText().toUpperCase();
        String NamaCustomer = txtNamaCustomer.getText().toUpperCase();
        String Alamat = txtAlamat.getText().toUpperCase();
        String NoTelp = txtNoTelp.getText();
        String NoPolisi = txtNoPolisi.getText().toUpperCase();
        String NoRangka = txtNoRangka.getText().toUpperCase();
        String NoMesin = txtNoMesin.getText().toUpperCase();

        int BerhasilCustomer = 0, BerhasilMotor = 0;
        try {
            Statement stmt = koneksi.createStatement();
            //INSERT TABEL CUSTOMER
            if (!txtIdCustomer.getText().equals("") && !txtNamaCustomer.getText().equals("") && !txtAlamat.getText().equals("") && !txtNoTelp.getText().equals("") && !txtNoPolisi.getText().equals("") && !txtNoRangka.getText().equals("") && !txtNoMesin.getText().equals("") && !cmbTipeMotor.getSelectedItem().equals("-")) {
                //INSERT T_CUSTOMER
                String InsertCustomer = "INSERT INTO T_Customer VALUES ('" + IdCustomer + "', '" + NamaCustomer + "','" + Alamat + "','" + NoTelp + "')";
                System.out.println(InsertCustomer);
                BerhasilCustomer = stmt.executeUpdate(InsertCustomer);

                //INSERT T_MOTOR
                String InsertMotor = "INSERT INTO T_Motor VALUES ('" + NoPolisi + "', '" + NoRangka + "','" + NoMesin + "','" + IdCustomer + "','" + GetKdTipeMotor() + "')";
                System.out.println(InsertMotor);
                BerhasilMotor = stmt.executeUpdate(InsertMotor);

                //VALIDASI QUERY
                if (BerhasilCustomer == 1 && BerhasilMotor == 1) {
                    JOptionPane.showMessageDialog(null, "DATA CUSTOMER BERHASIL DIMASUKKAN");
                    TambahCustomer.dispose();
                    CariNoPolisi.show();
                } else if (BerhasilCustomer == 1) {
                    JOptionPane.showMessageDialog(null, "DATA FORM CUSTOMER GAGAL DIMASUKKAN");
                } else if (BerhasilMotor == 1) {
                    JOptionPane.showMessageDialog(null, "DATA FORM MOTOR CUSTOMER GAGAL DIMASUKKAN");
                } else if (BerhasilCustomer == 0 && BerhasilMotor == 0) {
                    JOptionPane.showMessageDialog(null, "DATA CUSTOMER GAGAL DIMASUKKAN");
                }
            } else {
                if (txtIdCustomer.getText().equals("") && txtNamaCustomer.getText().equals("") && txtAlamat.getText().equals("") && txtNoTelp.getText().equals("") && txtNoPolisi.getText().equals("") && txtNoRangka.getText().equals("") && txtNoMesin.getText().equals("") && cmbTipeMotor.getSelectedItem().equals("-")) {
                    JOptionPane.showMessageDialog(null, "FORM MASIH KOSONG");
                } else if (txtIdCustomer.getText().equals("") && txtNamaCustomer.getText().equals("") && txtAlamat.getText().equals("") && txtNoTelp.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "ISI FORM CUSTOMER DENGAN BENAR");
                    txtIdCustomer.requestFocus();
                } else if (txtIdCustomer.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "ID CUSTOMER TIDAK BOLEH KOSONG");
                    txtIdCustomer.requestFocus();
                } else if (txtNamaCustomer.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "NAMA CUSTOMER TIDAK BOLEH KOSONG");
                    txtNamaCustomer.requestFocus();
                } else if (txtAlamat.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "ALAMAT CUSTOMER TIDAK BOLEH KOSONG");
                    txtAlamat.requestFocus();
                } else if (txtNoTelp.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "NO TELP CUSTOMER TIDAK BOLEH KOSONG");
                    txtNoTelp.requestFocus();
                } // INSERT TABEL MOTOR
                else if (txtNoPolisi.getText().equals("") && txtNoRangka.getText().equals("") && txtNoMesin.getText().equals("") && cmbTipeMotor.getSelectedItem().equals("-")) {
                    JOptionPane.showMessageDialog(null, "ISI FORM MOTOR CUSTOMER DENGAN BENAR");
                    txtNoPolisi.requestFocus();
                } else if (txtNoPolisi.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "NO POLISI TIDAK BOLEH KOSONG");
                    txtNoPolisi.requestFocus();
                } else if (txtNoRangka.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "NO RANGKA TIDAK BOLEH KOSONG");
                    txtNoRangka.requestFocus();
                } else if (txtNoMesin.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "NO MESIN TIDAK BOLEH KOSNONG");
                    txtNoMesin.requestFocus();
                } else if (cmbTipeMotor.getSelectedItem().equals("-")) {
                    JOptionPane.showMessageDialog(null, "PILIHAM TIPE MOTOR SALAH");
                    cmbTipeMotor.requestFocus();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
        }
    }

    //INSERT JFRAME PENDAFTARAN SERVICE
    public void InsertDataPendSparepart() {
        DefaultTableModel tSparepart = (DefaultTableModel) tblSparepart.getModel();
        DefaultTableModel tService = (DefaultTableModel) tblService.getModel();
        String KdFaktur = txtNoFaktur.getText().toUpperCase();
        String NoPolisi = txtNoPol.getText().toUpperCase();
        String NoFaktur = txtNoFaktur.getText().toUpperCase();
        int BerhasiDetSparepart = 0, BerhasiDetService = 0, BerhasilFaktur = 0;
        try {
            Statement stmt = koneksi.createStatement();

            //QUERY
            if (!NoPolisi.equals("")) {
                //INSERT TABEL FAKTUR
                String InsertFaktur = "INSERT INTO T_Faktur (Id_Faktur, No_Polisi, Id_Customer, Status)VALUES ('" + NoFaktur + "', '" + NoPolisi + "','" + GetIdCustomer() + "', 'PROSES')";
                BerhasilFaktur = stmt.executeUpdate(InsertFaktur);
                System.out.println("INSERT TABEL FAKTUR");
                System.out.println(InsertFaktur);

                //INSERT TABEL DETAIL PENDAFTARAN SPAREPART
                if (tSparepart.getRowCount() > 0) {
                    System.out.println("INSERT TABEL DETAIL PENDAFTARAN SPAREPART");
                    for (int i = 0; i < tSparepart.getRowCount(); i++) {
                        String InsertDetSparepart = ("INSERT INTO T_Det_Pendaftaran_Sparepart (Id_Faktur, Id_Sparepart, Harga) VALUES("
                                + "'" + KdFaktur + "',"
                                + "'" + tSparepart.getValueAt(i, 1) + "',"
                                + "'" + tSparepart.getValueAt(i, 3) + "')");
                        BerhasiDetSparepart = stmt.executeUpdate(InsertDetSparepart);
                        System.out.println(InsertDetSparepart);
                    }
                } else {
                    BerhasiDetSparepart = 1;
                }

                //INSERT TABEL DETAIL PENDAFTARAN SERVICE
                if (tService.getRowCount() > 0) {
                    System.out.println("INSERT TABEL DETAIL PENDAFTARAN SERVICE");
                    for (int i = 0; i < tService.getRowCount(); i++) {
                        String InsertDetService = ("INSERT INTO T_Det_Pendaftaran_Jasa (Id_Faktur, Id_Jasa, Harga) VALUES("
                                + "'" + KdFaktur + "',"
                                + "'" + tService.getValueAt(i, 1) + "',"
                                + "'" + tService.getValueAt(i, 3) + "')");
                        BerhasiDetService = stmt.executeUpdate(InsertDetService);
                        System.out.println(InsertDetService);
                    }
                } else {
                    BerhasiDetService = 1;
                }

                //VALIDASI QUERY
                if (BerhasiDetSparepart == 1 && BerhasiDetService == 1 && BerhasilFaktur == 1) {
                    JOptionPane.showMessageDialog(null, "DATA PENDAFTARAN BERHASIL DIMASUKKAN");
                } else if (BerhasiDetSparepart == 0) {
                    JOptionPane.showMessageDialog(null, "DATA DETAIL SPAREPART GAGAL DIMASUKKAN");
                } else if (BerhasiDetService == 0) {
                    JOptionPane.showMessageDialog(null, "DATA DETAIL SERVICE GAGAL DIMASUKKAN");
                } else if (BerhasilFaktur == 0) {
                    JOptionPane.showMessageDialog(null, "DATA FAKTUR GAGAL DIMASUKKAN");
                } else if (BerhasiDetSparepart == 0 && BerhasiDetService == 0 && BerhasilFaktur == 0) {
                    JOptionPane.showMessageDialog(null, "DATA PENDAFTARAN GAGAL DIMASUKKAN");
                }
            } else {
                JOptionPane.showMessageDialog(null, "NO POLISI HARUS DI ISI");
                btnCariNopol.requestFocus();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
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

        CariNoPolisi = new javax.swing.JFrame();
        mainPanel1 = new javax.swing.JPanel();
        PanelDirectory1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblNoPol3 = new javax.swing.JLabel();
        btnPilihCustomer = new javax.swing.JButton();
        txtCariNopol = new javax.swing.JTextField();
        tblDetSparepart1 = new javax.swing.JScrollPane();
        tblNoPolisi = new javax.swing.JTable();
        btnNewCustomer = new javax.swing.JButton();
        btnCancelCari = new javax.swing.JButton();
        txtDataTidakDitemukan = new javax.swing.JLabel();
        TambahCustomer = new javax.swing.JFrame();
        mainPanel2 = new javax.swing.JPanel();
        PanelDirectory2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnTambahCustomer = new javax.swing.JButton();
        btnCancelTambah = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblNoPol2 = new javax.swing.JLabel();
        lblNoPol4 = new javax.swing.JLabel();
        lblService6 = new javax.swing.JLabel();
        lblService1 = new javax.swing.JLabel();
        txtIdCustomer = new javax.swing.JTextField();
        txtNamaCustomer = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        txtNoTelp = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblNoPol5 = new javax.swing.JLabel();
        lblService7 = new javax.swing.JLabel();
        lblService8 = new javax.swing.JLabel();
        txtNoPolisi = new javax.swing.JTextField();
        txtNoRangka = new javax.swing.JTextField();
        txtNoMesin = new javax.swing.JTextField();
        lblService2 = new javax.swing.JLabel();
        cmbTipeMotor = new javax.swing.JComboBox<>();
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

        CariNoPolisi.setUndecorated(true);

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));

        PanelDirectory1.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory1.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Kasir/Pendaftaran Service/No Polisi");

        javax.swing.GroupLayout PanelDirectory1Layout = new javax.swing.GroupLayout(PanelDirectory1);
        PanelDirectory1.setLayout(PanelDirectory1Layout);
        PanelDirectory1Layout.setHorizontalGroup(
            PanelDirectory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel10)
                .addContainerGap(148, Short.MAX_VALUE))
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

        btnPilihCustomer.setBackground(new java.awt.Color(240, 240, 240));
        btnPilihCustomer.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnPilihCustomer.setForeground(new java.awt.Color(51, 51, 51));
        btnPilihCustomer.setText("Submit");
        btnPilihCustomer.setBorder(null);
        btnPilihCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPilihCustomerMouseClicked(evt);
            }
        });

        txtCariNopol.setBackground(new java.awt.Color(255, 255, 255));
        txtCariNopol.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtCariNopol.setForeground(new java.awt.Color(51, 51, 51));
        txtCariNopol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        txtCariNopol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariNopolKeyReleased(evt);
            }
        });

        tblNoPolisi.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblNoPolisi.setForeground(new java.awt.Color(0, 0, 0));
        tblNoPolisi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Nama Customer", "No Polisi", "Title 4"
            }
        ));
        tblDetSparepart1.setViewportView(tblNoPolisi);

        btnNewCustomer.setBackground(new java.awt.Color(240, 240, 240));
        btnNewCustomer.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnNewCustomer.setForeground(new java.awt.Color(51, 51, 51));
        btnNewCustomer.setText("New");
        btnNewCustomer.setBorder(null);
        btnNewCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewCustomerMouseClicked(evt);
            }
        });

        btnCancelCari.setBackground(new java.awt.Color(240, 240, 240));
        btnCancelCari.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnCancelCari.setForeground(new java.awt.Color(51, 51, 51));
        btnCancelCari.setText("Cancel");
        btnCancelCari.setBorder(null);
        btnCancelCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelCariMouseClicked(evt);
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPilihCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNoPol3)
                                .addComponent(txtCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(txtCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tblDetSparepart1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(txtDataTidakDitemukan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPilihCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelCari, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout CariNoPolisiLayout = new javax.swing.GroupLayout(CariNoPolisi.getContentPane());
        CariNoPolisi.getContentPane().setLayout(CariNoPolisiLayout);
        CariNoPolisiLayout.setHorizontalGroup(
            CariNoPolisiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CariNoPolisiLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        CariNoPolisiLayout.setVerticalGroup(
            CariNoPolisiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        TambahCustomer.setUndecorated(true);

        mainPanel2.setBackground(new java.awt.Color(255, 255, 255));

        PanelDirectory2.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory2.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Kasir/Pendaftaran Service/No Polisi");

        javax.swing.GroupLayout PanelDirectory2Layout = new javax.swing.GroupLayout(PanelDirectory2);
        PanelDirectory2.setLayout(PanelDirectory2Layout);
        PanelDirectory2Layout.setHorizontalGroup(
            PanelDirectory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDirectory2Layout.setVerticalGroup(
            PanelDirectory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectory2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(39, 39, 39))
        );

        btnTambahCustomer.setBackground(new java.awt.Color(240, 240, 240));
        btnTambahCustomer.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnTambahCustomer.setForeground(new java.awt.Color(51, 51, 51));
        btnTambahCustomer.setText("Submit");
        btnTambahCustomer.setBorder(null);
        btnTambahCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahCustomerActionPerformed(evt);
            }
        });

        btnCancelTambah.setBackground(new java.awt.Color(240, 240, 240));
        btnCancelTambah.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnCancelTambah.setForeground(new java.awt.Color(51, 51, 51));
        btnCancelTambah.setText("Cancel");
        btnCancelTambah.setBorder(null);
        btnCancelTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelTambahActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Customer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel1.setToolTipText("Data Customer");

        lblNoPol2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol2.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol2.setText("Id Customer");

        lblNoPol4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol4.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol4.setText("Nama Customer");

        lblService6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService6.setForeground(new java.awt.Color(51, 51, 51));
        lblService6.setText("Alamat");

        lblService1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService1.setForeground(new java.awt.Color(51, 51, 51));
        lblService1.setText("No Telp");

        txtIdCustomer.setBackground(new java.awt.Color(255, 255, 255));
        txtIdCustomer.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtIdCustomer.setForeground(new java.awt.Color(51, 51, 51));
        txtIdCustomer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtNamaCustomer.setBackground(new java.awt.Color(255, 255, 255));
        txtNamaCustomer.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNamaCustomer.setForeground(new java.awt.Color(51, 51, 51));
        txtNamaCustomer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtAlamat.setBackground(new java.awt.Color(255, 255, 255));
        txtAlamat.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtAlamat.setForeground(new java.awt.Color(51, 51, 51));
        txtAlamat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtNoTelp.setBackground(new java.awt.Color(255, 255, 255));
        txtNoTelp.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoTelp.setForeground(new java.awt.Color(51, 51, 51));
        txtNoTelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblService6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNoPol2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNoPol4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblService1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIdCustomer)
                    .addComponent(lblNoPol2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNamaCustomer)
                    .addComponent(lblNoPol4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblService6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblService1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Motor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel2.setToolTipText("Data Customer");

        lblNoPol5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol5.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol5.setText("No Rangka");

        lblService7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService7.setForeground(new java.awt.Color(51, 51, 51));
        lblService7.setText("No Mesin");

        lblService8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService8.setForeground(new java.awt.Color(51, 51, 51));
        lblService8.setText("Tipe Motor");

        txtNoPolisi.setBackground(new java.awt.Color(255, 255, 255));
        txtNoPolisi.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoPolisi.setForeground(new java.awt.Color(51, 51, 51));
        txtNoPolisi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtNoRangka.setBackground(new java.awt.Color(255, 255, 255));
        txtNoRangka.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoRangka.setForeground(new java.awt.Color(51, 51, 51));
        txtNoRangka.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtNoMesin.setBackground(new java.awt.Color(255, 255, 255));
        txtNoMesin.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoMesin.setForeground(new java.awt.Color(51, 51, 51));
        txtNoMesin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblService2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService2.setForeground(new java.awt.Color(51, 51, 51));
        lblService2.setText("No Polisi");

        cmbTipeMotor.setBackground(new java.awt.Color(255, 255, 255));
        cmbTipeMotor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbTipeMotor.setForeground(new java.awt.Color(51, 51, 51));
        cmbTipeMotor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblService7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNoPol5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblService8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                    .addComponent(lblService2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNoPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNoRangka, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNoMesin, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addComponent(cmbTipeMotor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblService2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNoRangka)
                    .addComponent(lblNoPol5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoMesin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblService7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblService8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipeMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanel2Layout = new javax.swing.GroupLayout(mainPanel2);
        mainPanel2.setLayout(mainPanel2Layout);
        mainPanel2Layout.setHorizontalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelDirectory2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanel2Layout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addComponent(btnCancelTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTambahCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        mainPanel2Layout.setVerticalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelDirectory2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout TambahCustomerLayout = new javax.swing.GroupLayout(TambahCustomer.getContentPane());
        TambahCustomer.getContentPane().setLayout(TambahCustomerLayout);
        TambahCustomerLayout.setHorizontalGroup(
            TambahCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        TambahCustomerLayout.setVerticalGroup(
            TambahCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TambahCustomerLayout.createSequentialGroup()
                .addComponent(mainPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pendaftaran Service");

        mainPanel3.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel3.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory3.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory3.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Kasir/Pendaftaran Service");

        javax.swing.GroupLayout PanelDirectory3Layout = new javax.swing.GroupLayout(PanelDirectory3);
        PanelDirectory3.setLayout(PanelDirectory3Layout);
        PanelDirectory3Layout.setHorizontalGroup(
            PanelDirectory3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addContainerGap(431, Short.MAX_VALUE))
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
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSubmitMouseClicked(evt);
            }
        });
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
                    .addComponent(tblDetSparepart2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDetSparepart2)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(btnHapusServices, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSparepart1)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(cmbSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTambahSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnHapusSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tblDetServices1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanel3Layout.setVerticalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(PanelDirectory3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(lblService3)
                        .addGap(74, 74, 74))
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(mainPanel3Layout.createSequentialGroup()
                                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblNoPol9)
                                            .addComponent(lblNoPol8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(btnCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(mainPanel3Layout.createSequentialGroup()
                                        .addComponent(lblSparepart1)
                                        .addGap(33, 33, 33))))
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTambahSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDetServices1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(tblDetServices1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHapusSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDetSparepart2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tblDetSparepart2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusServices, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void btnPilihCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPilihCustomerMouseClicked
        // TODO add your handling code here:
        klik();
        GetListService();
        GetListSparepart();
        CariNoPolisi.dispose();

    }//GEN-LAST:event_btnPilihCustomerMouseClicked

    private void btnNewCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewCustomerMouseClicked
        // TODO add your handling code here:
        TambahCustomer.show();
        CariNoPolisi.dispose();
        txtCariNopol.setText("");
        GetTipeMotor();
    }//GEN-LAST:event_btnNewCustomerMouseClicked

    private void btnCancelCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelCariMouseClicked
        // TODO add your handling code here:
        CariNoPolisi.dispose();
        txtCariNopol.setText("");
    }//GEN-LAST:event_btnCancelCariMouseClicked

    private void txtCariNopolKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariNopolKeyReleased
        // TODO add your handling code here:
        if (!txtCariNopol.getText().equals("")) {
            CariDataCustomer();
        } else if (txtCariNopol.getText().equals("")) {
            txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
            TampilDataCustomer();
        }
    }//GEN-LAST:event_txtCariNopolKeyReleased

    private void btnTambahCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahCustomerActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Data Yang Dimasukkan Benar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            InsertDataCustomer();
            TampilDataCustomer();
        }
    }//GEN-LAST:event_btnTambahCustomerActionPerformed

    private void btnCancelTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelTambahActionPerformed
        // TODO add your handling code here:
        TambahCustomer.dispose();
        CariNoPolisi.show();
        if (txtCariNopol.getText().equals("")) {
            txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
            TampilDataCustomer();
        }
    }//GEN-LAST:event_btnCancelTambahActionPerformed

    private void btnSubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseClicked

    }//GEN-LAST:event_btnSubmitMouseClicked

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Data Yang Dimasukkan Benar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            InsertDataPendSparepart();
            Clear();
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnHapusServicesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusServicesMouseClicked
        hapusTableService();
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnHapusServicesMouseClicked

    private void btnHapusSparepartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusSparepartMouseClicked
        hapusTableSparepart();
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnHapusSparepartMouseClicked

    private void btnTambahSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSparepartActionPerformed
        SetTableSparepart();
        cmbSparepart.setSelectedIndex(0);
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnTambahSparepartActionPerformed

    private void btnTambahServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahServiceActionPerformed
        SetTableService();
        cmbService.setSelectedIndex(0);
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnTambahServiceActionPerformed

    private void btnCariNopolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariNopolActionPerformed
        CariNoPolisi.show();
        if (txtCariNopol.getText().equals("")) {
            TampilDataCustomer();
            txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
        }
    }//GEN-LAST:event_btnCariNopolActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        Clear();
    }//GEN-LAST:event_btnClearActionPerformed

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
            java.util.logging.Logger.getLogger(PendaftaranService.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PendaftaranService.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PendaftaranService.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PendaftaranService.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PendaftaranService().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame CariNoPolisi;
    private javax.swing.JPanel PanelDirectory1;
    private javax.swing.JPanel PanelDirectory2;
    private javax.swing.JPanel PanelDirectory3;
    private javax.swing.JFrame TambahCustomer;
    private javax.swing.JButton btnCancelCari;
    private javax.swing.JButton btnCancelTambah;
    private javax.swing.JButton btnCariNopol;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHapusServices;
    private javax.swing.JButton btnHapusSparepart;
    private javax.swing.JButton btnNewCustomer;
    private javax.swing.JButton btnPilihCustomer;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnTambahCustomer;
    private javax.swing.JButton btnTambahService;
    private javax.swing.JButton btnTambahSparepart;
    private javax.swing.JComboBox<String> cmbService;
    private javax.swing.JComboBox<String> cmbSparepart;
    private javax.swing.JComboBox<String> cmbTipeMotor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDetServices1;
    private javax.swing.JLabel lblDetSparepart2;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblNoPol3;
    private javax.swing.JLabel lblNoPol4;
    private javax.swing.JLabel lblNoPol5;
    private javax.swing.JLabel lblNoPol8;
    private javax.swing.JLabel lblNoPol9;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblService2;
    private javax.swing.JLabel lblService3;
    private javax.swing.JLabel lblService6;
    private javax.swing.JLabel lblService7;
    private javax.swing.JLabel lblService8;
    private javax.swing.JLabel lblSparepart1;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel mainPanel2;
    private javax.swing.JPanel mainPanel3;
    private javax.swing.JScrollPane tblDetServices1;
    private javax.swing.JScrollPane tblDetSparepart1;
    private javax.swing.JScrollPane tblDetSparepart2;
    private javax.swing.JTable tblNoPolisi;
    private javax.swing.JTable tblService;
    private javax.swing.JTable tblSparepart;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCariNopol;
    private javax.swing.JLabel txtDataTidakDitemukan;
    private javax.swing.JTextField txtIdCustomer;
    private javax.swing.JTextField txtNamaCustomer;
    private javax.swing.JTextField txtNoFaktur;
    private javax.swing.JTextField txtNoMesin;
    private javax.swing.JTextField txtNoPol;
    private javax.swing.JTextField txtNoPolisi;
    private javax.swing.JTextField txtNoRangka;
    private javax.swing.JTextField txtNoTelp;
    // End of variables declaration//GEN-END:variables
}
