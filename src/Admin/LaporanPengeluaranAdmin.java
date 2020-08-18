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
public class LaporanPengeluaranAdmin extends javax.swing.JFrame {

    /**
     * Creates new form LaporanPengeluaranAdmin
     */
    Connection koneksi;

    public LaporanPengeluaranAdmin() {
        initComponents();
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "fauzan", "10118227_fauzanlukmanulhakim_servicemotoryamaha");
        this.setLocationRelativeTo(null);
        showData();
        setJDate();
    }
//-------------------------------------------------------------FORM UTAMA----------------------------------------------------------//
    //SELECT FROM DB

    public void showData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblPengeluaran.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nomor Faktur", "Nama Pegawai", "Tanggal", "Total Harga"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null, query2;
        int No = 1;
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM t_faktur_sparepart_masuk JOIN T_Pegawai USING(Id_Pegawai)";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Sprt_Masuk = rs.getString("Id_Sprt_Masuk");
                String Nama_Pegawai = rs.getString("Nama_Pegawai");
                String Tanggal = rs.getString("Tanggal");
                String Total_Harga = rs.getString("Total_Harga");
                dtm.addRow(new String[]{"" + No, Id_Sprt_Masuk, Nama_Pegawai, Tanggal, Total_Harga});
                No = No + 1;
            }
            query2 = "SELECT SUM(t_faktur_sparepart_masuk.Total_Harga) AS Total_Pemasukan,COUNT(Id_Sprt_Masuk) AS BanyakService "
                    + "FROM t_faktur_sparepart_masuk ";
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
        tblPengeluaran.setModel(dtm);
    }

    public void cariData() {
        DefaultTableModel tableModel = (DefaultTableModel) tblPengeluaran.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"No", "Nomor Faktur", "Nama Pegawai", "Tanggal", "Total Harga"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        String query = null, query2;
        int No = 1;
        String tanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tanggalLahir);
        String tglAwal = String.valueOf(fm.format(txtTglAwal.getDate()));
        String tglAkhir = String.valueOf(fm.format(txtTglAkhir.getDate()));
        try {
            Statement stmt = koneksi.createStatement();
            query = "SELECT * FROM t_faktur_sparepart_masuk JOIN T_Pegawai USING(Id_Pegawai) "
                    + "WHERE Tanggal BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String Id_Sprt_Masuk = rs.getString("Id_Sprt_Masuk");
                String Nama_Pegawai = rs.getString("Nama_Pegawai");
                String Tanggal = rs.getString("Tanggal");
                String Total_Harga = rs.getString("Total_Harga");
                dtm.addRow(new String[]{"" + No, Id_Sprt_Masuk, Nama_Pegawai, Tanggal, Total_Harga});
                No = No + 1;
            }
            query2 = "SELECT SUM(t_faktur_sparepart_masuk.Total_Harga) AS Total_Pemasukan,COUNT(Id_Sprt_Masuk) AS BanyakService "
                    + "FROM t_faktur_sparepart_masuk WHERE Tanggal BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "'";
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
        tblPengeluaran.setModel(dtm);
    }

    public void setJDate() {
        Calendar today = Calendar.getInstance();
        txtTglAwal.setDate(today.getTime());
        txtTglAkhir.setDate(today.getTime());
    }
