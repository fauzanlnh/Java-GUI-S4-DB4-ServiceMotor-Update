/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sparepart;

import Class.DatabaseConnection;
import Class.Login;
import Class.LoginSession;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fauzanlh
 */
public class BarangMasuk extends javax.swing.JFrame {

    /**
     * Creates new form BarangMasuk
     */
    Connection koneksi;

    public BarangMasuk() {
        initComponents();
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        setJDate();
        txtIdPegawai.setText(LoginSession.getIdPegawai());
    }

    public void setJDate() {
        Calendar today = Calendar.getInstance();
        txtTanggal.setDate(today.getTime());
    }

    public void cariData() {
        String kolom[] = {"Id Sparepart", "Nama Sparepart", "Stok", "Nama Jenis"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        int n = 0;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM T_Jenis_Sparepart,T_Jenis_Motor WHERE T_Jenis_Motor.Id_Jenis = T_Jenis_Sparepart.Id_Jenis "
                    + "AND Nama_Sparepart LIKE '%" + txtCari.getText() + "%'   "
                    + "ORDER BY Nama_Sparepart ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String KdSparepart = rs.getString("Id_Sparepart");
                String NamaSparepart = rs.getString("Nama_Sparepart");
                String Stok = rs.getString("Stok");
                String Nama_Jenis = rs.getString("Nama_Jenis");
                dtm.addRow(new String[]{KdSparepart, NamaSparepart, Stok, Nama_Jenis});
                n = n + 1;
            }
            if (n == 0) {
                txtDataTidakDitemukan.setForeground(new java.awt.Color(51, 51, 51));
                txtDataTidakDitemukan.show(true);
                txtCari.requestFocus();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblLihat.setModel(dtm);
    }

    public void lihatSparepart() {
        String kolom[] = {"Id Sparepart", "Nama Sparepart", "Stok", "Nama Jenis"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM T_Jenis_Sparepart,T_Jenis_Motor WHERE T_Jenis_Motor.Id_Jenis = T_Jenis_Sparepart.Id_Jenis "
                    + "ORDER BY Nama_Sparepart ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String KdSparepart = rs.getString("Id_Sparepart");
                String NamaSparepart = rs.getString("Nama_Sparepart");
                String Stok = rs.getString("Stok");
                String Nama_Jenis = rs.getString("Nama_Jenis");
                dtm.addRow(new String[]{KdSparepart, NamaSparepart, Stok, Nama_Jenis});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblLihat.setModel(dtm);
    }

    public void TambahTabel() {
        DefaultTableModel tableModel = (DefaultTableModel) tblDetail.getModel();
        String KdBarang = null;
        try {
            String query = "SELECT * FROM T_Jenis_Sparepart WHERE nama_sparepart = '" + txtSparepart.getText() + "'";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                KdBarang = rs.getString("Id_Sparepart");
            }
        } catch (SQLException e) {

        }
        int tHarga = Integer.parseInt(txtHarga.getText()) * Integer.parseInt(txtQty.getText());
        String harga = String.valueOf(tHarga);
        String[] data = new String[5];
        data[0] = KdBarang;
        data[1] = txtSparepart.getText();
        data[2] = txtHarga.getText();
        data[3] = txtQty.getText();
        data[4] = harga;
        tableModel.addRow(data);
        txtSparepart.setText("");
        txtHarga.setText("");
        txtQty.setText("");
    }

    public void SimpanFaktur() {
        String tanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tanggalLahir);
        String tanggal = String.valueOf(fm.format(txtTanggal.getDate()));
        String KdFaktur = txtNoFaktur.getText().toUpperCase();
        String IdPegawai = txtIdPegawai.getText();
        DefaultTableModel tableModel = (DefaultTableModel) tblDetail.getModel();
        int TotalFaktur = 0, BerhasilInserFaktur = 0, BerhasilInsertDetail = 0, BerhasilUpdateHarga = 0, berhasilUpdate = 0;
        try {
            Statement stmt = koneksi.createStatement();
            //QUERY 1
            String InsertFaktur = "INSERT INTO T_Faktur_Sparepart_Masuk (Id_Sprt_Masuk, Tanggal,Id_Pegawai) VALUES ('" + KdFaktur + "', '" + tanggal + "', '" + IdPegawai + "')";
            System.out.println(InsertFaktur);
            BerhasilInserFaktur = stmt.executeUpdate(InsertFaktur);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                //QUERY 2
                String InsertDetail = ("INSERT INTO T_Det_Sparepart_Masuk (Id_Sprt_Masuk, Id_Sparepart, Jmlh_Masuk, Harga_Masuk, Total_Per_Detail) VALUES("
                        + "'" + KdFaktur + "',"
                        + "'" + tableModel.getValueAt(i, 0) + "',"
                        + "'" + tableModel.getValueAt(i, 3) + "',"
                        + "'" + tableModel.getValueAt(i, 2) + "',"
                        + "'" + tableModel.getValueAt(i, 4) + "')");
                BerhasilInsertDetail = stmt.executeUpdate(InsertDetail);

                String Id_Sparepart = (String) tableModel.getValueAt(i, 0);
                String Banyak = tableModel.getValueAt(i, 3).toString();
                int QTY = Integer.valueOf(Banyak);
                //QUERY 3
                String UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = (Stok +'" + QTY + "') WHERE Id_Sparepart = '" + Id_Sparepart + "'";
                berhasilUpdate = stmt.executeUpdate(UpdateStok);

                String HS = tableModel.getValueAt(i, 4).toString();
                int HargaSatuan = Integer.valueOf(HS);
                TotalFaktur = HargaSatuan + TotalFaktur;
            }
            //QUERY 4
            String UpdateTotalHarga = "UPDATE T_Faktur_Sparepart_Masuk SET Total_Harga = '" + TotalFaktur + "' WHERE Id_Sprt_Masuk = '" + KdFaktur + "' ";
            BerhasilUpdateHarga = stmt.executeUpdate(UpdateTotalHarga);

            if (BerhasilInserFaktur == 1 && BerhasilInsertDetail == 1 && berhasilUpdate == 1 && BerhasilUpdateHarga == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dimasukan");
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Dimasukan");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
        }
    }

