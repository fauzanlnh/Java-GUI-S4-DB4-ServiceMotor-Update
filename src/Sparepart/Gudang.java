/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sparepart;

import Class.DatabaseConnection;
import Class.Login;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class Gudang extends javax.swing.JFrame {

    /**
     * Creates new form Gudang
     */
    Connection koneksi;

    public Gudang() {
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
                query = "SELECT * FROM T_Jenis_Sparepart ORDER BY Id_Sparepart";
            } else if (cmbUrut.getSelectedIndex() == 1) {
                query = "SELECT * FROM T_Jenis_Sparepart ORDER BY Nama_Sparepart";
            } else if (cmbUrut.getSelectedIndex() == 2) {
                query = "SELECT * FROM T_Jenis_Sparepart ORDER BY Stok";
            } else if (cmbUrut.getSelectedIndex() == 3) {
                query = "SELECT * FROM T_Jenis_Sparepart ORDER BY Harga_Sparepart ASC";
            } else if (cmbUrut.getSelectedIndex() == 4) {
                query = "SELECT * FROM T_Jenis_Sparepart ORDER BY Harga_Sparepart DESC";
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
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
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
                query = "SELECT * FROM T_Jenis_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
                        + "%' OR Nama_Sparepart LIKE '%" + txtCari.getText() + "%' ORDER BY Id_Sparepart ";
            } else if (cmbUrut.getSelectedIndex() == 1) {
                query = "SELECT * FROM T_Jenis_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
                        + "%' OR Nama_Sparepart LIKE '%" + txtCari.getText() + "%' ORDER BY Nama_Sparepart";
            } else if (cmbUrut.getSelectedIndex() == 2) {
                query = "SELECT * FROM T_Jenis_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
                        + "%' OR Nama_Sparepart LIKE '%" + txtCari.getText() + "%' ORDER BY Stok";
            } else if (cmbUrut.getSelectedIndex() == 3) {
                query = "SELECT * FROM T_Jenis_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
                        + "%' OR Nama_Sparepart LIKE '%" + txtCari.getText() + "%' ORDER BY Harga_Sparepart ASC";
            } else if (cmbUrut.getSelectedIndex() == 4) {
                query = "SELECT * FROM T_Jenis_Sparepart WHERE Id_Sparepart LIKE '%" + txtCari.getText()
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
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
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
        String getStok = tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 3).toString();
        String getHarga = tblSparepart.getValueAt(tblSparepart.getSelectedRow(), 4).toString();

        txtKdBarang.setText(getKdSparepart);
        txtNamaBarang.setText(getNamaSparepart);
        txtStok.setText(getStok);
        txtHarga.setText(getHarga);
    }

    public void UbahData() {
        String KdBarang = txtKdBarang.getText().toUpperCase();
        String NamaBarang = txtNamaBarang.getText().toUpperCase();
        String Stok = txtStok.getText().toUpperCase();
        String HargaJual = txtHarga.getText().toUpperCase();
        try {
            if (!NamaBarang.equals("") && !HargaJual.equals("")) {
                Statement stmt = koneksi.createStatement();
                String query = "UPDATE T_Jenis_Sparepart SET Nama_Sparepart = '" + NamaBarang + "', Harga_Sparepart = '" + HargaJual + "', Stok = '" + Stok + "' WHERE Id_Sparepart = '" + KdBarang + "'";
                System.out.println(query);
                int berhasil = stmt.executeUpdate(query);
                if (berhasil == 1) {
                    JOptionPane.showMessageDialog(null, "DATA BERHASIL DIUBAH");
                    showData();
                } else {
                    JOptionPane.showMessageDialog(null, "DATA GAGAL DIUBAH");
                }
            } else if (NamaBarang.equals("")) {
                JOptionPane.showMessageDialog(null, "NAMA BARANG MASIH KOSONG");
                txtNamaBarang.requestFocus();
            } else if (HargaJual.equals("")) {
                JOptionPane.showMessageDialog(null, "HARGA JUAL MASIH KOSONG");
                txtHarga.requestFocus();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
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
        lblNoPol6 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        mainPanel2 = new javax.swing.JPanel();
        PanelDirectory = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        tblDetServices = new javax.swing.JScrollPane();
        tblSparepart = new javax.swing.JTable();
        lblNoPol1 = new javax.swing.JLabel();
        cmbUrut = new javax.swing.JComboBox<>();
        btnEdit = new javax.swing.JButton();
        lblNoPol2 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnPrint = new javax.swing.JButton();

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(30, 130, 234));
        jPanel3.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Sparepart/Ubah Sparepart");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel16)
                .addContainerGap(105, Short.MAX_VALUE))
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

        lblNoPol6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol6.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol6.setText("Stok");

        txtStok.setBackground(new java.awt.Color(255, 255, 255));
        txtStok.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtStok.setForeground(new java.awt.Color(51, 51, 51));
        txtStok.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(174, 174, 174)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNoPol3)
                            .addComponent(lblNoPol4)
                            .addComponent(lblNoPol5)
                            .addComponent(lblNoPol6))
                        .addGap(49, 49, 49)
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtStok, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtHarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Stok Barang");

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

        btnPrint.setBackground(new java.awt.Color(240, 240, 240));
        btnPrint.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(51, 51, 51));
        btnPrint.setText("Print");
        btnPrint.setBorder(null);
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mainPanel2Layout = new javax.swing.GroupLayout(mainPanel2);
        mainPanel2.setLayout(mainPanel2Layout);
        mainPanel2Layout.setHorizontalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(mainPanel2Layout.createSequentialGroup()
                            .addComponent(lblNoPol2)
                            .addGap(18, 18, 18)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNoPol1)
                            .addGap(18, 18, 18)
                            .addComponent(cmbUrut, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        mainPanel2Layout.setVerticalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbUrut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoPol1)
                    .addComponent(lblNoPol2)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
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

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        // TODO add your handling code here:
        klik();
        BarangUbah.show();
        BarangUbah.setSize(425, 453);
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
        showData();
        txtCari.setText("");
    }//GEN-LAST:event_cmbUrutItemStateChanged

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        // TODO add your handling code here:
        //EXPORT PDF
        try {
            //Koneksi Database
            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
            //CETAK DATA
            HashMap parameter = new HashMap();
            //AMBIL FILE
            File file = new File("src/Report/LaporanStok.jasper");
            JasperReport jr = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameter, c);
            //AGAR TIDAK MENGCLOSE APLIKASi
            JasperViewer.viewReport(jp, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e);
        }
    }//GEN-LAST:event_btnPrintMouseClicked

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
            java.util.logging.Logger.getLogger(Gudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gudang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame BarangUbah;
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox<String> cmbUrut;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblNoPol1;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblNoPol3;
    private javax.swing.JLabel lblNoPol4;
    private javax.swing.JLabel lblNoPol5;
    private javax.swing.JLabel lblNoPol6;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel mainPanel2;
    private javax.swing.JScrollPane tblDetServices;
    private javax.swing.JTable tblSparepart;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtKdBarang;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtStok;
    // End of variables declaration//GEN-END:variables
}
