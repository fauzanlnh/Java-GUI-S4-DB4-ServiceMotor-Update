/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Class.DatabaseConnection;
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
public class LaporanDataJasa extends javax.swing.JFrame {

    /**
     * Creates new form LaporanDataJasa
     */
    Connection koneksi;

    public LaporanDataJasa() {
        initComponents();
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        this.setLocationRelativeTo(null);
        showData();
    }

    public void showData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblJasa.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nama Jasa", "Harga Jasa", "Jenis Motor"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        int no = 1;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM T_Jenis_Jasa JOIN t_jenis_motor USING(Id_Jenis) ORDER BY Nama_Jasa";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Nama_Jasa = rs.getString("Nama_Jasa");
                String Harga_Jasa = rs.getString("Harga_Jasa");
                String Namaa_Jenis = rs.getString("Nama_Jenis");
                dtm.addRow(new String[]{"" + no, Nama_Jasa, Harga_Jasa, Namaa_Jenis});
                no++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblJasa.setModel(dtm);
    }

    public void CariData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblJasa.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nama Jasa", "Harga Jasa", "Jenis Motor"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        int no = 1;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM T_Jenis_Jasa JOIN t_jenis_motor USING(Id_Jenis) WHERE Nama_Jasa LIKE '%" + txtCari.getText() + "%' "
                    + "ORDER BY Nama_Jasa";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Nama_Jasa = rs.getString("Nama_Jasa");
                String Harga_Jasa = rs.getString("Harga_Jasa");
                String Namaa_Jenis = rs.getString("Nama_Jenis");
                dtm.addRow(new String[]{"" + no, Nama_Jasa, Harga_Jasa, Namaa_Jenis});
                no++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblJasa.setModel(dtm);
    }

    public void Move() {
        try {
            Statement stmt = koneksi.createStatement();
            String getKdMotor = "SELECT * FROM T_Jenis_Jasa WHERE Nama_Jasa = '" + tblJasa.getValueAt(tblJasa.getSelectedRow(), 1) + "'";
            ResultSet rs = stmt.executeQuery(getKdMotor);
            if (rs.next()) {
                txtIdJasa.setText(rs.getString("Id_Jasa"));
                txtNamaJasa1.setText(rs.getString("Nama_Jasa"));
                txtHargaJasa1.setText(rs.getString("Harga_Jasa"));
                cmbTipeMotor1.setSelectedIndex(rs.getInt("Id_Jenis"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void UbahData() {
        try {
            Statement stmt = koneksi.createStatement();
            String Update = "UPDATE T_Jenis_Jasa SET Nama_Jasa = '" + txtNamaJasa1.getText().toUpperCase() + "', "
                    + "Id_Jenis = '" + cmbTipeMotor1.getSelectedIndex() + "', Harga_Jasa = '" + txtHargaJasa1.getText() + "' "
                    + "WHERE Id_Jasa = '" + txtIdJasa.getText() + "'";
            int berhasil = stmt.executeUpdate(Update);
            if (berhasil > 0) {
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIUBAH");
                showData();
                UbahJasa.dispose();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void SimpanData() {
        try {
            Statement stmt = koneksi.createStatement();
            String Insert = "INSERT INTO T_Jenis_Jasa (Nama_Jasa, Id_Jenis, Harga_Jasa) VALUES "
                    + "('" + txtNamaJasa.getText().toUpperCase() + "', "
                    + " '" + cmbTipeMotor.getSelectedIndex() + "', "
                    + "'" + txtHargaJasa.getText().toUpperCase() + "' )";
            int berhasil = stmt.executeUpdate(Insert);
            if (berhasil > 0) {
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIMASUKKAn");
                showData();
                TambahJasa.dispose();
            }
        } catch (SQLException e) {
            System.out.println(e);
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

        TambahJasa = new javax.swing.JFrame();
        mainPanel4 = new javax.swing.JPanel();
        PanelDirectory3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblNoPol7 = new javax.swing.JLabel();
        txtNamaJasa = new javax.swing.JTextField();
        lblService2 = new javax.swing.JLabel();
        cmbTipeMotor = new javax.swing.JComboBox<>();
        btnSubmit2 = new javax.swing.JButton();
        btnCancel2 = new javax.swing.JButton();
        lblNoPol9 = new javax.swing.JLabel();
        txtHargaJasa = new javax.swing.JTextField();
        UbahJasa = new javax.swing.JFrame();
        mainPanel3 = new javax.swing.JPanel();
        PanelDirectory2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtIdJasa = new javax.swing.JTextField();
        lblNoPol4 = new javax.swing.JLabel();
        txtNamaJasa1 = new javax.swing.JTextField();
        lblService1 = new javax.swing.JLabel();
        cmbTipeMotor1 = new javax.swing.JComboBox<>();
        btnSubmit1 = new javax.swing.JButton();
        lblNoPol5 = new javax.swing.JLabel();
        btnCancel1 = new javax.swing.JButton();
        lblNoPol6 = new javax.swing.JLabel();
        txtHargaJasa1 = new javax.swing.JTextField();
        mainPanel1 = new javax.swing.JPanel();
        PanelDirectory = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblJasa = new javax.swing.JTable();
        btnPrint1 = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnTambahMotor = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        lblBulan = new javax.swing.JLabel();

        TambahJasa.setUndecorated(true);

        mainPanel4.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel4.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory3.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory3.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Admin/ Tambah Data Jasa");

        javax.swing.GroupLayout PanelDirectory3Layout = new javax.swing.GroupLayout(PanelDirectory3);
        PanelDirectory3.setLayout(PanelDirectory3Layout);
        PanelDirectory3Layout.setHorizontalGroup(
            PanelDirectory3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel11)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        PanelDirectory3Layout.setVerticalGroup(
            PanelDirectory3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectory3Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(39, 39, 39))
        );

        lblNoPol7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol7.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol7.setText("Nama Jasa");

        txtNamaJasa.setBackground(new java.awt.Color(255, 255, 255));
        txtNamaJasa.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNamaJasa.setForeground(new java.awt.Color(51, 51, 51));
        txtNamaJasa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblService2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService2.setForeground(new java.awt.Color(51, 51, 51));
        lblService2.setText("Jenis Motor");

        cmbTipeMotor.setBackground(new java.awt.Color(255, 255, 255));
        cmbTipeMotor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbTipeMotor.setForeground(new java.awt.Color(51, 51, 51));
        cmbTipeMotor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Gigi", "Matic", "Sport" }));

        btnSubmit2.setBackground(new java.awt.Color(240, 240, 240));
        btnSubmit2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnSubmit2.setForeground(new java.awt.Color(51, 51, 51));
        btnSubmit2.setText("Submit");
        btnSubmit2.setBorder(null);
        btnSubmit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmit2ActionPerformed(evt);
            }
        });

        btnCancel2.setBackground(new java.awt.Color(240, 240, 240));
        btnCancel2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnCancel2.setForeground(new java.awt.Color(51, 51, 51));
        btnCancel2.setText("Cancel");
        btnCancel2.setBorder(null);
        btnCancel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel2ActionPerformed(evt);
            }
        });

        lblNoPol9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol9.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol9.setText("Harga  Jasa");

        txtHargaJasa.setBackground(new java.awt.Color(255, 255, 255));
        txtHargaJasa.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtHargaJasa.setForeground(new java.awt.Color(51, 51, 51));
        txtHargaJasa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        javax.swing.GroupLayout mainPanel4Layout = new javax.swing.GroupLayout(mainPanel4);
        mainPanel4.setLayout(mainPanel4Layout);
        mainPanel4Layout.setHorizontalGroup(
            mainPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel4Layout.createSequentialGroup()
                .addGroup(mainPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelDirectory3, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(mainPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel4Layout.createSequentialGroup()
                                .addComponent(lblService2)
                                .addGap(18, 18, 18)
                                .addGroup(mainPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanel4Layout.createSequentialGroup()
                                        .addComponent(btnCancel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSubmit2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cmbTipeMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(mainPanel4Layout.createSequentialGroup()
                                .addComponent(lblNoPol9)
                                .addGap(18, 18, 18)
                                .addComponent(txtHargaJasa, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel4Layout.createSequentialGroup()
                                .addComponent(lblNoPol7)
                                .addGap(22, 22, 22)
                                .addComponent(txtNamaJasa, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
        mainPanel4Layout.setVerticalGroup(
            mainPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelDirectory3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(mainPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaJasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(mainPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaJasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblService2)
                    .addComponent(cmbTipeMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(mainPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout TambahJasaLayout = new javax.swing.GroupLayout(TambahJasa.getContentPane());
        TambahJasa.getContentPane().setLayout(TambahJasaLayout);
        TambahJasaLayout.setHorizontalGroup(
            TambahJasaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TambahJasaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        TambahJasaLayout.setVerticalGroup(
            TambahJasaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TambahJasaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        UbahJasa.setUndecorated(true);

        mainPanel3.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel3.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory2.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory2.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Admin/ Ubah Data Jasa");

        javax.swing.GroupLayout PanelDirectory2Layout = new javax.swing.GroupLayout(PanelDirectory2);
        PanelDirectory2.setLayout(PanelDirectory2Layout);
        PanelDirectory2Layout.setHorizontalGroup(
            PanelDirectory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel10)
                .addContainerGap(125, Short.MAX_VALUE))
        );
        PanelDirectory2Layout.setVerticalGroup(
            PanelDirectory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectory2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(39, 39, 39))
        );

        txtIdJasa.setEditable(false);
        txtIdJasa.setBackground(new java.awt.Color(255, 255, 255));
        txtIdJasa.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtIdJasa.setForeground(new java.awt.Color(51, 51, 51));
        txtIdJasa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblNoPol4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol4.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol4.setText("Nama Jasa");

        txtNamaJasa1.setBackground(new java.awt.Color(255, 255, 255));
        txtNamaJasa1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNamaJasa1.setForeground(new java.awt.Color(51, 51, 51));
        txtNamaJasa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblService1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService1.setForeground(new java.awt.Color(51, 51, 51));
        lblService1.setText("Jenis Motor");

        cmbTipeMotor1.setBackground(new java.awt.Color(255, 255, 255));
        cmbTipeMotor1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbTipeMotor1.setForeground(new java.awt.Color(51, 51, 51));
        cmbTipeMotor1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Gigi", "Matic", "Sport" }));

        btnSubmit1.setBackground(new java.awt.Color(240, 240, 240));
        btnSubmit1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnSubmit1.setForeground(new java.awt.Color(51, 51, 51));
        btnSubmit1.setText("Submit");
        btnSubmit1.setBorder(null);
        btnSubmit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmit1ActionPerformed(evt);
            }
        });

        lblNoPol5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol5.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol5.setText("Kode Jasa");

        btnCancel1.setBackground(new java.awt.Color(240, 240, 240));
        btnCancel1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnCancel1.setForeground(new java.awt.Color(51, 51, 51));
        btnCancel1.setText("Cancel");
        btnCancel1.setBorder(null);
        btnCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel1ActionPerformed(evt);
            }
        });

        lblNoPol6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol6.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol6.setText("Harga  Jasa");

        txtHargaJasa1.setBackground(new java.awt.Color(255, 255, 255));
        txtHargaJasa1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtHargaJasa1.setForeground(new java.awt.Color(51, 51, 51));
        txtHargaJasa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        javax.swing.GroupLayout mainPanel3Layout = new javax.swing.GroupLayout(mainPanel3);
        mainPanel3.setLayout(mainPanel3Layout);
        mainPanel3Layout.setHorizontalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelDirectory2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblService1)
                                .addGap(18, 18, 18)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanel3Layout.createSequentialGroup()
                                        .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSubmit1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cmbTipeMotor1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(lblNoPol6)
                                .addGap(18, 18, 18)
                                .addComponent(txtHargaJasa1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNoPol5)
                                    .addComponent(lblNoPol4))
                                .addGap(22, 22, 22)
                                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNamaJasa1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                    .addComponent(txtIdJasa))))))
                .addGap(0, 0, 0))
        );
        mainPanel3Layout.setVerticalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelDirectory2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdJasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoPol5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaJasa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaJasa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblService1)
                    .addComponent(cmbTipeMotor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout UbahJasaLayout = new javax.swing.GroupLayout(UbahJasa.getContentPane());
        UbahJasa.getContentPane().setLayout(UbahJasaLayout);
        UbahJasaLayout.setHorizontalGroup(
            UbahJasaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UbahJasaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        UbahJasaLayout.setVerticalGroup(
            UbahJasaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UbahJasaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel1.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Admin/Data Jenis Jasa");

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

        tblJasa.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblJasa.setForeground(new java.awt.Color(0, 0, 0));
        tblJasa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nama Jasa", "Harga Jasa", "Jenis Motor"
            }
        ));
        jScrollPane2.setViewportView(tblJasa);

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

        btnTambahMotor.setBackground(new java.awt.Color(240, 240, 240));
        btnTambahMotor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTambahMotor.setForeground(new java.awt.Color(51, 51, 51));
        btnTambahMotor.setText("Tambah");
        btnTambahMotor.setBorder(null);
        btnTambahMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahMotorActionPerformed(evt);
            }
        });

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtCari.setForeground(new java.awt.Color(51, 51, 51));
        txtCari.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        lblBulan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBulan.setForeground(new java.awt.Color(51, 51, 51));
        lblBulan.setText("Tipe Jasa");

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(mainPanel1Layout.createSequentialGroup()
                            .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblBulan)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTambahMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTambahMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addComponent(lblBulan)
                        .addGap(10, 10, 10)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrint1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrint1MouseClicked
        //EXPORT PDF
        try {
            //Koneksi Database
            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
            //CETAK DATA
            HashMap parameter = new HashMap();
            //AMBIL FILE
            File file = new File("src/Report/LaporanDataJasa.jasper");
            JasperReport jr = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameter, c);
            //AGAR TIDAK MENGCLOSE APLIKASi
            JasperViewer.viewReport(jp, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e);
        }
    }//GEN-LAST:event_btnPrint1MouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        Move();
        UbahJasa.show();
        UbahJasa.setSize(410, 410);
        UbahJasa.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnTambahMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahMotorActionPerformed
        // TODO add your handling code here:
        TambahJasa.show();
        TambahJasa.setSize(410, 360);
        TambahJasa.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnTambahMotorActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        if (!txtCari.getText().equals("")) {
            CariData();
        } else if (txtCari.getText().equals("")) {
            showData();
        }
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnSubmit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmit1ActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Yakin Akan Merubah Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            UbahData();
        }
    }//GEN-LAST:event_btnSubmit1ActionPerformed

    private void btnCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel1ActionPerformed
        // TODO add your handling code here:
        UbahJasa.dispose();
    }//GEN-LAST:event_btnCancel1ActionPerformed

    private void btnSubmit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmit2ActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Yakin Akan Menyimpan Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            SimpanData();
        }
    }//GEN-LAST:event_btnSubmit2ActionPerformed

    private void btnCancel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel2ActionPerformed
        // TODO add your handling code here:
        TambahJasa.dispose();
    }//GEN-LAST:event_btnCancel2ActionPerformed

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
            java.util.logging.Logger.getLogger(LaporanDataJasa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaporanDataJasa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaporanDataJasa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaporanDataJasa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LaporanDataJasa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JPanel PanelDirectory2;
    private javax.swing.JPanel PanelDirectory3;
    private javax.swing.JFrame TambahJasa;
    private javax.swing.JFrame UbahJasa;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JButton btnCancel2;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint1;
    private javax.swing.JButton btnSubmit1;
    private javax.swing.JButton btnSubmit2;
    private javax.swing.JButton btnTambahMotor;
    private javax.swing.JComboBox<String> cmbTipeMotor;
    private javax.swing.JComboBox<String> cmbTipeMotor1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBulan;
    private javax.swing.JLabel lblNoPol4;
    private javax.swing.JLabel lblNoPol5;
    private javax.swing.JLabel lblNoPol6;
    private javax.swing.JLabel lblNoPol7;
    private javax.swing.JLabel lblNoPol9;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblService2;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel mainPanel3;
    private javax.swing.JPanel mainPanel4;
    private javax.swing.JTable tblJasa;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHargaJasa;
    private javax.swing.JTextField txtHargaJasa1;
    private javax.swing.JTextField txtIdJasa;
    private javax.swing.JTextField txtNamaJasa;
    private javax.swing.JTextField txtNamaJasa1;
    // End of variables declaration//GEN-END:variables
}
