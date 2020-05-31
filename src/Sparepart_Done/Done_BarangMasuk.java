/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sparepart_Done;

import Class.DatabaseConnection;
import Class.Login;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fauzanlh
 */
public class Done_BarangMasuk extends javax.swing.JFrame {

    /**
     * Creates new form Done_BarangMasuk
     */
    Connection koneksi;

    public Done_BarangMasuk() {
        initComponents();
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
    }

    public void lihatSparepart() {
        DefaultTableModel tableModel = (DefaultTableModel) tblLihat.getModel();
        tableModel.getDataVector().removeAllElements();
        tableModel.fireTableDataChanged();
        try {
            Statement stmt = koneksi.createStatement();
            String query_bukaTabel = "SELECT * FROM t_sparepart order by nama_sparepart";
            ResultSet rs = stmt.executeQuery(query_bukaTabel);
            while (rs.next()) {
                Object[] getO = new Object[3];
                getO[0] = rs.getString("id_sparepart");
                getO[1] = rs.getString("nama_sparepart");
                getO[2] = rs.getString("stok");
                tableModel.addRow(getO);
            }
        } catch (SQLException e) {

        }
    }

    public void TambahTabel() {
        DefaultTableModel tableModel = (DefaultTableModel) tblDetail.getModel();
        String KdBarang = null;
        try {
            String query = "SELECT * FROM t_sparepart WHERE nama_sparepart = '" + txtSparepart.getText() + "'";
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
        try {
            Statement stmt = koneksi.createStatement();
            String Insert = "INSERT INTO T_Faktur_Sparepart_Masuk (Id_Sprt_Masuk, Tanggal) VALUES ('" + KdFaktur + "', '" + tanggal + "')";
            System.out.println(Insert);
            int berhasil = stmt.executeUpdate(Insert);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dimasukan");
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Dimasukan");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database" + ex);
        }
    }