//-------------------------------------------------------------FORM UTAMA----------------------------------------------------------//
//------------------------------------------------------------JFRAME POPUP---------------------------------------------------------//

    public void hapusDetSpareMasuk() {
        DefaultTableModel tableModel = (DefaultTableModel) tblDetail.getModel();
        int row = tblDetail.getSelectedRow();
        if (row >= 0) {
            tableModel.removeRow(row);
        }
    }

    public void setTabel() {
        String IdSprtMasuk = tblPengeluaran.getValueAt(tblPengeluaran.getSelectedRow(), 1).toString();
        DefaultTableModel tableModel = (DefaultTableModel) tblDetail.getModel();
        tableModel.setRowCount(0);
        String kolom[] = {"Id Det Sparepart", "Id Sparepart", "Nama Sparepart", "Harga", "Qty", "Total Harga"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);
        try {
            Statement stmt = koneksi.createStatement();
            String Query = "SELECT * FROM t_faktur_sparepart_masuk JOIN t_det_sparepart_masuk USING(Id_Sprt_Masuk) JOIN T_Jenis_Sparepart USING (Id_Sparepart) "
                    + "WHERE Id_Sprt_Masuk = '" + IdSprtMasuk + "' ";
            ResultSet rs = stmt.executeQuery(Query);
            while (rs.next()) {
                txtNoFaktur.setText(IdSprtMasuk);
                txtIdPegawai.setText(rs.getString("Id_Pegawai"));
                String IdDetSparepart = rs.getString("Id_DetBrgMasuk");
                String Id_Sparepart = rs.getString("Id_Sparepart");
                String NamaSparepart = rs.getString("Nama_Sparepart");
                String Harga = rs.getString("Harga_Masuk");
                String Qty = rs.getString("Jmlh_Masuk");
                String Total = rs.getString("Total_Per_Detail");
                dtm.addRow(new String[]{"" + IdDetSparepart, Id_Sparepart, NamaSparepart, Harga, Qty, Total});

            }
            tblDetail.setModel(dtm);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void simpanFaktur() {
        String KdFaktur = txtNoFaktur.getText().toUpperCase();
        DefaultTableModel tableModel = (DefaultTableModel) tblDetail.getModel();
        int TotalFaktur = 0, BerhasilInsertDetail = 0, BerhasilUpdateHarga = 0, berhasilUpdate = 0, BerhasilUpdateDetail = 0;
        if (tblDetail.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "DATA PEMBELIAN TIDAK BOLEH KOSONG");
        } else {
            try {
                Statement stmt = koneksi.createStatement();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    //QUERY 2
                    String Id_Sparepart = (String) tableModel.getValueAt(i, 1);
                    String Banyak = tableModel.getValueAt(i, 4).toString();
                    int QTY = Integer.valueOf(Banyak);
                    if (tableModel.getValueAt(i, 0).toString().equals("")) {
                        String InsertDetail = ("INSERT INTO T_Det_Sparepart_Masuk (Id_Sprt_Masuk, Id_Sparepart, Jmlh_Masuk, Harga_Masuk, Total_Per_Detail) VALUES("
                                + "'" + KdFaktur + "',"
                                + "'" + tableModel.getValueAt(i, 1) + "',"
                                + "'" + tableModel.getValueAt(i, 4) + "',"
                                + "'" + tableModel.getValueAt(i, 3) + "',"
                                + "'" + tableModel.getValueAt(i, 5) + "')");
                        BerhasilInsertDetail = stmt.executeUpdate(InsertDetail);
                        //QUERY 3
                        String UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = (Stok +'" + QTY + "') WHERE Id_Sparepart = '" + Id_Sparepart + "'";
                        berhasilUpdate = stmt.executeUpdate(UpdateStok);
                        BerhasilUpdateDetail = 1;
                    } else {
                        String getQtyLama = "SELECT * FROM T_Det_Sparepart_Masuk WHERE Id_DetBrgMasuk = '" + tableModel.getValueAt(i, 0) + "'";
                        int QtyLama = 0, Diff = 0;
                        ResultSet rs = stmt.executeQuery(getQtyLama);
                        if (rs.next()) {
                            QtyLama = rs.getInt("Jmlh_Masuk");
                        }
                        Diff = QTY - QtyLama;
                        String UpdateDetail = "UPDATE T_Det_Sparepart_Masuk SET Jmlh_Masuk = '" + tableModel.getValueAt(i, 4) + "', "
                                + "Harga_Masuk = '" + tableModel.getValueAt(i, 3) + "', "
                                + "Total_Per_Detail = '" + tableModel.getValueAt(i, 5) + "' "
                                + "WHERE Id_DetBrgMasuk = '" + tableModel.getValueAt(i, 0) + "'";
                        BerhasilUpdateDetail = stmt.executeUpdate(UpdateDetail);
                        String UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = (Stok +" + Diff + ") WHERE Id_Sparepart = '" + Id_Sparepart + "'";
                        System.out.println(UpdateStok);
                        berhasilUpdate = stmt.executeUpdate(UpdateStok);
                        BerhasilInsertDetail = 1;
                    }
                    String HS = tableModel.getValueAt(i, 5).toString();
                    int HargaSatuan = Integer.valueOf(HS);
                    TotalFaktur = HargaSatuan + TotalFaktur;
                }
                //QUERY 4
                String UpdateTotalHarga = "UPDATE T_Faktur_Sparepart_Masuk SET Total_Harga = '" + TotalFaktur + "' WHERE Id_Sprt_Masuk = '" + KdFaktur + "' ";
                BerhasilUpdateHarga = stmt.executeUpdate(UpdateTotalHarga);

                if (BerhasilInsertDetail == 1 && berhasilUpdate == 1 && BerhasilUpdateHarga == 1 && BerhasilUpdateDetail == 1) {
                    JOptionPane.showMessageDialog(null, "DATA BERHASIL DIMASUKKAN");
                    ubahFaktur.dispose();
                    try {
                        koneksi.commit();
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                    showData();
                } else {
                    JOptionPane.showMessageDialog(null, "DATA GAGAL DIMASUKKAN");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
            }
        }
    }

    public void DeleteAll() {
        String IdFaktur = tblPengeluaran.getValueAt(tblPengeluaran.getSelectedRow(), 1).toString();
        DefaultTableModel tDetail = (DefaultTableModel) tblDetail.getModel();
        int BerhasilUpdateStok = 0;
        try {
            Statement stmt = koneksi.createStatement();
            for (int i = 0; i < tDetail.getRowCount(); i++) {
                String UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = Stok - '" + tDetail.getValueAt(i, 4) + "' "
                        + "WHERE Id_Sparepart = '" + tblDetail.getValueAt(i, 1) + "'";
                BerhasilUpdateStok = stmt.executeUpdate(UpdateStok);
            }
            String DeleteFaktur = "DELETE FROM T_Faktur_Sparepart_Masuk WHERE Id_Sprt_Masuk = '" + IdFaktur + "'";
            int BDeleteFaktur = stmt.executeUpdate(DeleteFaktur);
            if (BDeleteFaktur > 0 && BerhasilUpdateStok > 0) {
                JOptionPane.showMessageDialog(null, "Data Faktur Berhasil Dihapus");
                showData();
            } else if (BerhasilUpdateStok < 1) {
                JOptionPane.showMessageDialog(null, "DATA STOK GAGAL DIUBAH");
            } else if (BDeleteFaktur < 1) {
                JOptionPane.showMessageDialog(null, "DATA FAKTUR GAGAL DIHAPUS");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteDetSpareMasuk() {
        DefaultTableModel tSparepart = (DefaultTableModel) tblDetail.getModel();
        try {
            Statement stmt = koneksi.createStatement();
            String Delete = "DELETE FROM T_Det_Sparepart_Masuk "
                    + "WHERE Id_DetBrgMasuk = '" + tSparepart.getValueAt(tblDetail.getSelectedRow(), 0).toString() + "'";
            int berhasil = stmt.executeUpdate(Delete);
            String UpdateStok = "UPDATE T_Jenis_Sparepart SET Stok = Stok - '" + tSparepart.getValueAt(tblDetail.getSelectedRow(), 4) + "' "
                    + "WHERE Id_Sparepart = '" + tSparepart.getValueAt(tblDetail.getSelectedRow(), 1) + "'";
            int bUPdate = stmt.executeUpdate(UpdateStok);
            System.out.println(Delete);
            hapusDetSpareMasuk();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
//------------------------------------------------------------JFRAME POPUP---------------------------------------------------------//
//--------------------------------------------------------JFRAME LIHAT SPAREPART---------------------------------------------------//

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
            JOptionPane.showMessageDialog(null, "KESALAHAN PADA DATABASE" + ex);
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
        String[] data = new String[6];
        data[0] = "";
        data[1] = KdBarang;
        data[2] = txtSparepart.getText();
        data[3] = txtHarga.getText();
        data[4] = txtQty.getText();
        data[5] = harga;
        tableModel.addRow(data);
        txtSparepart.setText("");
        txtHarga.setText("");
        txtQty.setText("");
    }

//--------------------------------------------------------JFRAME LIHAT SPAREPART---------------------------------------------------//    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ubahFaktur = new javax.swing.JFrame();
        mainPanel2 = new javax.swing.JPanel();
        PanelDirectory1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblService1 = new javax.swing.JLabel();
        txtNoFaktur = new javax.swing.JTextField();
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
        txtSparepart = new javax.swing.JTextField();
        btnLihat = new javax.swing.JButton();
        lblService8 = new javax.swing.JLabel();
        txtIdPegawai = new javax.swing.JLabel();
        btnCancel2 = new javax.swing.JButton();
        F_LihatBarang = new javax.swing.JFrame();
        mainPanel3 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        tblDetServices1 = new javax.swing.JScrollPane();
        tblLihat = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblNoPol2 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        txtDataTidakDitemukan = new javax.swing.JLabel();
        mainPanel1 = new javax.swing.JPanel();
        PanelDirectory = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPengeluaran = new javax.swing.JTable();
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
        btnEdit = new javax.swing.JButton();
        btnDeleteAll = new javax.swing.JButton();

        ubahFaktur.setUndecorated(true);

        mainPanel2.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel2.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory1.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory1.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Kasir/Cek Stok");

        javax.swing.GroupLayout PanelDirectory1Layout = new javax.swing.GroupLayout(PanelDirectory1);
        PanelDirectory1.setLayout(PanelDirectory1Layout);
        PanelDirectory1Layout.setHorizontalGroup(
            PanelDirectory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectory1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDirectory1Layout.setVerticalGroup(
            PanelDirectory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectory1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(39, 39, 39))
        );

        lblService1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService1.setForeground(new java.awt.Color(51, 51, 51));
        lblService1.setText("Harga");

        txtNoFaktur.setEditable(false);
        txtNoFaktur.setBackground(new java.awt.Color(255, 255, 255));
        txtNoFaktur.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNoFaktur.setForeground(new java.awt.Color(51, 51, 51));
        txtNoFaktur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

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

        txtSparepart.setEditable(false);
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

        lblService8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblService8.setForeground(new java.awt.Color(51, 51, 51));
        lblService8.setText("ID Pegawai");

        txtIdPegawai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtIdPegawai.setForeground(new java.awt.Color(51, 51, 51));
        txtIdPegawai.setText("ID Pegawai");

        btnCancel2.setBackground(new java.awt.Color(240, 240, 240));
        btnCancel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancel2.setForeground(new java.awt.Color(51, 51, 51));
        btnCancel2.setText("Cancel");
        btnCancel2.setBorder(null);
        btnCancel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mainPanel2Layout = new javax.swing.GroupLayout(mainPanel2);
        mainPanel2.setLayout(mainPanel2Layout);
        mainPanel2Layout.setHorizontalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelDirectory1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tblDetServices)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusService, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainPanel2Layout.createSequentialGroup()
                                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(83, 83, 83))
                            .addGroup(mainPanel2Layout.createSequentialGroup()
                                .addComponent(txtSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(mainPanel2Layout.createSequentialGroup()
                        .addComponent(lblService4)
                        .addGap(47, 47, 47)
                        .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblService8)
                        .addGap(26, 26, 26)
                        .addComponent(txtIdPegawai)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        mainPanel2Layout.setVerticalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelDirectory1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblService4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblService8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtIdPegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addGap(28, 28, 28)
                .addComponent(tblDetServices, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusService, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout ubahFakturLayout = new javax.swing.GroupLayout(ubahFaktur.getContentPane());
        ubahFaktur.getContentPane().setLayout(ubahFakturLayout);
        ubahFakturLayout.setHorizontalGroup(
            ubahFakturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        ubahFakturLayout.setVerticalGroup(
            ubahFakturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ubahFakturLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        F_LihatBarang.setUndecorated(true);

        mainPanel3.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout mainPanel3Layout = new javax.swing.GroupLayout(mainPanel3);
        mainPanel3.setLayout(mainPanel3Layout);
        mainPanel3Layout.setHorizontalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel3Layout.createSequentialGroup()
                        .addComponent(lblNoPol2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(mainPanel3Layout.createSequentialGroup()
                            .addComponent(txtDataTidakDitemukan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tblDetServices1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        mainPanel3Layout.setVerticalGroup(
            mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoPol2)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tblDetServices1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataTidakDitemukan)
                    .addGroup(mainPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                .addComponent(mainPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        F_LihatBarangLayout.setVerticalGroup(
            F_LihatBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(F_LihatBarangLayout.createSequentialGroup()
                .addComponent(mainPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel1.setPreferredSize(new java.awt.Dimension(710, 673));

        PanelDirectory.setBackground(new java.awt.Color(30, 130, 234));
        PanelDirectory.setPreferredSize(new java.awt.Dimension(636, 100));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Admin/Laporan Pengeluaran");

        javax.swing.GroupLayout PanelDirectoryLayout = new javax.swing.GroupLayout(PanelDirectory);
        PanelDirectory.setLayout(PanelDirectoryLayout);
        PanelDirectoryLayout.setHorizontalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDirectoryLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel8)
                .addContainerGap(396, Short.MAX_VALUE))
        );
        PanelDirectoryLayout.setVerticalGroup(
            PanelDirectoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDirectoryLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(39, 39, 39))
        );

        tblPengeluaran.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblPengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        tblPengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "No Faktur", "ID Customer", "Total Service", "Total Sparepart", "Total Bayar"
            }
        ));
        jScrollPane2.setViewportView(tblPengeluaran);

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
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Total Pemasukkan :");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Banyak Faktur :");

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

        btnDeleteAll.setBackground(new java.awt.Color(240, 240, 240));
        btnDeleteAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDeleteAll.setForeground(new java.awt.Color(51, 51, 51));
        btnDeleteAll.setText("Delete");
        btnDeleteAll.setBorder(null);
        btnDeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAllActionPerformed(evt);
            }
        });

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
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotalPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTotalService, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        String Berdasarkan = "Semua Pengeluaran", tglAwal = "", tglAkhir = "";
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
            File file = new File("src/Report/LaporanPengeluaran2.jasper");
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
        ubahFaktur.show();
        ubahFaktur.setSize(550, 570);
        ubahFaktur.setLocationRelativeTo(null);
        setTabel();
        try {
            koneksi.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Data Yang Dipilih Akan Dihapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            setTabel();
            DeleteAll();
        }
    }//GEN-LAST:event_btnDeleteAllActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        cariData();
        Click = 1;
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnTambahTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahTabelMouseClicked
        if (!txtSparepart.getText().equals("") && !txtHarga.getText().equals("") && !txtQty.getText().equals("")) {
            TambahTabel();
        } else if (txtSparepart.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "NANA SPAREPART MASIH KOSONG");
            txtSparepart.requestFocus();
        } else if (txtHarga.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "HARGA BELI MASIH KOSONG");
            txtHarga.requestFocus();
        } else if (txtQty.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "JUMLAH YANG DIBELI MASIH KOSONG");
            txtQty.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "DATA YANG AKAN DIMASUKKAN MASIH KOSONG");
            txtSparepart.requestFocus();
        }
    }//GEN-LAST:event_btnTambahTabelMouseClicked

    private void tblDetailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetailKeyReleased
        int Total = 0;
        int Qty = 0;
        String getHarga = tblDetail.getValueAt(tblDetail.getSelectedRow(), 3).toString();
        String getQty = tblDetail.getValueAt(tblDetail.getSelectedRow(), 4).toString();
        try {
            Qty = Integer.parseInt(getQty);
        } catch (NumberFormatException e) {

        }

        int Harga = Integer.parseInt(getHarga);
        Total = Qty * Harga;
        tblDetail.setValueAt(Total, tblDetail.getSelectedRow(), 5);        // TODO add your handling code here:
    }//GEN-LAST:event_tblDetailKeyReleased

    private void btnSubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseClicked
        if (!txtNoFaktur.getText().equals("") && tblDetail.getRowCount() > 0) {
            int ok = JOptionPane.showConfirmDialog(null, "Data Yang Dimasukkan Benar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == 0) {
                simpanFaktur();
            }
        } else if (txtNoFaktur.getText().equals("") && tblDetail.getRowCount() > 0) {
            JOptionPane.showMessageDialog(null, "NO FAKTUR MASIH KOSONG");
            txtNoFaktur.requestFocus();
        } else if (txtNoFaktur.getText().equals("") && tblDetail.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "NO FAKTUR DAN DETAIL PEMBELIAN MASIH KOSONG");
            txtNoFaktur.requestFocus();
        } else if (!txtNoFaktur.getText().equals("") && tblDetail.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "DETAIL PEMBELIAN MASIH KOSONG");
            btnLihat.requestFocus();
        }
    }//GEN-LAST:event_btnSubmitMouseClicked

    private void btnHapusServiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusServiceMouseClicked
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            if (tblDetail.getValueAt(tblDetail.getSelectedRow(), 0).toString().equals("")) {
                hapusDetSpareMasuk();
            } else {
                deleteDetSpareMasuk();
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

    private void btnCancel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancel2MouseClicked
        // TODO add your handling code here:
        try {
            koneksi.rollback();
            ubahFaktur.dispose();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnCancel2MouseClicked

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
            java.util.logging.Logger.getLogger(LaporanPengeluaranAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaporanPengeluaranAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaporanPengeluaranAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaporanPengeluaranAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LaporanPengeluaranAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame F_LihatBarang;
    private javax.swing.JPanel PanelDirectory;
    private javax.swing.JPanel PanelDirectory1;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancel2;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnDeleteAll;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapusService;
    private javax.swing.JButton btnLihat;
    private javax.swing.JButton btnPrint1;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnTambahTabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNoPol2;
    private javax.swing.JLabel lblService1;
    private javax.swing.JLabel lblService4;
    private javax.swing.JLabel lblService5;
    private javax.swing.JLabel lblService6;
    private javax.swing.JLabel lblService8;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JPanel mainPanel2;
    private javax.swing.JPanel mainPanel3;
    private javax.swing.JScrollPane tblDetServices;
    private javax.swing.JScrollPane tblDetServices1;
    private javax.swing.JTable tblDetail;
    private javax.swing.JTable tblLihat;
    private javax.swing.JTable tblPengeluaran;
    private javax.swing.JTextField txtCari;
    private javax.swing.JLabel txtDataTidakDitemukan;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JLabel txtIdPegawai;
    private javax.swing.JTextField txtNoFaktur;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSparepart;
    private com.toedter.calendar.JDateChooser txtTglAkhir;
    private com.toedter.calendar.JDateChooser txtTglAwal;
    private javax.swing.JLabel txtTotalPemasukan;
    private javax.swing.JLabel txtTotalService;
    private javax.swing.JFrame ubahFaktur;
    // End of variables declaration//GEN-END:variables
}
