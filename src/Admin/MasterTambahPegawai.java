/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Class.DatabaseConnection;
import Class.Login;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Fauzanlh
 */
public class MasterTambahPegawai extends javax.swing.JFrame {

    /**
     * Creates new form MasterGantiPassword
     */
    Connection koneksi;

    public MasterTambahPegawai() {
        initComponents();
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        SetIdPegawai();
        txtAlamat.setLineWrap(true);
        txtAlamat.setWrapStyleWord(true);
    }

    public void ClearFormTambah() {
        txtIdPegawai.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        cmbBagian.setSelectedIndex(0);
        txtNoTelp.setText("");
        txtUsernameBaru.setText("");
        txtPassword1.setText("");
        SetIdPegawai();
    }

    public void SetUsername() {
        String Nama = txtNama.getText();
        String UserBaru[] = Nama.split(" ");
        String Jumlah = null;
        try {
            Statement stmt = koneksi.createStatement();
            String SelectUser = "SELECT COUNT(Username) AS Banyak FROM T_Login WHERE Username = '" + UserBaru[0] + "'";
            ResultSet rs = stmt.executeQuery(SelectUser);
            if (rs.next()) {
                Jumlah = rs.getString("Banyak");
                if (Jumlah.equals("0")) {
                    Jumlah = "";
                }
            }
            txtUsernameBaru.setText(UserBaru[0] + Jumlah);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void SetIdPegawai() {
        String Bagian = cmbBagian.getSelectedItem().toString();
        int IdPegawaiTerakhir = 0;
        int IdPegawaiBaru = 1;
        try {
            Statement stmt = koneksi.createStatement();
            String SelectIdPegawai = "SELECT * FROM T_PEGAWAI WHERE Bagian = '" + Bagian + "' ORDER BY Id_Pegawai DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(SelectIdPegawai);
            if (rs.next()) {
                IdPegawaiTerakhir = rs.getInt("Id_Pegawai");
                IdPegawaiBaru = IdPegawaiBaru + IdPegawaiTerakhir;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        txtIdPegawai.setText("" + IdPegawaiBaru);
    }

    public void TambahPegawai() {
        String IdPegawai = txtIdPegawai.getText().toUpperCase();
        String Nama = txtNama.getText().toUpperCase();
        String Alamat = txtAlamat.getText().toUpperCase();
        String Bagian = cmbBagian.getSelectedItem().toString().toUpperCase();
        String NoTelp = txtNoTelp.getText().toUpperCase();
        String User = txtUsernameBaru.getText().toUpperCase();
        String Pass = txtPassword1.getText().toUpperCase();
        try {
            Statement stmt = koneksi.createStatement();
            if (!IdPegawai.equals("") && !Nama.equals("") && !Alamat.equals("") && !NoTelp.equals("") && !Bagian.equals("-") && !User.equals("") && !Pass.equals("")) {
                String TambahPegawai = "INSERT INTO T_Pegawai VALUES ('" + IdPegawai + "','" + Nama + "','" + Alamat + "','" + NoTelp + "','" + Bagian + "')";
                String TambahLogin = "INSERT INTO T_Login VALUES ('" + User + "', '" + Pass + "','" + Bagian + "','" + IdPegawai + "')";
                int BerhasilTPegawai = stmt.executeUpdate(TambahPegawai);
                int BerhasilTLogin = stmt.executeUpdate(TambahLogin);
                if (BerhasilTPegawai == 1 && BerhasilTLogin == 1) {
                    System.out.println("INSERT TABEL PEGAWAI");
                    System.out.println(TambahPegawai);
                    System.out.println("INSERT TABEL LOGIN");
                    System.out.println(TambahLogin);
                    JOptionPane.showMessageDialog(null, "DATA PEGAWAI BERHASIL DITAMBAHKAN");
                    ClearFormTambah();
                } else if (BerhasilTPegawai == 0) {
                    System.out.println(TambahPegawai);
                    JOptionPane.showMessageDialog(null, "DATA PEGAWAI GAGAL DITAMBAHKAN");
                } else if (BerhasilTLogin == 0) {
                    System.out.println(TambahLogin);
                    JOptionPane.showMessageDialog(null, "DATA PEGAWAI GAGAL DITAMBAHKAN");
                }
            } else {
                if (IdPegawai.equals("")) {
                    JOptionPane.showMessageDialog(null, "ID PEGAWAI HARUS DIISI");
                    txtIdPegawai.requestFocus();
                } else if (Nama.equals("")) {
                    JOptionPane.showMessageDialog(null, "NAMA PEGAWAI HARUS DIISI");
                    txtIdPegawai.requestFocus();
                } else if (Alamat.equals("")) {
                    JOptionPane.showMessageDialog(null, "ALAMAT HARUS DIISI");
                    txtAlamat.requestFocus();
                } else if (Bagian.equals("-")) {
                    JOptionPane.showMessageDialog(null, "BAGIAN HARUS DIISI");
                    cmbBagian.requestFocus();
                } else if (NoTelp.equals("")) {
                    JOptionPane.showMessageDialog(null, "NO TELP HARUS DIISI");
                    txtNoTelp.requestFocus();
                } else if (User.equals("")) {
                    JOptionPane.showMessageDialog(null, "USERNAME HARUS DIISI");
                    txtUsernameBaru.requestFocus();
                } else if (Pass.equals("")) {
                    JOptionPane.showMessageDialog(null, "PASSWORD HARUS DIISI");
                    txtPassword1.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "FORM HARUS DI ISI");
                    txtIdPegawai.requestFocus();
                }
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

        mainPanel1 = new javax.swing.JPanel();
        PanelDirectory = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        Form1 = new javax.swing.JPanel();
        lblNoPol3 = new javax.swing.JLabel();
        txtIdPegawai = new javax.swing.JTextField();
        txtPassword1 = new javax.swing.JPasswordField();
        lblNoPol4 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnClearPegawai = new javax.swing.JButton();
        lblNoPol5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        lblNoPol6 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        lblNoPol7 = new javax.swing.JLabel();
        txtNoTelp = new javax.swing.JTextField();
        lblNoPol9 = new javax.swing.JLabel();
        txtUsernameBaru = new javax.swing.JTextField();
        lblNoPol10 = new javax.swing.JLabel();
        cmbBagian = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tambah Pegawai");

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel1.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Master/Tambah Pegawai");

        javax.swing.GroupLayout PanelDirectoryLayout = new javax.swing.GroupLayout(PanelDirectory);
        PanelDirectory.setLayout(PanelDirectoryLayout);
        PanelDirectoryLayout.setHorizontalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectoryLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDirectoryLayout.setVerticalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectoryLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(39, 39, 39))
        );

        Form1.setBackground(new java.awt.Color(255, 255, 255));
        Form1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Pegawai", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16), new java.awt.Color(51, 51, 51))); // NOI18N
        Form1.setForeground(new java.awt.Color(51, 51, 51));
        Form1.setRequestFocusEnabled(false);

        lblNoPol3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol3.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol3.setText("ID Pegawai");

        txtIdPegawai.setEditable(false);
        txtIdPegawai.setBackground(new java.awt.Color(255, 255, 255));
        txtIdPegawai.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        txtIdPegawai.setForeground(new java.awt.Color(51, 51, 51));
        txtIdPegawai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtPassword1.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        txtPassword1.setForeground(new java.awt.Color(51, 51, 51));
        txtPassword1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNoPol4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol4.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol4.setText("Password");

        btnSimpan.setBackground(new java.awt.Color(240, 240, 240));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(51, 51, 51));
        btnSimpan.setText("Simpan");
        btnSimpan.setBorder(null);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnClearPegawai.setBackground(new java.awt.Color(240, 240, 240));
        btnClearPegawai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnClearPegawai.setForeground(new java.awt.Color(51, 51, 51));
        btnClearPegawai.setText("Clear");
        btnClearPegawai.setBorder(null);
        btnClearPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPegawaiActionPerformed(evt);
            }
        });

