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
public class LaporanDataMotor extends javax.swing.JFrame {

    /**
     * Creates new form LaporanDataMotor
     */
    Connection koneksi;

    public LaporanDataMotor() {
        initComponents();
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        this.setLocationRelativeTo(null);
        showData();
    }

    public void showData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblMotor.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nama Tipe", "Jenis Motor"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        int no = 1;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM T_Tipe JOIN t_jenis_motor USING(Id_Jenis) ORDER BY Nama_Tipe";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Nama_Tipe = rs.getString("Nama_Tipe");
                String Namaa_Jenis = rs.getString("Nama_Jenis");
                dtm.addRow(new String[]{"" + no, Nama_Tipe, Namaa_Jenis});
                no++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblMotor.setModel(dtm);
    }

    public void CariData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblMotor.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nama Tipe", "Jenis Motor"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null;
        int no = 1;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM T_Tipe JOIN t_jenis_motor USING(Id_Jenis) WHERE Nama_Tipe LIKE '%" + txtCari.getText() + "%' "
                    + "ORDER BY Nama_Tipe";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Nama_Tipe = rs.getString("Nama_Tipe");
                String Namaa_Jenis = rs.getString("Nama_Jenis");
                dtm.addRow(new String[]{"" + no, Nama_Tipe, Namaa_Jenis});
                no++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblMotor.setModel(dtm);
    }

    public void Move() {
        try {
            Statement stmt = koneksi.createStatement();
            String getKdMotor = "SELECT * FROM T_Tipe WHERE Nama_Tipe = '" + tblMotor.getValueAt(tblMotor.getSelectedRow(), 1) + "'";
            ResultSet rs = stmt.executeQuery(getKdMotor);
            if (rs.next()) {
                txtIdTipe1.setText(rs.getString("Id_Tipe"));
                txtNamaTipe1.setText(rs.getString("Nama_Tipe"));
                cmbTipeMotor1.setSelectedIndex(rs.getInt("Id_Jenis"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void UbahData() {
        try {
            Statement stmt = koneksi.createStatement();
            String Update = "UPDATE T_Tipe SET Nama_Tipe = '" + txtNamaTipe1.getText().toUpperCase() + "', "
                    + "Id_Jenis = '" + cmbTipeMotor1.getSelectedIndex() + "' WHERE Id_Tipe = '" + txtIdTipe1.getText() + "'";
            int berhasil = stmt.executeUpdate(Update);
            if (berhasil > 0) {
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIUBAH");
                showData();
                UbahMotor.dispose();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void SimpanData() {
        try {
            Statement stmt = koneksi.createStatement();
            String Insert = "INSERT INTO T_Tipe (Nama_Tipe, Id_Jenis) VALUES ('" + txtNamaTipe.getText().toUpperCase() + "', "
                    + " '" + cmbTipeMotor.getSelectedIndex() + "')";
            int berhasil = stmt.executeUpdate(Insert);
            if (berhasil > 0) {
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIMASUKKAn");
                showData();
                TambahMotor.dispose();
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

        TambahMotor = new javax.swing.JFrame();
        mainPanel2 = new javax.swing.JPanel();
        PanelDirectory1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblNoPol2 = new javax.swing.JLabel();
        txtNamaTipe = new javax.swing.JTextField();
        lblService = new javax.swing.JLabel();
        cmbTipeMotor = new javax.swing.JComboBox<>();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        UbahMotor = new javax.swing.JFrame();
        mainPanel3 = new javax.swing.JPanel();
        PanelDirectory2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtIdTipe1 = new javax.swing.JTextField();
        lblNoPol4 = new javax.swing.JLabel();
        txtNamaTipe1 = new javax.swing.JTextField();
        lblService1 = new javax.swing.JLabel();
        cmbTipeMotor1 = new javax.swing.JComboBox<>();
        btnSubmit1 = new javax.swing.JButton();
        lblNoPol5 = new javax.swing.JLabel();
        btnCancel1 = new javax.swing.JButton();
        mainPanel1 = new javax.swing.JPanel();
        PanelDirectory = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMotor = new javax.swing.JTable();
        btnPrint1 = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnTambahMotor = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        lblBulan = new javax.swing.JLabel();

        TambahMotor.setUndecorated(true);

        mainPanel2.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel2.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory1.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory1.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Admin/ Tambah Tipe Motor");

        javax.swing.GroupLayout PanelDirectory1Layout = new javax.swing.GroupLayout(PanelDirectory1);
        PanelDirectory1.setLayout(PanelDirectory1Layout);
        PanelDirectory1Layout.setHorizontalGroup(
            PanelDirectory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        PanelDirectory1Layout.setVerticalGroup(
            PanelDirectory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectory1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(39, 39, 39))
        );

        lblNoPol2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol2.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol2.setText("Nama Tipe");

        txtNamaTipe.setBackground(new java.awt.Color(255, 255, 255));
        txtNamaTipe.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNamaTipe.setForeground(new java.awt.Color(51, 51, 51));
        txtNamaTipe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblService.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService.setForeground(new java.awt.Color(51, 51, 51));
        lblService.setText("Jenis Motor");

        cmbTipeMotor.setBackground(new java.awt.Color(255, 255, 255));
        cmbTipeMotor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbTipeMotor.setForeground(new java.awt.Color(51, 51, 51));
        cmbTipeMotor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Gigi", "Matic", "Sport" }));

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

        btnCancel.setBackground(new java.awt.Color(240, 240, 240));
        btnCancel.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(51, 51, 51));
        btnCancel.setText("Cancel");
        btnCancel.setBorder(null);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanel2Layout = new javax.swing.GroupLayout(mainPanel2);
        mainPanel2.setLayout(mainPanel2Layout);
        mainPanel2Layout.setHorizontalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNoPol2)
                            .addComponent(lblService))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel2Layout.createSequentialGroup()
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNamaTipe, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTipeMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(PanelDirectory1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainPanel2Layout.setVerticalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelDirectory1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaTipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblService)
                    .addComponent(cmbTipeMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout TambahMotorLayout = new javax.swing.GroupLayout(TambahMotor.getContentPane());
        TambahMotor.getContentPane().setLayout(TambahMotorLayout);
        TambahMotorLayout.setHorizontalGroup(
            TambahMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TambahMotorLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        TambahMotorLayout.setVerticalGroup(
            TambahMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TambahMotorLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        UbahMotor.setUndecorated(true);

        mainPanel3.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel3.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory2.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory2.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Admin/ Ubah Tipe Motor");

        javax.swing.GroupLayout PanelDirectory2Layout = new javax.swing.GroupLayout(PanelDirectory2);
        PanelDirectory2.setLayout(PanelDirectory2Layout);
        PanelDirectory2Layout.setHorizontalGroup(
            PanelDirectory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel10)
                .addContainerGap(105, Short.MAX_VALUE))
        );
        PanelDirectory2Layout.setVerticalGroup(
            PanelDirectory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectory2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(39, 39, 39))
        );

        txtIdTipe1.setEditable(false);
        txtIdTipe1.setBackground(new java.awt.Color(255, 255, 255));
        txtIdTipe1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtIdTipe1.setForeground(new java.awt.Color(51, 51, 51));
        txtIdTipe1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblNoPol4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol4.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol4.setText("Nama Tipe");

        txtNamaTipe1.setBackground(new java.awt.Color(255, 255, 255));
        txtNamaTipe1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNamaTipe1.setForeground(new java.awt.Color(51, 51, 51));
        txtNamaTipe1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

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
        lblNoPol5.setText("Kode Tipe");

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

        javax.swing.GroupLayout mainPanel3Layout = new javax.swing.GroupLayout(mainPanel3);
        mainPanel3.setLayout(mainPanel3Layout);
        mainPanel3Layout.setHorizontalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNoPol4)
                            .addComponent(lblService1)
                            .addComponent(lblNoPol5))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel3Layout.createSequentialGroup()
                                .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSubmit1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtIdTipe1)
                                .addComponent(txtNamaTipe1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbTipeMotor1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(PanelDirectory2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainPanel3Layout.setVerticalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelDirectory2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdTipe1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoPol5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaTipe1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblService1)
                    .addComponent(cmbTipeMotor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout UbahMotorLayout = new javax.swing.GroupLayout(UbahMotor.getContentPane());
        UbahMotor.getContentPane().setLayout(UbahMotorLayout);
        UbahMotorLayout.setHorizontalGroup(
            UbahMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UbahMotorLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        UbahMotorLayout.setVerticalGroup(
            UbahMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UbahMotorLayout.createSequentialGroup()
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
        jLabel8.setText("Admin/Data Jenis Motor");

        javax.swing.GroupLayout PanelDirectoryLayout = new javax.swing.GroupLayout(PanelDirectory);
        PanelDirectory.setLayout(PanelDirectoryLayout);
        PanelDirectoryLayout.setHorizontalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectoryLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel8)
                .addContainerGap(204, Short.MAX_VALUE))
        );
        PanelDirectoryLayout.setVerticalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectoryLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(39, 39, 39))
        );

        tblMotor.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblMotor.setForeground(new java.awt.Color(0, 0, 0));
        tblMotor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Tipe", "Nama Tipe", "Jenis Motor"
            }
        ));
        jScrollPane2.setViewportView(tblMotor);

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
        lblBulan.setText("Tipe Motor");

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
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
            File file = new File("src/Report/LaporanDataMotor.jasper");
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
        UbahMotor.show();
        UbahMotor.setSize(410, 410);
        UbahMotor.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnTambahMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahMotorActionPerformed
        // TODO add your handling code here:
        TambahMotor.show();
        TambahMotor.setSize(410, 360);
        TambahMotor.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnTambahMotorActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        if (!txtCari.getText().equals("")) {
            CariData();
        } else if (txtCari.getText().equals("")) {
            showData();
        }
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        SimpanData();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Yakin Akan Memasukkan Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            TambahMotor.dispose();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSubmit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmit1ActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Yakin Akan Merubah Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            UbahData();
        }
    }//GEN-LAST:event_btnSubmit1ActionPerformed

    private void btnCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel1ActionPerformed
        // TODO add your handling code here:
        UbahMotor.dispose();
    }//GEN-LAST:event_btnCancel1ActionPerformed

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
            java.util.logging.Logger.getLogger(LaporanDataMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaporanDataMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaporanDataMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaporanDataMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LaporanDataMotor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JPanel PanelDirectory1;
    private javax.swing.JPanel PanelDirectory2;
    private javax.swing.JFrame TambahMotor;
    private javax.swing.JFrame UbahMotor;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint1;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnSubmit1;
    private javax.swing.JButton btnTambahMotor;
    private javax.swing.JComboBox<String> cmbTipeMotor;
    private javax.swing.JComboBox<String> cmbTipeMotor1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBulan;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblNoPol4;
    private javax.swing.JLabel lblNoPol5;
    private javax.swing.JLabel lblService;
    private javax.swing.JLabel lblService1;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel mainPanel2;
    private javax.swing.JPanel mainPanel3;
    private javax.swing.JTable tblMotor;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtIdTipe1;
    private javax.swing.JTextField txtNamaTipe;
    private javax.swing.JTextField txtNamaTipe1;
    // End of variables declaration//GEN-END:variables
}