    public void Clear() {
        setJDate();
        txtNoFaktur.setText("");
        txtSparepart.setText("");
        txtHarga.setText("");
        txtQty.setText("");
        DefaultTableModel tableModel = (DefaultTableModel) tblDetail.getModel();
        tableModel.setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        F_LihatBarang = new javax.swing.JFrame();
        mainPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        tblDetServices1 = new javax.swing.JScrollPane();
        tblLihat = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblNoPol2 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        txtDataTidakDitemukan = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        mainPanel2 = new javax.swing.JPanel();
        PanelDirectory = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnCekStok = new javax.swing.JPanel();
        lblNoPol6 = new javax.swing.JLabel();
        btnBarangBaru = new javax.swing.JPanel();
        lblNoPol7 = new javax.swing.JLabel();
        lblService1 = new javax.swing.JLabel();
        txtNoFaktur = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        btnTambahTabel = new javax.swing.JButton();
        lblService4 = new javax.swing.JLabel();
        lblService5 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        tblDetServices = new javax.swing.JScrollPane();
        tblDetail = new javax.swing.JTable();
        btnSubmit = new javax.swing.JButton();
        btnHapusService = new javax.swing.JButton();
        lblService6 = new javax.swing.JLabel();
        lblService7 = new javax.swing.JLabel();
        txtSparepart = new javax.swing.JTextField();
        btnLihat = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        lblService8 = new javax.swing.JLabel();
        txtIdPegawai = new javax.swing.JLabel();

        F_LihatBarang.setUndecorated(true);

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(30, 130, 234));
        jPanel3.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Sparepart/Barang Masuk/Lihat Barang");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(39, 39, 39))
        );

        tblLihat.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblLihat.setForeground(new java.awt.Color(51, 51, 51));
        tblLihat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Sparepart", "Nama Sparepart", "Stok", "Nama Jenis"
            }
        ));
        tblDetServices1.setViewportView(tblLihat);

        btnAdd.setBackground(new java.awt.Color(240, 240, 240));
        btnAdd.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 51, 51));
        btnAdd.setText("Add");
        btnAdd.setBorder(null);
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
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

        lblNoPol2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol2.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol2.setText("Cari Nama");

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtCari.setForeground(new java.awt.Color(51, 51, 51));
        txtCari.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        txtDataTidakDitemukan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
        txtDataTidakDitemukan.setText("Data Yang Dicari Tidak Ditemukan");

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addComponent(lblNoPol2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(mainPanel1Layout.createSequentialGroup()
                            .addComponent(txtDataTidakDitemukan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tblDetServices1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol2)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tblDetServices1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataTidakDitemukan)
                    .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout F_LihatBarangLayout = new javax.swing.GroupLayout(F_LihatBarang.getContentPane());
        F_LihatBarang.getContentPane().setLayout(F_LihatBarangLayout);
        F_LihatBarangLayout.setHorizontalGroup(
            F_LihatBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(F_LihatBarangLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        F_LihatBarangLayout.setVerticalGroup(
            F_LihatBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(F_LihatBarangLayout.createSequentialGroup()
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 745, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Barang Masuk");

        mainPanel2.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel2.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Kasir/Cek Stok");

        javax.swing.GroupLayout PanelDirectoryLayout = new javax.swing.GroupLayout(PanelDirectory);
        PanelDirectory.setLayout(PanelDirectoryLayout);
        PanelDirectoryLayout.setHorizontalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectoryLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDirectoryLayout.setVerticalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectoryLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(39, 39, 39))
        );

        btnCekStok.setBackground(new java.awt.Color(255, 255, 255));
        btnCekStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCekStokMouseClicked(evt);
            }
        });

        lblNoPol6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNoPol6.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNoPol6.setText(" Cek Stok >");

        javax.swing.GroupLayout btnCekStokLayout = new javax.swing.GroupLayout(btnCekStok);
        btnCekStok.setLayout(btnCekStokLayout);
        btnCekStokLayout.setHorizontalGroup(
            btnCekStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 143, Short.MAX_VALUE)
            .addGroup(btnCekStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnCekStokLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblNoPol6)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnCekStokLayout.setVerticalGroup(
            btnCekStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
            .addGroup(btnCekStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnCekStokLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblNoPol6)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        btnBarangBaru.setBackground(new java.awt.Color(255, 255, 255));
        btnBarangBaru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBarangBaruMouseClicked(evt);
            }
        });

        lblNoPol7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNoPol7.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNoPol7.setText("    < Barang Baru");

        javax.swing.GroupLayout btnBarangBaruLayout = new javax.swing.GroupLayout(btnBarangBaru);
        btnBarangBaru.setLayout(btnBarangBaruLayout);
        btnBarangBaruLayout.setHorizontalGroup(
            btnBarangBaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBarangBaruLayout.createSequentialGroup()
                .addComponent(lblNoPol7)
                .addGap(0, 53, Short.MAX_VALUE))
        );
        btnBarangBaruLayout.setVerticalGroup(
            btnBarangBaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnBarangBaruLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNoPol7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblService1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService1.setForeground(new java.awt.Color(51, 51, 51));
        lblService1.setText("Harga");

        txtNoFaktur.setBackground(new java.awt.Color(255, 255, 255));
        txtNoFaktur.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoFaktur.setForeground(new java.awt.Color(51, 51, 51));
        txtNoFaktur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtTanggal.setBackground(new java.awt.Color(255, 255, 255));
        txtTanggal.setForeground(new java.awt.Color(51, 51, 51));

        btnTambahTabel.setBackground(new java.awt.Color(240, 240, 240));
        btnTambahTabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTambahTabel.setForeground(new java.awt.Color(51, 51, 51));
        btnTambahTabel.setText("Tambah");
        btnTambahTabel.setBorder(null);
        btnTambahTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahTabelMouseClicked(evt);
            }
        });

        lblService4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService4.setForeground(new java.awt.Color(51, 51, 51));
        lblService4.setText("No Faktur");

        lblService5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService5.setForeground(new java.awt.Color(51, 51, 51));
        lblService5.setText("Qty");

        txtHarga.setBackground(new java.awt.Color(255, 255, 255));
        txtHarga.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtHarga.setForeground(new java.awt.Color(51, 51, 51));
        txtHarga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtQty.setBackground(new java.awt.Color(255, 255, 255));
        txtQty.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtQty.setForeground(new java.awt.Color(51, 51, 51));
        txtQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        tblDetail.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblDetail.setForeground(new java.awt.Color(51, 51, 51));
        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Sparepart", "Nama Sparepart", "Harga", "Qty", "Total"
            }
        ));
        tblDetail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDetailKeyReleased(evt);
            }
        });
        tblDetServices.setViewportView(tblDetail);

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

        btnHapusService.setBackground(new java.awt.Color(240, 240, 240));
        btnHapusService.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHapusService.setForeground(new java.awt.Color(51, 51, 51));
        btnHapusService.setText("Hapus");
        btnHapusService.setBorder(null);
        btnHapusService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusServiceMouseClicked(evt);
            }
        });

        lblService6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService6.setForeground(new java.awt.Color(51, 51, 51));
        lblService6.setText("Nama Sparepart");

        lblService7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService7.setForeground(new java.awt.Color(51, 51, 51));
        lblService7.setText("Tanggal");

        txtSparepart.setBackground(new java.awt.Color(255, 255, 255));
        txtSparepart.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtSparepart.setForeground(new java.awt.Color(51, 51, 51));
        txtSparepart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        btnLihat.setBackground(new java.awt.Color(240, 240, 240));
        btnLihat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLihat.setForeground(new java.awt.Color(51, 51, 51));
        btnLihat.setText("Cari");
        btnLihat.setBorder(null);
        btnLihat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLihatMouseClicked(evt);
            }
        });

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

        lblService8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService8.setForeground(new java.awt.Color(51, 51, 51));
        lblService8.setText("ID Pegawai");

        txtIdPegawai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtIdPegawai.setForeground(new java.awt.Color(51, 51, 51));
        txtIdPegawai.setText("ID Pegawai");

        javax.swing.GroupLayout mainPanel2Layout = new javax.swing.GroupLayout(mainPanel2);
        mainPanel2.setLayout(mainPanel2Layout);
        mainPanel2Layout.setHorizontalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanel2Layout.createSequentialGroup()
                .addComponent(btnBarangBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 401, Short.MAX_VALUE)
                .addComponent(btnCekStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(PanelDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainPanel2Layout.createSequentialGroup()
                                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblService4)
                                    .addComponent(lblService7)
                                    .addComponent(lblService6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblService1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanel2Layout.createSequentialGroup()
                                .addComponent(lblService5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel2Layout.createSequentialGroup()
                                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(mainPanel2Layout.createSequentialGroup()
                                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnTambahTabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(lblService8)
                                .addGap(26, 26, 26)
                                .addComponent(txtIdPegawai))
                            .addGroup(mainPanel2Layout.createSequentialGroup()
                                .addComponent(txtSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapusService, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanel2Layout.setVerticalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCekStok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBarangBaru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblService4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblService8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtIdPegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addComponent(lblService7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(180, 180, 180))
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblService6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblService1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblService5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTambahTabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusService, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahTabelMouseClicked
        if (!txtSparepart.getText().equals("") && !txtHarga.getText().equals("") && !txtQty.getText().equals("")) {
            TambahTabel();
        } else if (txtSparepart.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nama Sparepart Harus Diisi!!");
            txtSparepart.requestFocus();
        } else if (txtHarga.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Harga Beli Harus Diisi!!");
            txtHarga.requestFocus();
        } else if (txtQty.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Jumlah Yang Dibeli Harus Diisi!!");
            txtQty.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak boleh kosong!!");
            txtSparepart.requestFocus();
        }
    }//GEN-LAST:event_btnTambahTabelMouseClicked

    private void btnSubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseClicked
        if (!txtNoFaktur.getText().equals("") && tblDetail.getRowCount() > 0) {
            int ok = JOptionPane.showConfirmDialog(null, "Data Yang Dimasukkan Benar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == 0) {
                SimpanFaktur();
                Clear();
            }
        } else if (txtNoFaktur.getText().equals("") && tblDetail.getRowCount() > 0) {
            JOptionPane.showMessageDialog(null, "No Faktur Harus Diisi!!");
            txtNoFaktur.requestFocus();
        } else if (txtNoFaktur.getText().equals("") && tblDetail.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "No Faktur Dan Detail Pembelian Masih Kosong");
            txtNoFaktur.requestFocus();
        } else if (!txtNoFaktur.getText().equals("") && tblDetail.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "Detail Pembelian Masih Kosong");
            btnLihat.requestFocus();
        }

    }//GEN-LAST:event_btnSubmitMouseClicked

    private void btnHapusServiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusServiceMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tableModel = (DefaultTableModel) tblDetail.getModel();
        int row = tblDetail.getSelectedRow();
        if (row >= 0) {
            int ok = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == 0) {
                tableModel.removeRow(row);
            }
        }
    }//GEN-LAST:event_btnHapusServiceMouseClicked

    private void btnLihatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLihatMouseClicked
        // TODO add your handling code here:
        F_LihatBarang.show();
        F_LihatBarang.setSize(565, 412);
        F_LihatBarang.setLocationRelativeTo(null);
        lihatSparepart();
    }//GEN-LAST:event_btnLihatMouseClicked

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        // TODO add your handling code here:
        String getKodeAa = tblLihat.getValueAt(tblLihat.getSelectedRow(), 1).toString();

        txtSparepart.setText(getKodeAa);
        F_LihatBarang.dispose();
    }//GEN-LAST:event_btnAddMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        // TODO add your handling code here:
        F_LihatBarang.dispose();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        if (!txtCari.getText().equals("")) {
            txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
            cariData();
        } else if (txtCari.getText().equals("")) {
            txtDataTidakDitemukan.setForeground(new java.awt.Color(255, 255, 255));
            lihatSparepart();
        }
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnCekStokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCekStokMouseClicked
        // TODO add your handling code here:
        Gudang ts = new Gudang();
        ts.show();
        this.dispose();
    }//GEN-LAST:event_btnCekStokMouseClicked

    private void btnBarangBaruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBarangBaruMouseClicked
        // TODO add your handling code here:
        BarangBaru ps = new BarangBaru();
        ps.show();
        this.dispose();
    }//GEN-LAST:event_btnBarangBaruMouseClicked

    private void tblDetailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetailKeyReleased
        int Total = 0;
        int Qty = 0;
        String getHarga = tblDetail.getValueAt(tblDetail.getSelectedRow(), 2).toString();
        String getQty = tblDetail.getValueAt(tblDetail.getSelectedRow(), 3).toString();
        try {
            Qty = Integer.parseInt(getQty);
        } catch (NumberFormatException e) {

        }

        int Harga = Integer.parseInt(getHarga);
        Total = Qty * Harga;
        tblDetail.setValueAt(Total, tblDetail.getSelectedRow(), 4);        // TODO add your handling code here:

    }//GEN-LAST:event_tblDetailKeyReleased

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
            java.util.logging.Logger.getLogger(BarangMasuk.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BarangMasuk.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BarangMasuk.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BarangMasuk.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BarangMasuk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame F_LihatBarang;
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JButton btnAdd;
    private javax.swing.JPanel btnBarangBaru;
    private javax.swing.JButton btnCancel;
    private javax.swing.JPanel btnCekStok;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHapusService;
    private javax.swing.JButton btnLihat;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnTambahTabel;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblNoPol6;
    private javax.swing.JLabel lblNoPol7;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblService4;
    private javax.swing.JLabel lblService5;
    private javax.swing.JLabel lblService6;
    private javax.swing.JLabel lblService7;
    private javax.swing.JLabel lblService8;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel mainPanel2;
    private javax.swing.JScrollPane tblDetServices;
    private javax.swing.JScrollPane tblDetServices1;
    private javax.swing.JTable tblDetail;
    private javax.swing.JTable tblLihat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JLabel txtDataTidakDitemukan;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JLabel txtIdPegawai;
    private javax.swing.JTextField txtNoFaktur;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSparepart;
    private com.toedter.calendar.JDateChooser txtTanggal;
    // End of variables declaration//GEN-END:variables
}
