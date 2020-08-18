/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kasir;

import Class.DatabaseConnection;
import Class.Login;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class LaporanPemasukan extends javax.swing.JFrame {

    /**
     * Creates new form LaporanPemasukkanService
     */
    Connection koneksi;
    String TanggalAwal, TanggalAkhir, Tahun;

    public LaporanPemasukan() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setLocationRelativeTo(null);
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "fauzan", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        showData();
        setJDate();
    }

    //SELECT FROM DB
    public void showData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblPemasukan.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nomor Faktur", "ID Customer", "Total Service", "Total Sparepart", "Total Bayar"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null, query2;
        int No = 1;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT T_Faktur.Id_Faktur, T_Faktur.Id_Customer, T_Faktur.Total_Jasa, T_Faktur.Total_Sparepart, T_Faktur.Total_Bayar "
                    + "FROM T_Faktur "
                    + "WHERE STATUS = 'BERES'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Faktur = rs.getString("Id_Faktur");
                String Id_Customer = rs.getString("Id_Customer");
                String Total_Jasa = rs.getString("Total_Jasa");
                String Total_Sparepart = rs.getString("Total_Sparepart");
                String Total_Bayar = rs.getString("Total_Bayar");
                dtm.addRow(new String[]{"" + No, Id_Faktur, Id_Customer, Total_Jasa, Total_Sparepart, Total_Bayar});
                No = No + 1;
            }
            query2 = "SELECT SUM(T_Faktur.Total_Bayar) AS Total_Pemasukan,COUNT(Id_Faktur) AS BanyakService "
                    + "FROM T_Faktur "
                    + "WHERE STATUS = 'BERES'";
            ResultSet rs2 = stmt.executeQuery(query2);
            if (rs2.next()) {
                String Total_Pemasukan = rs2.getString("Total_Pemasukan");
                String BanyakService = rs2.getString("BanyakService");
                if (Total_Pemasukan == null) {
                    Total_Pemasukan = "0";
                }
                txtTotalPemasukan.setText(Total_Pemasukan);
                txtTotalService.setText(BanyakService);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblPemasukan.setModel(dtm);
    }

    public void cariData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblPemasukan.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nomor Faktur", "ID Customer", "Total Service", "Total Sparepart", "Total Bayar"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null, query2;
        int No = 1;
        String tanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tanggalLahir);
        String tglAwal = String.valueOf(fm.format(txtTglAwal.getDate()));
        String tglAkhir = String.valueOf(fm.format(txtTglAkhir.getDate()));
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT T_Faktur.Id_Faktur, T_Faktur.Id_Customer, T_Faktur.Total_Jasa, T_Faktur.Total_Sparepart, T_Faktur.Total_Bayar "
                    + "FROM T_Faktur "
                    + "WHERE STATUS = 'BERES' AND Tanggal BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Faktur = rs.getString("Id_Faktur");
                String Id_Customer = rs.getString("Id_Customer");
                String Total_Jasa = rs.getString("Total_Jasa");
                String Total_Sparepart = rs.getString("Total_Sparepart");
                String Total_Bayar = rs.getString("Total_Bayar");
                dtm.addRow(new String[]{"" + No, Id_Faktur, Id_Customer, Total_Jasa, Total_Sparepart, Total_Bayar});
                No = No + 1;
            }
            query2 = "SELECT SUM(T_Faktur.Total_Bayar) AS Total_Pemasukan,COUNT(Id_Faktur) AS BanyakService "
                    + "FROM T_Faktur "
                    + "WHERE STATUS = 'BERES' AND Tanggal BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "'";
            ResultSet rs2 = stmt.executeQuery(query2);
            if (rs2.next()) {
                String Total_Pemasukan = rs2.getString("Total_Pemasukan");
                String BanyakService = rs2.getString("BanyakService");
                if (Total_Pemasukan == null) {
                    Total_Pemasukan = "0";
                }
                txtTotalPemasukan.setText(Total_Pemasukan);
                txtTotalService.setText(BanyakService);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database" + ex);
        }
        tblPemasukan.setModel(dtm);
    }

    public void setJDate() {
        Calendar today = Calendar.getInstance();
        txtTglAwal.setDate(today.getTime());
        txtTglAkhir.setDate(today.getTime());
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
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPemasukan = new javax.swing.JTable();
        btnPrint1 = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtTotalPemasukan = new javax.swing.JLabel();
        txtTotalService = new javax.swing.JLabel();
        txtTglAwal = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        txtTglAkhir = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Laporan Service");

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel1.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Kasir/Laporan Service");

        javax.swing.GroupLayout PanelDirectoryLayout = new javax.swing.GroupLayout(PanelDirectory);
        PanelDirectory.setLayout(PanelDirectoryLayout);
        PanelDirectoryLayout.setHorizontalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectoryLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel8)
                .addContainerGap(478, Short.MAX_VALUE))
        );
        PanelDirectoryLayout.setVerticalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectoryLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(39, 39, 39))
        );

        tblPemasukan.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblPemasukan.setForeground(new java.awt.Color(0, 0, 0));
        tblPemasukan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "No Faktur", "ID Customer", "Total Service", "Total Sparepart", "Total Bayar"
            }
        ));
        jScrollPane2.setViewportView(tblPemasukan);

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

        btnCari.setBackground(new java.awt.Color(240, 240, 240));
        btnCari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCari.setForeground(new java.awt.Color(51, 51, 51));
        btnCari.setText("Cari");
        btnCari.setBorder(null);
        btnCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCariMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Total Pemasukkan :");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Total Service :");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Rp.");

        txtTotalPemasukan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalPemasukan.setForeground(new java.awt.Color(51, 51, 51));
        txtTotalPemasukan.setText("0");

        txtTotalService.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalService.setForeground(new java.awt.Color(51, 51, 51));
        txtTotalService.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalService.setText("0");

        txtTglAwal.setBackground(new java.awt.Color(255, 255, 255));
        txtTglAwal.setForeground(new java.awt.Color(51, 51, 51));

        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Tanggal Awal :");

        txtTglAkhir.setBackground(new java.awt.Color(255, 255, 255));
        txtTglAkhir.setForeground(new java.awt.Color(51, 51, 51));

        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Tanggal Akhir :");

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalService, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotalPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanel1Layout.createSequentialGroup()
                            .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(txtTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                    .addComponent(txtTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(PanelDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(9, 9, 9)
                        .addComponent(txtTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10)
                        .addGap(6, 6, 6)
                        .addComponent(txtTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTotalService))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19)
                            .addComponent(txtTotalPemasukan))))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
