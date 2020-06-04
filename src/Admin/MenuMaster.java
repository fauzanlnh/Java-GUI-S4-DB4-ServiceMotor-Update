/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Kasir.*;
import Class.Login;
import Class.LoginSession;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author Fauzanlh
 */
public class MenuMaster extends javax.swing.JFrame {

    public MenuMaster() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * Creates new form HomeKasir
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        DirectoryPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        closePanel = new javax.swing.JPanel();
        txtClose = new javax.swing.JLabel();
        minimizePanel = new javax.swing.JPanel();
        txtMinimize = new javax.swing.JLabel();
        pnlHome = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlTambahPegawai = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        pnlGantiPassword = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setPreferredSize(new java.awt.Dimension(930, 751));

        DirectoryPanel.setBackground(new java.awt.Color(30, 130, 234));
        DirectoryPanel.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 72)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("|");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/SS_Support.png"))); // NOI18N
        jLabel10.setText("    Service Motor");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Master/");

        javax.swing.GroupLayout DirectoryPanelLayout = new javax.swing.GroupLayout(DirectoryPanel);
        DirectoryPanel.setLayout(DirectoryPanelLayout);
        DirectoryPanelLayout.setHorizontalGroup(
            DirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DirectoryPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel9)
                .addGap(10, 10, 10)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(361, Short.MAX_VALUE))
        );
        DirectoryPanelLayout.setVerticalGroup(
            DirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DirectoryPanelLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(DirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );

        closePanel.setBackground(new java.awt.Color(255, 255, 255));
        closePanel.setForeground(new java.awt.Color(30, 130, 234));
        closePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closePanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closePanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closePanelMouseExited(evt);
            }
        });

        txtClose.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        txtClose.setForeground(new java.awt.Color(30, 130, 234));
        txtClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtClose.setText("X");

        javax.swing.GroupLayout closePanelLayout = new javax.swing.GroupLayout(closePanel);
        closePanel.setLayout(closePanelLayout);
        closePanelLayout.setHorizontalGroup(
            closePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, closePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtClose, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );
        closePanelLayout.setVerticalGroup(
            closePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, closePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtClose)
                .addContainerGap())
        );

        minimizePanel.setBackground(new java.awt.Color(255, 255, 255));
        minimizePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizePanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizePanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizePanelMouseExited(evt);
            }
        });

        txtMinimize.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        txtMinimize.setForeground(new java.awt.Color(30, 130, 234));
        txtMinimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtMinimize.setText("_");

        javax.swing.GroupLayout minimizePanelLayout = new javax.swing.GroupLayout(minimizePanel);
        minimizePanel.setLayout(minimizePanelLayout);
        minimizePanelLayout.setHorizontalGroup(
            minimizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, minimizePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMinimize, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );
        minimizePanelLayout.setVerticalGroup(
            minimizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, minimizePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtMinimize)
                .addContainerGap())
        );

        pnlHome.setBackground(new java.awt.Color(234, 234, 234));
        pnlHome.setForeground(new java.awt.Color(234, 234, 234));
        pnlHome.setToolTipText("");
        pnlHome.setPreferredSize(new java.awt.Dimension(182, 163));
        pnlHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlHomeMouseExited(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/HIS_Home2_VLB.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(30, 132, 234));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Home");

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pnlTambahPegawai.setBackground(new java.awt.Color(234, 234, 234));
        pnlTambahPegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTambahPegawaiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTambahPegawaiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTambahPegawaiMouseExited(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/HIS_Add User_VLB.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(30, 132, 234));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Tambah Pegawai");

        javax.swing.GroupLayout pnlTambahPegawaiLayout = new javax.swing.GroupLayout(pnlTambahPegawai);
        pnlTambahPegawai.setLayout(pnlTambahPegawaiLayout);
        pnlTambahPegawaiLayout.setHorizontalGroup(
            pnlTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTambahPegawaiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlTambahPegawaiLayout.setVerticalGroup(
            pnlTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTambahPegawaiLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlGantiPassword.setBackground(new java.awt.Color(234, 234, 234));
        pnlGantiPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlGantiPasswordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlGantiPasswordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlGantiPasswordMouseExited(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/VLB_Change Password_VLB.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(30, 132, 234));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Ganti Password");

        javax.swing.GroupLayout pnlGantiPasswordLayout = new javax.swing.GroupLayout(pnlGantiPassword);
        pnlGantiPassword.setLayout(pnlGantiPasswordLayout);
        pnlGantiPasswordLayout.setHorizontalGroup(
            pnlGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGantiPasswordLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGantiPasswordLayout.setVerticalGroup(
            pnlGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGantiPasswordLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(DirectoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(pnlHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlGantiPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlTambahPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minimizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(DirectoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlGantiPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTambahPegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHome, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addGap(273, 273, 273))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closePanelMouseClicked
        int ok = JOptionPane.showConfirmDialog(null, "Anda Akan Keluar Dari Program?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_closePanelMouseClicked

    private void minimizePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizePanelMouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minimizePanelMouseClicked

    private void pnlHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHomeMouseClicked
        dispose();
        HomeAdmin ha = new HomeAdmin();
        ha.show();
    }//GEN-LAST:event_pnlHomeMouseClicked

    private void pnlHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHomeMouseEntered
        pnlHome.setBackground(new java.awt.Color(115, 163, 239));
    }//GEN-LAST:event_pnlHomeMouseEntered

    private void pnlHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHomeMouseExited
        pnlHome.setBackground(new java.awt.Color(234, 234, 234));
    }//GEN-LAST:event_pnlHomeMouseExited

    private void pnlTambahPegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTambahPegawaiMouseClicked
        MasterTambahPegawai ps = new MasterTambahPegawai();
        ps.show();
    }//GEN-LAST:event_pnlTambahPegawaiMouseClicked

    private void pnlTambahPegawaiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTambahPegawaiMouseEntered
        pnlTambahPegawai.setBackground(new java.awt.Color(115, 163, 239));
    }//GEN-LAST:event_pnlTambahPegawaiMouseEntered

    private void pnlTambahPegawaiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTambahPegawaiMouseExited
        pnlTambahPegawai.setBackground(new java.awt.Color(234, 234, 234));
    }//GEN-LAST:event_pnlTambahPegawaiMouseExited

    private void pnlGantiPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGantiPasswordMouseClicked
        MasterGantiPassword ps = new MasterGantiPassword();
        ps.show();
    }//GEN-LAST:event_pnlGantiPasswordMouseClicked

    private void pnlGantiPasswordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGantiPasswordMouseEntered
        pnlGantiPassword.setBackground(new java.awt.Color(115, 163, 239));
    }//GEN-LAST:event_pnlGantiPasswordMouseEntered

    private void pnlGantiPasswordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGantiPasswordMouseExited
        pnlGantiPassword.setBackground(new java.awt.Color(234, 234, 234));
    }//GEN-LAST:event_pnlGantiPasswordMouseExited

    private void closePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closePanelMouseEntered
        closePanel.setBackground(Color.red);
        txtClose.setForeground(Color.white);
    }//GEN-LAST:event_closePanelMouseEntered

    private void minimizePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizePanelMouseEntered
        minimizePanel.setBackground(new java.awt.Color(234, 234, 234));
    }//GEN-LAST:event_minimizePanelMouseEntered

    private void closePanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closePanelMouseExited
        closePanel.setBackground(new java.awt.Color(255, 255, 255));
        txtClose.setForeground(new java.awt.Color(30, 130, 234));
    }//GEN-LAST:event_closePanelMouseExited

    private void minimizePanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizePanelMouseExited
        minimizePanel.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_minimizePanelMouseExited
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
            java.util.logging.Logger.getLogger(MenuMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new MenuMaster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DirectoryPanel;
    private javax.swing.JPanel closePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel minimizePanel;
    private javax.swing.JPanel pnlGantiPassword;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlTambahPegawai;
    private javax.swing.JLabel txtClose;
    private javax.swing.JLabel txtMinimize;
    // End of variables declaration//GEN-END:variables
}