    public void SimpanDetFaktur() {
        DefaultTableModel tableModel = (DefaultTableModel) tblDetail.getModel();
        String KdFaktur = txtNoFaktur.getText().toUpperCase();
        int TotalFaktur = 0;
        try {
            Statement stmt = koneksi.createStatement();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String Insert = ("INSERT INTO T_Det_Sparepart_Masuk (Id_Sprt_Masuk, Id_Sparepart, Jmlh_Masuk, Harga_Masuk, Total_Per_Detail) VALUES("
                        + "'" + KdFaktur + "',"
                        + "'" + tableModel.getValueAt(i, 0) + "',"
                        + "'" + tableModel.getValueAt(i, 3) + "',"
                        + "'" + tableModel.getValueAt(i, 2) + "',"
                        + "'" + tableModel.getValueAt(i, 4) + "')");
                int berhasil = stmt.executeUpdate(Insert);
                if (berhasil == 1) {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dimasukan");
                    System.out.println(Insert);
                } else {
                    JOptionPane.showMessageDialog(null, "Data Gagal Dimasukan");
                }

                String Id_Sparepart = (String) tableModel.getValueAt(i, 0);
                String Banyak = tableModel.getValueAt(i, 3).toString();
                int QTY = Integer.valueOf(Banyak);

                String UpdateStok = "UPDATE T_Sparepart SET Stok = (Stok +'" + QTY + "') WHERE Id_Sparepart = '" + Id_Sparepart + "'";
                int berhasilUpdate = stmt.executeUpdate(UpdateStok);
                if (berhasilUpdate == 1) {
                    JOptionPane.showMessageDialog(null, "Data Stok Berhasil Diubah");
                    System.out.println(UpdateStok);
                } else {
                    JOptionPane.showMessageDialog(null, "Data Stok Gagal Diubah");
                }

                String HS = tableModel.getValueAt(i, 4).toString();
                int HargaSatuan = Integer.valueOf(HS);
                TotalFaktur = HargaSatuan + TotalFaktur;
            }
            String UpdateTptalHarga = "UPDATE T_Faktur_Sparepart_Masuk SET Total_Harga = '" + TotalFaktur + "' WHERE Id_Sprt_Masuk = '" + KdFaktur + "' ";
            int berhasil = stmt.executeUpdate(UpdateTptalHarga);

            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Total Harga Berhasil Diubah");
            } else {
                JOptionPane.showMessageDialog(null, "Data Total Harga Gagal Diubah");
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

        F_LihatBarang = new javax.swing.JFrame();
        mainPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        tblDetServices1 = new javax.swing.JScrollPane();
        tblLihat = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        sidePanel = new javax.swing.JPanel();
        pnlBarba = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlKeluar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnlBarma = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnlHome = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblJudul = new javax.swing.JLabel();
        pnlGudang = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnlLaporan = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        closePanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        minimizePanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
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
                "Id Sparepart", "Nama Sparepart", "Stok"
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

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tblDetServices1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(tblDetServices1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(mainPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        sidePanel.setBackground(new java.awt.Color(0, 102, 204));
        sidePanel.setPreferredSize(new java.awt.Dimension(348, 411));

        pnlBarba.setBackground(new java.awt.Color(30, 130, 234));
        pnlBarba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBarbaMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/S_New White.png"))); // NOI18N
        jLabel1.setText("Tambah Barang Baru");
        jLabel1.setIconTextGap(30);

        javax.swing.GroupLayout pnlBarbaLayout = new javax.swing.GroupLayout(pnlBarba);
        pnlBarba.setLayout(pnlBarbaLayout);
        pnlBarbaLayout.setHorizontalGroup(
            pnlBarbaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBarbaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBarbaLayout.setVerticalGroup(
            pnlBarbaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBarbaLayout.createSequentialGroup()
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

        pnlBarma.setBackground(new java.awt.Color(30, 130, 234));
        pnlBarma.setPreferredSize(new java.awt.Dimension(303, 58));
        pnlBarma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBarmaMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/S_Product White.png"))); // NOI18N
        jLabel3.setText("Barang Masuk");
        jLabel3.setIconTextGap(30);

        javax.swing.GroupLayout pnlBarmaLayout = new javax.swing.GroupLayout(pnlBarma);
        pnlBarma.setLayout(pnlBarmaLayout);
        pnlBarmaLayout.setHorizontalGroup(
            pnlBarmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBarmaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBarmaLayout.setVerticalGroup(
            pnlBarmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBarmaLayout.createSequentialGroup()
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

        lblJudul.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(255, 255, 255));
        lblJudul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/SS_Support.png"))); // NOI18N
        lblJudul.setText("    Service Motor");

        pnlGudang.setBackground(new java.awt.Color(30, 130, 234));
        pnlGudang.setPreferredSize(new java.awt.Dimension(303, 58));
        pnlGudang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlGudangMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/S_Warehouse Storage White.png"))); // NOI18N
        jLabel6.setText("Gudang");
        jLabel6.setIconTextGap(30);

        javax.swing.GroupLayout pnlGudangLayout = new javax.swing.GroupLayout(pnlGudang);
        pnlGudang.setLayout(pnlGudangLayout);
        pnlGudangLayout.setHorizontalGroup(
            pnlGudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGudangLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGudangLayout.setVerticalGroup(
            pnlGudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGudangLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pnlLaporan.setBackground(new java.awt.Color(30, 130, 234));
        pnlLaporan.setPreferredSize(new java.awt.Dimension(303, 58));
        pnlLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlLaporanMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Report White.png"))); // NOI18N
        jLabel10.setText("Laporan");
        jLabel10.setIconTextGap(30);

        javax.swing.GroupLayout pnlLaporanLayout = new javax.swing.GroupLayout(pnlLaporan);
        pnlLaporan.setLayout(pnlLaporanLayout);
        pnlLaporanLayout.setHorizontalGroup(
            pnlLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLaporanLayout.setVerticalGroup(
            pnlLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel10)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBarba, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlBarma, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .addComponent(pnlHome, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
            .addComponent(pnlGudang, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .addComponent(pnlLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblJudul)
                .addGap(40, 40, 40)
                .addComponent(pnlHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlBarba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlBarma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlGudang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(30, 130, 234));
        jPanel2.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Sparepart_Done/Barang Masuk");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(39, 39, 39))
        );

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

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblService4)
                                    .addComponent(lblService7)
                                    .addComponent(lblService6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblService1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                                .addComponent(lblService5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnTambahTabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(txtSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(302, 302, 302)
                        .addComponent(btnHapusService, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblService4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(lblService7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(180, 180, 180))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblService6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblService1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblService5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTambahTabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusService, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closePanelMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closePanelMouseClicked

    private void minimizePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizePanelMouseClicked
        // TODO add your handling code here:
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minimizePanelMouseClicked

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
        // TODO add your handling code here:      
        if (!txtNoFaktur.getText().equals("")) {
            txtNoFaktur.setText("");
            SimpanFaktur();
            SimpanDetFaktur();
        } else {
            JOptionPane.showMessageDialog(null, "No Faktur Harus Diisi!!");
            txtNoFaktur.requestFocus();
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

    private void pnlBarbaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBarbaMouseClicked
        dispose();
        Done_BarangMasuk ps = new Done_BarangMasuk();
        ps.show();
    }//GEN-LAST:event_pnlBarbaMouseClicked

    private void pnlKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKeluarMouseClicked
        // TODO add your handling code here:
        dispose();
        Login ps = new Login();
        ps.show();
    }//GEN-LAST:event_pnlKeluarMouseClicked

    private void pnlBarmaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBarmaMouseClicked
        // TODO add your handling code here:
        dispose();
        Done_BarangMasuk ps = new Done_BarangMasuk();
        ps.show();
    }//GEN-LAST:event_pnlBarmaMouseClicked

    private void pnlHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHomeMouseClicked
        // TODO add your handling code here:
        dispose();
        HomeSparepart hk = new HomeSparepart();
        hk.show();
    }//GEN-LAST:event_pnlHomeMouseClicked

    private void pnlGudangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGudangMouseClicked
        // TODO add your handling code here:
        Done_Gudang gd = new Done_Gudang();
        gd.show();
    }//GEN-LAST:event_pnlGudangMouseClicked

    private void pnlLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLaporanMouseClicked
        // TODO add your handling code here:
        Done_Laporan lp = new Done_Laporan();
        lp.show();
    }//GEN-LAST:event_pnlLaporanMouseClicked

    private void btnLihatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLihatMouseClicked
        // TODO add your handling code here:
        F_LihatBarang.show();
        F_LihatBarang.setSize(578, 502);
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
            java.util.logging.Logger.getLogger(Done_BarangMasuk.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Done_BarangMasuk.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Done_BarangMasuk.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Done_BarangMasuk.class
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Done_BarangMasuk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame F_LihatBarang;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnHapusService;
    private javax.swing.JButton btnLihat;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnTambahTabel;
    private javax.swing.JPanel closePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblService4;
    private javax.swing.JLabel lblService5;
    private javax.swing.JLabel lblService6;
    private javax.swing.JLabel lblService7;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel minimizePanel;
    private javax.swing.JPanel pnlBarba;
    private javax.swing.JPanel pnlBarma;
    private javax.swing.JPanel pnlGudang;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlKeluar;
    private javax.swing.JPanel pnlLaporan;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JScrollPane tblDetServices;
    private javax.swing.JScrollPane tblDetServices1;
    private javax.swing.JTable tblDetail;
    private javax.swing.JTable tblLihat;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtNoFaktur;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSparepart;
    private com.toedter.calendar.JDateChooser txtTanggal;
    // End of variables declaration//GEN-END:variables
}
