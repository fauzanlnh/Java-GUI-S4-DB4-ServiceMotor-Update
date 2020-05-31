/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_Kasir_Done;

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
    String KdJenisMotor;

    public PendaftaranService() {
        initComponents();
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        CariNoPolisi.setSize(575, 440);
        CariNoPolisi.setLocationRelativeTo(null);
        TambahCustomer.setSize(625, 450);
        TambahCustomer.setLocationRelativeTo(null);
        setKodeFaktur();
        getListService();
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
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblNoPolisi.setModel(dtm);
    }

    public void cariDataCustomer() {
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
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblNoPolisi.setModel(dtm);
    }

    public void InsertDataCustomer() {
        String IdCustomer = txtIdCustomer.getText().toUpperCase();
        String NamaCustomer = txtNamaCustomer.getText().toUpperCase();
        String Alamat = txtAlamat.getText().toUpperCase();
        String NoTelp = txtNoTelp.getText();
        try {
            Statement stmt = koneksi.createStatement();
            String query = "insert into T_Customer values ('" + IdCustomer + "', '" + NamaCustomer + "','" + Alamat + "','" + NoTelp + "')";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                System.out.println("Data Customer Berhasi Dimasukan");
            } else {
                System.out.println("Data Customer Gagal Dimasukan");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
        }
    }

    public void InsertDataMotor() {
        String KdTipeMotor = "";
        String IdCustomer = txtIdCustomer.getText().toUpperCase();
        String NoPolisi = txtNoPolisi.getText().toUpperCase();
        String NoRangka = txtNoRangka.getText().toUpperCase();
        String NoMesin = txtNoMesin.getText().toUpperCase();
        String TipeMotor = cmbTipeMotor.getSelectedItem().toString();
        try {
            String SelectKD = "SELECT * FROM T_Tipe WHERE Nama_Tipe = '" + TipeMotor + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectKD);
            if (rs.next()) {
                KdTipeMotor = rs.getString("Id_Tipe");
            }
            try {
                Statement stmt = koneksi.createStatement();
                String query = "INSERT INTO T_Motor VALUES ('" + NoPolisi + "', '" + NoRangka + "','" + NoMesin + "','" + IdCustomer + "','" + KdTipeMotor + "')";
                System.out.println(query);
                int berhasil = stmt.executeUpdate(query);
                if (berhasil == 1) {
                    JOptionPane.showMessageDialog(null, "Data Customer Berhasil Dimasukan");
                    System.out.println("Data Motor Berhasi Dimasukan");
                } else {
                    JOptionPane.showMessageDialog(null, "Data Customer Gagal Dimasukan");
                    System.out.println("Data Motor Berhasi Dimasukan");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
        }
    }

    public void getTipeMotor() {
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

    public void klik() {
        String getNoPol = tblNoPolisi.getValueAt(tblNoPolisi.getSelectedRow(), 2).toString();

        txtNoPol.setText(getNoPol);
    }

    public void setKodeFaktur() {
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

    public void setTableService() {
        DefaultTableModel tableModel = (DefaultTableModel) tblService.getModel();
        String KdJasa = null, Harga = null;
        String NamaJasa = cmbService.getSelectedItem().toString();
        try {
            String query = "SELECT * FROM T_Jasa WHERE Nama_Jasa = '" + NamaJasa + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                KdJasa = rs.getString("Id_Jasa");
                Harga = rs.getString("Harga_Jasa");
            }
        } catch (SQLException e) {

        }
        int no = tblService.getRowCount() + 1;
        String No = Integer.toString(no);
        String[] data = new String[4];
        data[0] = No;
        data[1] = KdJasa;
        data[2] = NamaJasa;
        data[3] = Harga;
        tableModel.addRow(data);
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

    public void setTableSparepart() {
        DefaultTableModel tableModel = (DefaultTableModel) tblSparepart.getModel();
        String KdBarang = null, Harga = null;
        String NamaSparepart = cmbSparepart.getSelectedItem().toString();
        try {
            String query = "SELECT * FROM t_sparepart WHERE nama_sparepart = '" + NamaSparepart + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                KdBarang = rs.getString("Id_Sparepart");
                Harga = rs.getString("Harga_Sparepart");
            }
        } catch (SQLException e) {

        }
        int no = tblSparepart.getRowCount() + 1;
        String No = Integer.toString(no);
        String[] data = new String[4];
        data[0] = No;
        data[1] = KdBarang;
        data[2] = NamaSparepart;
        data[3] = Harga;
        tableModel.addRow(data);
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
    //Sparepart

    //Services
    //Database
    public void InsertDataFaktur() {
        String NoPolisi = txtNoPol.getText().toUpperCase();
        String NoFaktur = txtNoFaktur.getText().toUpperCase();
        String IdCustomer = "";
        try {
            String SelectIDCust = "SELECT * FROM T_Motor WHERE No_Polisi = '" + NoPolisi + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(SelectIDCust);
            while (rs.next()) {
                IdCustomer = rs.getString("Id_Customer");
            }
        } catch (SQLException e) {
        }
        try {
            Statement stmt = koneksi.createStatement();
            String query = "INSERT INTO T_Faktur (Id_Faktur, No_Polisi, Id_Customer, Status)VALUES ('" + NoFaktur + "', '" + NoPolisi + "','" + IdCustomer + "', 'PROSES')";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                System.out.println("Data Faktur Berhasi Dimasukan");
            } else {
                System.out.println("Data Faktur Gagal Dimasukan");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
        }
    }

    //Insert Det Service & Det Sparepart Disatukan
    public void InsertDataDetFakturSparepart() {
        DefaultTableModel tableModel = (DefaultTableModel) tblSparepart.getModel();
        String KdFaktur = txtNoFaktur.getText().toUpperCase();
        try {
            Statement stmt = koneksi.createStatement();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String Insert = ("INSERT INTO T_Det_Pendaftaran_Sparepart (Id_Faktur, Id_Sparepart, Harga) VALUES("
                        + "'" + KdFaktur + "',"
                        + "'" + tableModel.getValueAt(i, 1) + "',"
                        + "'" + tableModel.getValueAt(i, 3) + "')");
                int berhasil = stmt.executeUpdate(Insert);
                if (berhasil == 1) {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dimasukan");
                    System.out.println(Insert);
                } else {
                    JOptionPane.showMessageDialog(null, "Data Gagal Dimasukan");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
        }
    }

    public void InsertDataDetFakturService() {
        DefaultTableModel tableModel = (DefaultTableModel) tblService.getModel();
        String KdFaktur = txtNoFaktur.getText().toUpperCase();
        try {
            Statement stmt = koneksi.createStatement();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String Insert = ("INSERT INTO T_Det_Pendaftaran_Jasa (Id_Faktur, Id_Jasa, Harga) VALUES("
                        + "'" + KdFaktur + "',"
                        + "'" + tableModel.getValueAt(i, 1) + "',"
                        + "'" + tableModel.getValueAt(i, 3) + "')");
                int berhasil = stmt.executeUpdate(Insert);
                if (berhasil == 1) {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dimasukan");
                    System.out.println(Insert);
                } else {
                    JOptionPane.showMessageDialog(null, "Data Gagal Dimasukan");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
        }
    }

    //Form
    public void Clear() {
        cmbSparepart.removeAllItems();
        cmbSparepart.addItem("-");
        cmbService.removeAllItems();
        cmbService.addItem("-");
        txtNoPol.setText("");
        DefaultTableModel tableModel = (DefaultTableModel) tblService.getModel();
        tableModel.setRowCount(0);
        DefaultTableModel tableModel2 = (DefaultTableModel) tblSparepart.getModel();
        tableModel2.setRowCount(0);
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
        btnPilihCustomer1 = new javax.swing.JButton();
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
        btnSubmit = new javax.swing.JButton();
        btnHapusServices = new javax.swing.JButton();
        btnHapusSparepart = new javax.swing.JButton();
        btnTambahSparepart = new javax.swing.JButton();
        btnTambahService = new javax.swing.JButton();
        btnCariNopol = new javax.swing.JButton();
        txtNoPol = new javax.swing.JTextField();
        txtNoFaktur = new javax.swing.JTextField();
        lblDetServices = new javax.swing.JLabel();
        tblDetServices = new javax.swing.JScrollPane();
        tblSparepart = new javax.swing.JTable();
        tblDetSparepart = new javax.swing.JScrollPane();
        tblService = new javax.swing.JTable();
        lblDetSparepart1 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();

        CariNoPolisi.setUndecorated(true);

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));

        PanelDirectory1.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory1.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("UI_Kasir_Done/Pendaftaran Service/No Polisi");

        javax.swing.GroupLayout PanelDirectory1Layout = new javax.swing.GroupLayout(PanelDirectory1);
        PanelDirectory1.setLayout(PanelDirectory1Layout);
        PanelDirectory1Layout.setHorizontalGroup(
            PanelDirectory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel10)
                .addContainerGap(42, Short.MAX_VALUE))
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

        btnPilihCustomer1.setBackground(new java.awt.Color(240, 240, 240));
        btnPilihCustomer1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnPilihCustomer1.setForeground(new java.awt.Color(51, 51, 51));
        btnPilihCustomer1.setText("Cari");
        btnPilihCustomer1.setBorder(null);
        btnPilihCustomer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPilihCustomer1MouseClicked(evt);
            }
        });

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
                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                    .addComponent(txtCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnPilihCustomer1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPilihCustomer1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jLabel12.setText("UI_Kasir_Done/Pendaftaran Service/No Polisi");

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
        btnTambahCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahCustomerMouseClicked(evt);
            }
        });

        btnCancelTambah.setBackground(new java.awt.Color(240, 240, 240));
        btnCancelTambah.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnCancelTambah.setForeground(new java.awt.Color(51, 51, 51));
        btnCancelTambah.setText("Cancel");
        btnCancelTambah.setBorder(null);
        btnCancelTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelTambahMouseClicked(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
                .addComponent(pnlKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        PanelDirectory.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("UI_Kasir_Done/Pendaftaran Service");

        javax.swing.GroupLayout PanelDirectoryLayout = new javax.swing.GroupLayout(PanelDirectory);
        PanelDirectory.setLayout(PanelDirectoryLayout);
        PanelDirectoryLayout.setHorizontalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectoryLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addContainerGap(191, Short.MAX_VALUE))
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
        lblNoPol.setText("No Faktur");

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
        lblNoPol1.setText("No Polisi");

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
        btnTambahSparepart.setText("Tambah");
        btnTambahSparepart.setBorder(null);
        btnTambahSparepart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahSparepartMouseClicked(evt);
            }
        });

        btnTambahService.setBackground(new java.awt.Color(240, 240, 240));
        btnTambahService.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTambahService.setForeground(new java.awt.Color(51, 51, 51));
        btnTambahService.setText("Tambah");
        btnTambahService.setBorder(null);
        btnTambahService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahServiceMouseClicked(evt);
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

        lblDetServices.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDetServices.setForeground(new java.awt.Color(51, 51, 51));
        lblDetServices.setText("List Sparepart");

        tblSparepart.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblSparepart.setForeground(new java.awt.Color(51, 51, 51));
        tblSparepart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Sparepart", "Nama Sparepart", "Harga"
            }
        ));
        tblDetServices.setViewportView(tblSparepart);

        tblService.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblService.setForeground(new java.awt.Color(51, 51, 51));
        tblService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Service", "Nama Service", "Harga"
            }
        ));
        tblDetSparepart.setViewportView(tblService);

        lblDetSparepart1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDetSparepart1.setForeground(new java.awt.Color(51, 51, 51));
        lblDetSparepart1.setText("List Services");

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

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblService)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNoPol1)
                                    .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(btnCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNoPol)
                                            .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblSparepart))
                                        .addGap(83, 83, 83)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnHapusSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnHapusServices, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnTambahSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(65, 65, 65))
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(PanelDirectory, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblDetSparepart1)
                                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(tblDetSparepart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                                                .addComponent(lblDetServices, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(tblDetServices, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                                    .addGap(184, 184, 184))
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol1)
                    .addComponent(lblNoPol))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCariNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSparepart)
                    .addComponent(lblService))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahService, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHapusSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(lblDetServices)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDetSparepart1)
                .addGap(12, 12, 12)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tblDetSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusServices, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
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
            .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnTambahServiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahServiceMouseClicked
        setTableService();
        cmbService.setSelectedIndex(0);
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnTambahServiceMouseClicked

    private void btnTambahSparepartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahSparepartMouseClicked
        setTableSparepart();
        cmbSparepart.setSelectedIndex(0);
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnTambahSparepartMouseClicked

    private void btnHapusSparepartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusSparepartMouseClicked
        hapusTableSparepart();
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnHapusSparepartMouseClicked

    private void btnHapusServicesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusServicesMouseClicked
        hapusTableService();
        if (tblSparepart.getRowCount() > 0 || tblService.getRowCount() > 0) {
            btnCariNopol.setEnabled(false);
        } else {
            btnCariNopol.setEnabled(true);
        }
    }//GEN-LAST:event_btnHapusServicesMouseClicked

    private void btnSubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseClicked
        InsertDataFaktur();
        if (tblSparepart.getRowCount() > 0) {
            InsertDataDetFakturSparepart();
        }
        if (tblService.getRowCount() > 0) {
            InsertDataDetFakturService();
        }
    }//GEN-LAST:event_btnSubmitMouseClicked

    private void minimizePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizePanelMouseClicked
        // TODO add your handling code here:
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minimizePanelMouseClicked

    private void closePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closePanelMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closePanelMouseClicked

    private void btnPilihCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPilihCustomerMouseClicked
        // TODO add your handling code here:
        klik();
        getListService();
        getListSparepart();
        CariNoPolisi.dispose();

    }//GEN-LAST:event_btnPilihCustomerMouseClicked

    private void btnNewCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewCustomerMouseClicked
        // TODO add your handling code here:
        TambahCustomer.show();
        CariNoPolisi.dispose();
        txtCariNopol.setText("");
        getTipeMotor();
    }//GEN-LAST:event_btnNewCustomerMouseClicked

    private void btnCancelCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelCariMouseClicked
        // TODO add your handling code here:
        CariNoPolisi.dispose();
        txtCariNopol.setText("");
    }//GEN-LAST:event_btnCancelCariMouseClicked

    private void btnTambahCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahCustomerMouseClicked
        // TODO add your handling code here:
        InsertDataCustomer();
        InsertDataMotor();
        TampilDataCustomer();
        TambahCustomer.dispose();
        CariNoPolisi.show();

    }//GEN-LAST:event_btnTambahCustomerMouseClicked

    private void btnCancelTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelTambahMouseClicked
        // TODO add your handling code here:
        TambahCustomer.dispose();
        CariNoPolisi.show();
        if (txtCariNopol.getText().equals("")) {
            txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
            TampilDataCustomer();
        }
    }//GEN-LAST:event_btnCancelTambahMouseClicked

    private void btnPilihCustomer1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPilihCustomer1MouseClicked
        // TODO add your handling code here:
        cariDataCustomer();
    }//GEN-LAST:event_btnPilihCustomer1MouseClicked

    private void txtCariNopolKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariNopolKeyReleased
        // TODO add your handling code here:
        if (txtCariNopol.getText().equals("")) {
            txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
            TampilDataCustomer();
        }
    }//GEN-LAST:event_txtCariNopolKeyReleased

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
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JPanel PanelDirectory1;
    private javax.swing.JPanel PanelDirectory2;
    private javax.swing.JFrame TambahCustomer;
    private javax.swing.JButton btnCancelCari;
    private javax.swing.JButton btnCancelTambah;
    private javax.swing.JButton btnCariNopol;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHapusServices;
    private javax.swing.JButton btnHapusSparepart;
    private javax.swing.JButton btnNewCustomer;
    private javax.swing.JButton btnPilihCustomer;
    private javax.swing.JButton btnPilihCustomer1;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnTambahCustomer;
    private javax.swing.JButton btnTambahService;
    private javax.swing.JButton btnTambahSparepart;
    private javax.swing.JPanel closePanel;
    private javax.swing.JComboBox<String> cmbService;
    private javax.swing.JComboBox<String> cmbSparepart;
    private javax.swing.JComboBox<String> cmbTipeMotor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDetServices;
    private javax.swing.JLabel lblDetSparepart1;
    private javax.swing.JLabel lblNoPol;
    private javax.swing.JLabel lblNoPol1;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblNoPol3;
    private javax.swing.JLabel lblNoPol4;
    private javax.swing.JLabel lblNoPol5;
    private javax.swing.JLabel lblService;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblService2;
    private javax.swing.JLabel lblService6;
    private javax.swing.JLabel lblService7;
    private javax.swing.JLabel lblService8;
    private javax.swing.JLabel lblSparepart;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel mainPanel2;
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
