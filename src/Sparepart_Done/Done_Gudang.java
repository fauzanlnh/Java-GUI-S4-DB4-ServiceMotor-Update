/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sparepart_Done;

import Class.DatabaseConnection;
import Class.Login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fauzanlh
 */
public class Done_Gudang extends javax.swing.JFrame {

    /**
     * Creates new form Done_Gudang
     */
    Connection koneksi;

    public Done_Gudang() {
        initComponents();
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        showData();
    }

    public void showData() {
        String kolom[] = {"NO", "Kode Sparepart", "Nama Sparepart", "Stok", "Harga Jual"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        try {
            Statement stmt = koneksi.createStatement();
            if (cmbUrut.getSelectedIndex() == 0) {
                query = "SELECT * FROM T_Sparepart ORDER BY Id_Sparepart";
            } else if (cmbUrut.getSelectedIndex() == 1) {
                query = "SELECT * FROM T_Sparepart ORDER BY Nama_Sparepart";
            } else if (cmbUrut.getSelectedIndex() == 2) {
                query = "SELECT * FROM T_Sparepart ORDER BY Stok";
            } else if (cmbUrut.getSelectedIndex() == 3) {
                query = "SELECT * FROM T_Sparepart ORDER BY Harga_Sparepart ASC";
            } else if (cmbUrut.getSelectedIndex() == 4) {
                query = "SELECT * FROM T_Sparepart ORDER BY Harga_Sparepart DESC";
            }

            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String KdSparepart = rs.getString("Id_Sparepart");
                String NamaSparepart = rs.getString("Nama_Sparepart");
                String Stok = rs.getString("Stok");
                String Harga = rs.getString("Harga_Sparepart");
                dtm.addRow(new String[]{no + "", KdSparepart, NamaSparepart, Stok, Harga});
                no++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblSparepart.setModel(dtm);
    }

    public void cariData() {
        String kolom[] = {"NO", "Kode Sparepart", "Nama Sparepart", "Stok", "Harga Jual"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        try {
            Statement stmt = koneksi.createStatement();
            if (cmbUrut.getSelectedIndex() == 0) {
                query = "SELECT * FROM T_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
                        + "%' OR Nama_Sparepart LIKE '%" + txtCari.getText() + "%' ORDER BY Id_Sparepart ";
            } else if (cmbUrut.getSelectedIndex() == 1) {
                query = "SELECT * FROM T_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
                        + "%' OR Nama_Sparepart LIKE '%" + txtCari.getText() + "%' ORDER BY Nama_Sparepart";
            } else if (cmbUrut.getSelectedIndex() == 2) {
                query = "SELECT * FROM T_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
                        + "%' OR Nama_Sparepart LIKE '%" + txtCari.getText() + "%' ORDER BY Stok";
            } else if (cmbUrut.getSelectedIndex() == 3) {
                query = "SELECT * FROM T_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
                        + "%' OR Nama_Sparepart LIKE '%" + txtCari.getText() + "%' ORDER BY Harga_Sparepart ASC";
            } else if (cmbUrut.getSelectedIndex() == 4) {
                query = "SELECT * FROM T_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
                        + "%' OR Nama_Sparepart LIKE '%" + txtCari.getText() + "%' ORDER BY Harga_Sparepart DESC";
            }
            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String KdSparepart = rs.getString("Id_Sparepart");
                String NamaSparepart = rs.getString("Nama_Sparepart");
                String Stok = rs.getString("Stok");
                String Harga = rs.getString("Harga_Sparepart");
                dtm.addRow(new String[]{no + "", KdSparepart, NamaSparepart, Stok, Harga});
                no++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblSparepart.setModel(dtm);
    }

    public void Reset() {
        txtKdBarang.setText("");
        txtNamaBarang.setText("");
        txtHarga.setText("");
    }

    public void klik() {
        String getKdSparepart = tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 1).toString();
        String getNamaSparepart = tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 2).toString();
        String getHarga = tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 4).toString();

        txtKdBarang.setText(getKdSparepart);
        txtNamaBarang.setText(getNamaSparepart);
        txtHarga.setText(getHarga);
    }

    public void UbahData() {
        String KdBarang = txtKdBarang.getText().toUpperCase();
        String NamaBarang = txtNamaBarang.getText().toUpperCase();
        String HargaJual = txtHarga.getText().toUpperCase();

        try {
            Statement stmt = koneksi.createStatement();
            String query = "UPDATE T_Sparepart SET Nama_Sparepart = '" + NamaBarang + "', Harga_Sparepart = '" + HargaJual + "' WHERE Id_Sparepart = '" + KdBarang + "'";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Dimasukan");
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

        BarangUbah = new javax.swing.JFrame();
        mainPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        lblNoPol3 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        lblNoPol4 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        lblNoPol5 = new javax.swing.JLabel();
        txtKdBarang = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        sidePanel = new javax.swing.JPanel();
        pnlKeluar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnlBarma = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnlGudang = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnlHome = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblJudul = new javax.swing.JLabel();
        pnlLaporan = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        pnlBarba = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        closePanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        minimizePanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        tblDetServices = new javax.swing.JScrollPane();
        tblSparepart = new javax.swing.JTable();
        lblNoPol1 = new javax.swing.JLabel();
        cmbUrut = new javax.swing.JComboBox<>();
        btnEdit = new javax.swing.JButton();
        lblNoPol2 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();

        BarangUbah.setUndecorated(true);

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(30, 130, 234));
        jPanel3.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Sparepart/Gudang/Edit Sparepart");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel16)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(39, 39, 39))
        );

        lblNoPol3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol3.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol3.setText("Nama Sparepart");

        txtNamaBarang.setBackground(new java.awt.Color(255, 255, 255));
        txtNamaBarang.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNamaBarang.setForeground(new java.awt.Color(51, 51, 51));
        txtNamaBarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

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

        lblNoPol4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol4.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol4.setText("Kode Sparepart");

        txtHarga.setBackground(new java.awt.Color(255, 255, 255));
        txtHarga.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtHarga.setForeground(new java.awt.Color(51, 51, 51));
        txtHarga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblNoPol5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol5.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol5.setText("Harga Jual");

        txtKdBarang.setEditable(false);
        txtKdBarang.setBackground(new java.awt.Color(255, 255, 255));
        txtKdBarang.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtKdBarang.setForeground(new java.awt.Color(51, 51, 51));
        txtKdBarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        btnCancel.setBackground(new java.awt.Color(240, 240, 240));
        btnCancel.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
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
                .addGap(22, 22, 22)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNoPol3)
                            .addComponent(lblNoPol4)
                            .addComponent(lblNoPol5))
                        .addGap(49, 49, 49)
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(174, 174, 174)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BarangUbahLayout = new javax.swing.GroupLayout(BarangUbah.getContentPane());
        BarangUbah.getContentPane().setLayout(BarangUbahLayout);
        BarangUbahLayout.setHorizontalGroup(
            BarangUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BarangUbahLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        BarangUbahLayout.setVerticalGroup(
            BarangUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        sidePanel.setBackground(new java.awt.Color(0, 102, 204));
        sidePanel.setPreferredSize(new java.awt.Dimension(348, 411));

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
                .addContainerGap(175, Short.MAX_VALUE))
        );
        pnlBarmaLayout.setVerticalGroup(
            pnlBarmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBarmaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addContainerGap(19, Short.MAX_VALUE))
        );

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
                .addContainerGap(218, Short.MAX_VALUE))
        );
        pnlGudangLayout.setVerticalGroup(
            pnlGudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGudangLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
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
        jLabel7.setText("Sparepart/Gudang");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addContainerGap(407, Short.MAX_VALUE))
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

        tblSparepart.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblSparepart.setForeground(new java.awt.Color(0, 0, 0));
        tblSparepart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDetServices.setViewportView(tblSparepart);

        lblNoPol1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol1.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol1.setText("Urut Berdasarkan");

        cmbUrut.setBackground(new java.awt.Color(255, 255, 255));
        cmbUrut.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbUrut.setForeground(new java.awt.Color(51, 51, 51));
        cmbUrut.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID Sparepart", "Nama Sparepart", "Stok", "Harga Termurah", "Harga Termahal" }));
        cmbUrut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbUrutItemStateChanged(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(240, 240, 240));
        btnEdit.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(51, 51, 51));
        btnEdit.setText("Ubah");
        btnEdit.setBorder(null);
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });

        lblNoPol2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol2.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol2.setText("Cari Kode / Nama");

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtCari.setForeground(new java.awt.Color(51, 51, 51));
        txtCari.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addComponent(lblNoPol2)
                            .addGap(18, 18, 18)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNoPol1)
                            .addGap(18, 18, 18)
                            .addComponent(cmbUrut, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbUrut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoPol1)
                    .addComponent(lblNoPol2)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sidePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void pnlGudangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGudangMouseClicked
        // TODO add your handling code here:
        Done_Gudang gd = new Done_Gudang();
        gd.show();
    }//GEN-LAST:event_pnlGudangMouseClicked

    private void pnlHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHomeMouseClicked
        // TODO add your handling code here:
        dispose();
        HomeSparepart hk = new HomeSparepart();
        hk.show();
    }//GEN-LAST:event_pnlHomeMouseClicked

    private void pnlLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLaporanMouseClicked
        // TODO add your handling code here:
        Done_Laporan lp = new Done_Laporan();
        lp.show();
    }//GEN-LAST:event_pnlLaporanMouseClicked

    private void pnlBarbaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBarbaMouseClicked
        dispose();
        Done_BarangBaru ps = new Done_BarangBaru();
        ps.show();
    }//GEN-LAST:event_pnlBarbaMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        // TODO add your handling code here:
        klik();
        BarangUbah.show();
        BarangUbah.setSize(421, 433);
        BarangUbah.setLocationRelativeTo(null);
        txtNamaBarang.requestFocus();
    }//GEN-LAST:event_btnEditMouseClicked

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        cariData();
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnSubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseClicked
        // TODO add your handling code here:
        UbahData();
        BarangUbah.dispose();
    }//GEN-LAST:event_btnSubmitMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        // TODO add your handling code here:
        BarangUbah.dispose();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void cmbUrutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbUrutItemStateChanged
        // TODO add your handling code here:
        showData();
    }//GEN-LAST:event_cmbUrutItemStateChanged

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
            java.util.logging.Logger.getLogger(Done_Gudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Done_Gudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Done_Gudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Done_Gudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Done_Gudang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame BarangUbah;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel closePanel;
    private javax.swing.JComboBox<String> cmbUrut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblNoPol1;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblNoPol3;
    private javax.swing.JLabel lblNoPol4;
    private javax.swing.JLabel lblNoPol5;
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
    private javax.swing.JTable tblSparepart;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtKdBarang;
    private javax.swing.JTextField txtNamaBarang;
    // End of variables declaration//GEN-END:variables
}