        lblNoPol5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol5.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol5.setText("Alamat");

        txtAlamat.setBackground(new java.awt.Color(255, 255, 255));
        txtAlamat.setColumns(20);
        txtAlamat.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        lblNoPol6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol6.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol6.setText("Nama");

        txtNama.setBackground(new java.awt.Color(255, 255, 255));
        txtNama.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        txtNama.setForeground(new java.awt.Color(51, 51, 51));
        txtNama.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamaKeyReleased(evt);
            }
        });

        lblNoPol7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol7.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol7.setText("No Telp");

        txtNoTelp.setBackground(new java.awt.Color(255, 255, 255));
        txtNoTelp.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        txtNoTelp.setForeground(new java.awt.Color(51, 51, 51));
        txtNoTelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblNoPol9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol9.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol9.setText("Username");

        txtUsernameBaru.setBackground(new java.awt.Color(255, 255, 255));
        txtUsernameBaru.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        txtUsernameBaru.setForeground(new java.awt.Color(51, 51, 51));
        txtUsernameBaru.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblNoPol10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoPol10.setForeground(new java.awt.Color(51, 51, 51));
        lblNoPol10.setText("Bagian");

        cmbBagian.setBackground(new java.awt.Color(255, 255, 255));
        cmbBagian.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbBagian.setForeground(new java.awt.Color(51, 51, 51));
        cmbBagian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "KASIR", "SPAREPART", "TEKNISI" }));
        cmbBagian.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBagianItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout Form1Layout = new javax.swing.GroupLayout(Form1);
        Form1.setLayout(Form1Layout);
        Form1Layout.setHorizontalGroup(
            Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Form1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Form1Layout.createSequentialGroup()
                        .addComponent(lblNoPol5)
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Form1Layout.createSequentialGroup()
                            .addComponent(lblNoPol6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Form1Layout.createSequentialGroup()
                                .addComponent(btnClearPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Form1Layout.createSequentialGroup()
                                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNoPol3)
                                    .addComponent(lblNoPol4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(Form1Layout.createSequentialGroup()
                                        .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtUsernameBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNoTelp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(5, 5, 5))
                                    .addComponent(cmbBagian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdPegawai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(lblNoPol7)
                    .addComponent(lblNoPol9)
                    .addComponent(lblNoPol10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Form1Layout.setVerticalGroup(
            Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Form1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol3)
                    .addComponent(txtIdPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol6)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNoPol5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoPol7))
                .addGap(7, 7, 7)
                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol10)
                    .addComponent(cmbBagian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsernameBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoPol9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoPol4))
                .addGap(18, 18, 18)
                .addGroup(Form1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(Form1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(195, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(Form1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        TambahPegawai();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnClearPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPegawaiActionPerformed
        ClearFormTambah();
    }//GEN-LAST:event_btnClearPegawaiActionPerformed

    private void txtNamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyReleased
        SetUsername();
    }//GEN-LAST:event_txtNamaKeyReleased

    private void cmbBagianItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBagianItemStateChanged
        SetIdPegawai();
    }//GEN-LAST:event_cmbBagianItemStateChanged

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
            java.util.logging.Logger.getLogger(MasterTambahPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MasterTambahPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MasterTambahPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MasterTambahPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MasterTambahPegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Form1;
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JButton btnClearPegawai;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbBagian;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNoPol10;
    private javax.swing.JLabel lblNoPol3;
    private javax.swing.JLabel lblNoPol4;
    private javax.swing.JLabel lblNoPol5;
    private javax.swing.JLabel lblNoPol6;
    private javax.swing.JLabel lblNoPol7;
    private javax.swing.JLabel lblNoPol9;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtIdPegawai;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoTelp;
    private javax.swing.JPasswordField txtPassword1;
    private javax.swing.JTextField txtUsernameBaru;
    // End of variables declaration//GEN-END:variables
}