int Click = 0;
    private void btnPrint1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrint1MouseClicked
        String Berdasarkan = "Semua Pemasukkan", tglAwal = "", tglAkhir = "";
        try {
            //Koneksi Database
            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "fauzan", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
            //CETAK DATA
            HashMap parameter = new HashMap();
            //AMBIL FILE
            if (Click == 1) {
                String tanggalLahir = "yyyy-MM-dd";
                SimpleDateFormat fm = new SimpleDateFormat(tanggalLahir);
                tglAwal = String.valueOf(fm.format(txtTglAwal.getDate()));
                tglAkhir = String.valueOf(fm.format(txtTglAkhir.getDate()));
                Berdasarkan = "Tanggal";
            }
            parameter.put("bds", Berdasarkan);
            parameter.put("tglAwal", tglAwal);
            parameter.put("tglAkhir", tglAkhir);
            File file = new File("src/Report/LaporanPemasukkan.jasper");
            JasperReport jr = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameter, c);
            //AGAR TIDAK MENGCLOSE APLIKASi
            JasperViewer.viewReport(jp, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e);
        }
    }//GEN-LAST:event_btnPrint1MouseClicked

    private void btnCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCariMouseClicked
        cariData();
        Click = 1;
    }//GEN-LAST:event_btnCariMouseClicked

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
            java.util.logging.Logger.getLogger(LaporanPemasukan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaporanPemasukan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaporanPemasukan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaporanPemasukan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new LaporanPemasukan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnPrint1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JTable tblPemasukan;
    private com.toedter.calendar.JDateChooser txtTglAkhir;
    private com.toedter.calendar.JDateChooser txtTglAwal;
    private javax.swing.JLabel txtTotalPemasukan;
    private javax.swing.JLabel txtTotalService;
    // End of variables declaration//GEN-END:variables
}
