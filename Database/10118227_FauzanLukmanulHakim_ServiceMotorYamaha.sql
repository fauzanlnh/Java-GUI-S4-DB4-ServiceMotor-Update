/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 10.1.34-MariaDB : Database - 10118227_fauzanlukmanulhakim_servicemotoryamaha
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`10118227_fauzanlukmanulhakim_servicemotoryamaha` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `10118227_fauzanlukmanulhakim_servicemotoryamaha`;

/*Table structure for table `t_customer` */

DROP TABLE IF EXISTS `t_customer`;

CREATE TABLE `t_customer` (
  `Id_Customer` int(11) NOT NULL AUTO_INCREMENT,
  `Nama_Customer` varchar(50) NOT NULL,
  `Alamat` varchar(50) DEFAULT NULL,
  `No_Telp` varchar(13) NOT NULL,
  PRIMARY KEY (`Id_Customer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_customer` */

/*Table structure for table `t_det_faktur_jasa` */

DROP TABLE IF EXISTS `t_det_faktur_jasa`;

CREATE TABLE `t_det_faktur_jasa` (
  `Id_DetJasa` int(11) NOT NULL AUTO_INCREMENT,
  `Id_Faktur` int(11) DEFAULT NULL,
  `Id_Jasa` int(11) DEFAULT NULL,
  `Harga` int(11) DEFAULT NULL,
  `Qty` int(11) DEFAULT NULL,
  `Total_Per_Detail` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_DetJasa`),
  KEY `FK_IdFakturDetJasa` (`Id_Faktur`),
  KEY `FK_IdJasaDetJasa` (`Id_Jasa`),
  CONSTRAINT `FK_IdFakturDetJasa` FOREIGN KEY (`Id_Faktur`) REFERENCES `t_faktur` (`Id_Faktur`),
  CONSTRAINT `t_det_faktur_jasa_ibfk_1` FOREIGN KEY (`Id_Jasa`) REFERENCES `t_jenis_jasa` (`Id_Jasa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_det_faktur_jasa` */

/*Table structure for table `t_det_faktur_sparepart` */

DROP TABLE IF EXISTS `t_det_faktur_sparepart`;

CREATE TABLE `t_det_faktur_sparepart` (
  `Id_DetSparepart` int(11) NOT NULL AUTO_INCREMENT,
  `Id_Faktur` int(11) DEFAULT NULL,
  `Id_Sparepart` varchar(15) DEFAULT NULL,
  `Harga` int(11) DEFAULT NULL,
  `Qty` int(11) DEFAULT NULL,
  `Total_Per_Detail` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_DetSparepart`),
  KEY `FK_IdFakturDetSparepart` (`Id_Faktur`),
  KEY `FK_IdSparepartDetSparepart` (`Id_Sparepart`),
  CONSTRAINT `FK_IdFakturDetSparepart` FOREIGN KEY (`Id_Faktur`) REFERENCES `t_faktur` (`Id_Faktur`),
  CONSTRAINT `FK_IdSparepartDetSparepart` FOREIGN KEY (`Id_Sparepart`) REFERENCES `t_jenis_sparepart` (`Id_Sparepart`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_det_faktur_sparepart` */

/*Table structure for table `t_det_pendaftaran_jasa` */

DROP TABLE IF EXISTS `t_det_pendaftaran_jasa`;

CREATE TABLE `t_det_pendaftaran_jasa` (
  `Id_Det_Pend_Jasa` int(11) NOT NULL AUTO_INCREMENT,
  `Id_Faktur` int(11) DEFAULT NULL,
  `Id_Jasa` int(11) DEFAULT NULL,
  `Harga` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Det_Pend_Jasa`),
  KEY `Id_Jasa` (`Id_Jasa`),
  KEY `Id_Faktur` (`Id_Faktur`),
  CONSTRAINT `t_det_pendaftaran_jasa_ibfk_1` FOREIGN KEY (`Id_Jasa`) REFERENCES `t_jenis_jasa` (`Id_Jasa`),
  CONSTRAINT `t_det_pendaftaran_jasa_ibfk_2` FOREIGN KEY (`Id_Faktur`) REFERENCES `t_faktur` (`Id_Faktur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_det_pendaftaran_jasa` */

/*Table structure for table `t_det_pendaftaran_sparepart` */

DROP TABLE IF EXISTS `t_det_pendaftaran_sparepart`;

CREATE TABLE `t_det_pendaftaran_sparepart` (
  `Id_Det_Pend_Spare` int(11) NOT NULL AUTO_INCREMENT,
  `Id_Faktur` int(11) DEFAULT NULL,
  `Id_Sparepart` varchar(15) DEFAULT NULL,
  `Harga` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Det_Pend_Spare`),
  KEY `Id_Sparepart` (`Id_Sparepart`),
  KEY `Id_Faktur` (`Id_Faktur`),
  CONSTRAINT `t_det_pendaftaran_sparepart_ibfk_1` FOREIGN KEY (`Id_Sparepart`) REFERENCES `t_jenis_sparepart` (`Id_Sparepart`),
  CONSTRAINT `t_det_pendaftaran_sparepart_ibfk_2` FOREIGN KEY (`Id_Faktur`) REFERENCES `t_faktur` (`Id_Faktur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_det_pendaftaran_sparepart` */

/*Table structure for table `t_det_sparepart_masuk` */

DROP TABLE IF EXISTS `t_det_sparepart_masuk`;

CREATE TABLE `t_det_sparepart_masuk` (
  `Id_DetBrgMasuk` int(11) NOT NULL AUTO_INCREMENT,
  `Id_Sprt_Masuk` varchar(15) DEFAULT NULL,
  `Id_Sparepart` varchar(15) DEFAULT NULL,
  `Jmlh_Masuk` int(11) DEFAULT NULL,
  `Harga_Masuk` int(11) DEFAULT NULL,
  `Total_Per_Detail` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_DetBrgMasuk`),
  KEY `FK_IdSparepartDetSparepartMasuk` (`Id_Sprt_Masuk`),
  KEY `FK_IdSpprtMasukDetSparepartMasuk` (`Jmlh_Masuk`),
  KEY `Id_Sparepart` (`Id_Sparepart`),
  CONSTRAINT `t_det_sparepart_masuk_ibfk_2` FOREIGN KEY (`Id_Sparepart`) REFERENCES `t_jenis_sparepart` (`Id_Sparepart`),
  CONSTRAINT `t_det_sparepart_masuk_ibfk_3` FOREIGN KEY (`Id_Sprt_Masuk`) REFERENCES `t_faktur_sparepart_masuk` (`Id_Sprt_Masuk`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_sparepart_masuk` */

insert  into `t_det_sparepart_masuk`(`Id_DetBrgMasuk`,`Id_Sprt_Masuk`,`Id_Sparepart`,`Jmlh_Masuk`,`Harga_Masuk`,`Total_Per_Detail`) values 
(2,'COBA1','OLI1',70,35000,2450000);

/*Table structure for table `t_detail_tindakan` */

DROP TABLE IF EXISTS `t_detail_tindakan`;

CREATE TABLE `t_detail_tindakan` (
  `Kode_Detail_Tindakan` bigint(20) NOT NULL AUTO_INCREMENT,
  `Kode_Rincian` bigint(20) NOT NULL,
  `Kode_Tindakan` bigint(20) NOT NULL,
  `Harga_Per_Tindakan` bigint(20) DEFAULT NULL,
  `Qty` bigint(20) DEFAULT NULL,
  `Total_Per_Detail` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Kode_Detail_Tindakan`),
  KEY `FK_KodeRincian` (`Kode_Rincian`),
  KEY `FK_KodeTindakan` (`Kode_Tindakan`),
  CONSTRAINT `FK_KodeRincian` FOREIGN KEY (`Kode_Rincian`) REFERENCES `t_rincian` (`Kode_Rincian`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_KodeTindakan` FOREIGN KEY (`Kode_Tindakan`) REFERENCES `t_tindakan` (`Kode_Tindakan`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_detail_tindakan` */

/*Table structure for table `t_faktur` */

DROP TABLE IF EXISTS `t_faktur`;

CREATE TABLE `t_faktur` (
  `Id_Faktur` int(11) NOT NULL AUTO_INCREMENT,
  `Tanggal` date DEFAULT NULL,
  `Id_Teknisi` int(11) DEFAULT NULL,
  `Id_Kasir` int(11) DEFAULT NULL,
  `Id_Customer` int(11) DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `No_Polisi` varchar(15) NOT NULL,
  `Total_Sparepart` int(11) DEFAULT NULL,
  `Total_Jasa` int(11) DEFAULT NULL,
  `Total_Bayar` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Faktur`),
  KEY `FK_IdTeknisiFaktur` (`Id_Teknisi`),
  KEY `FK_IdKasirFaktur` (`Id_Kasir`),
  KEY `FK_IdCustomerFaktur` (`Id_Customer`),
  KEY `FK_NoPolisiFaktur` (`No_Polisi`),
  CONSTRAINT `FK_IdCustomerFaktur` FOREIGN KEY (`Id_Customer`) REFERENCES `t_customer` (`Id_Customer`),
  CONSTRAINT `FK_NoPolisiFaktur` FOREIGN KEY (`No_Polisi`) REFERENCES `t_motor` (`No_Polisi`),
  CONSTRAINT `t_faktur_ibfk_1` FOREIGN KEY (`Id_Teknisi`) REFERENCES `t_pegawai` (`Id_Pegawai`),
  CONSTRAINT `t_faktur_ibfk_2` FOREIGN KEY (`Id_Kasir`) REFERENCES `t_pegawai` (`Id_Pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_faktur` */

/*Table structure for table `t_faktur_sparepart_masuk` */

DROP TABLE IF EXISTS `t_faktur_sparepart_masuk`;

CREATE TABLE `t_faktur_sparepart_masuk` (
  `Id_Sprt_Masuk` varchar(15) NOT NULL,
  `Tanggal` date DEFAULT NULL,
  `Total_Harga` int(11) DEFAULT NULL,
  `Id_Pegawai` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Sprt_Masuk`),
  KEY `FK_IdBsparepartFakturSparepartMasuk` (`Total_Harga`),
  KEY `Id_Pegawai` (`Id_Pegawai`),
  CONSTRAINT `t_faktur_sparepart_masuk_ibfk_1` FOREIGN KEY (`Id_Pegawai`) REFERENCES `t_pegawai` (`Id_Pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_faktur_sparepart_masuk` */

insert  into `t_faktur_sparepart_masuk`(`Id_Sprt_Masuk`,`Tanggal`,`Total_Harga`,`Id_Pegawai`) values 
('COBA1','2020-08-02',2450000,0);

/*Table structure for table `t_jenis_jasa` */

DROP TABLE IF EXISTS `t_jenis_jasa`;

CREATE TABLE `t_jenis_jasa` (
  `Id_Jasa` int(11) NOT NULL AUTO_INCREMENT,
  `Nama_Jasa` varchar(30) DEFAULT NULL,
  `Id_Jenis` int(11) DEFAULT NULL,
  `Harga_Jasa` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Jasa`),
  KEY `FK_IdJenisJasa` (`Id_Jenis`),
  CONSTRAINT `t_jenis_jasa_ibfk_1` FOREIGN KEY (`Id_Jenis`) REFERENCES `t_jenis_motor` (`Id_Jenis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_jenis_jasa` */

/*Table structure for table `t_jenis_motor` */

DROP TABLE IF EXISTS `t_jenis_motor`;

CREATE TABLE `t_jenis_motor` (
  `Id_Jenis` int(11) NOT NULL AUTO_INCREMENT,
  `Nama_Jenis` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Id_Jenis`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `t_jenis_motor` */

insert  into `t_jenis_motor`(`Id_Jenis`,`Nama_Jenis`) values 
(1,'GIGI'),
(2,'MATIC'),
(3,'SPORT');

/*Table structure for table `t_jenis_sparepart` */

DROP TABLE IF EXISTS `t_jenis_sparepart`;

CREATE TABLE `t_jenis_sparepart` (
  `Id_Sparepart` varchar(15) NOT NULL,
  `Nama_Sparepart` varchar(50) DEFAULT NULL,
  `Id_Jenis` int(11) DEFAULT NULL,
  `Stok` int(11) DEFAULT NULL,
  `Harga_Sparepart` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Sparepart`),
  KEY `FK_IdJenisSparepart` (`Id_Jenis`),
  CONSTRAINT `t_jenis_sparepart_ibfk_1` FOREIGN KEY (`Id_Jenis`) REFERENCES `t_jenis_motor` (`Id_Jenis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_jenis_sparepart` */

insert  into `t_jenis_sparepart`(`Id_Sparepart`,`Nama_Sparepart`,`Id_Jenis`,`Stok`,`Harga_Sparepart`) values 
('OLI1','YAMALUBE POWER MATIC',2,70,45000);

/*Table structure for table `t_login` */

DROP TABLE IF EXISTS `t_login`;

CREATE TABLE `t_login` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `level` varchar(20) DEFAULT NULL,
  `Id_Pegawai` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `Id_Pegawai` (`Id_Pegawai`),
  CONSTRAINT `t_login_ibfk_1` FOREIGN KEY (`Id_Pegawai`) REFERENCES `t_pegawai` (`Id_Pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_login` */

insert  into `t_login`(`username`,`password`,`level`,`Id_Pegawai`) values 
('ADMIN','ADMIN','ADMIN',0);

/*Table structure for table `t_motor` */

DROP TABLE IF EXISTS `t_motor`;

CREATE TABLE `t_motor` (
  `No_Polisi` varchar(15) NOT NULL,
  `No_Rangka` varchar(20) NOT NULL,
  `No_Mesin` varchar(20) NOT NULL,
  `Id_Customer` int(11) DEFAULT NULL,
  `Id_Tipe` int(11) DEFAULT NULL,
  PRIMARY KEY (`No_Polisi`),
  KEY `FK_IdCustomer` (`Id_Customer`),
  KEY `FK_IdTipe` (`Id_Tipe`),
  CONSTRAINT `FK_IdCustomer` FOREIGN KEY (`Id_Customer`) REFERENCES `t_customer` (`Id_Customer`),
  CONSTRAINT `FK_IdTipe` FOREIGN KEY (`Id_Tipe`) REFERENCES `t_tipe` (`Id_Tipe`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_motor` */

/*Table structure for table `t_pegawai` */

DROP TABLE IF EXISTS `t_pegawai`;

CREATE TABLE `t_pegawai` (
  `Id_Pegawai` int(5) NOT NULL,
  `Nama_Pegawai` varchar(50) DEFAULT NULL,
  `Alamat` varchar(50) DEFAULT NULL,
  `No_Telp` varchar(15) DEFAULT NULL,
  `Bagian` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id_Pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_pegawai` */

insert  into `t_pegawai`(`Id_Pegawai`,`Nama_Pegawai`,`Alamat`,`No_Telp`,`Bagian`) values 
(0,'ADMIN','-','-','-');

/*Table structure for table `t_pegawai_kasir` */

DROP TABLE IF EXISTS `t_pegawai_kasir`;

CREATE TABLE `t_pegawai_kasir` (
  `Id_Pegawai` int(11) NOT NULL,
  `Nama_Pegawai` varchar(50) NOT NULL,
  `Alamat` varchar(50) NOT NULL,
  `No_Telp` varchar(13) NOT NULL,
  PRIMARY KEY (`Id_Pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_pegawai_kasir` */

/*Table structure for table `t_pegawai_teknisi` */

DROP TABLE IF EXISTS `t_pegawai_teknisi`;

CREATE TABLE `t_pegawai_teknisi` (
  `Id_Pegawai` int(11) NOT NULL,
  `Nama_Pegawai` varchar(50) NOT NULL,
  `Alamat` varchar(50) NOT NULL,
  `No_Telp` varchar(13) NOT NULL,
  PRIMARY KEY (`Id_Pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_pegawai_teknisi` */

/*Table structure for table `t_rincian` */

DROP TABLE IF EXISTS `t_rincian`;

CREATE TABLE `t_rincian` (
  `Kode_Rincian` bigint(20) NOT NULL AUTO_INCREMENT,
  `Subtotal_Tindakan` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Kode_Rincian`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_rincian` */

/*Table structure for table `t_tindakan` */

DROP TABLE IF EXISTS `t_tindakan`;

CREATE TABLE `t_tindakan` (
  `Kode_Tindakan` bigint(20) NOT NULL AUTO_INCREMENT,
  `Nama_Tindakan` char(30) DEFAULT NULL,
  `Harga_Per_Tindakan` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Kode_Tindakan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_tindakan` */

/*Table structure for table `t_tipe` */

DROP TABLE IF EXISTS `t_tipe`;

CREATE TABLE `t_tipe` (
  `Id_Tipe` int(11) NOT NULL AUTO_INCREMENT,
  `Nama_Tipe` varchar(25) NOT NULL,
  `Id_Jenis` int(11) NOT NULL,
  PRIMARY KEY (`Id_Tipe`),
  KEY `FK_IdJenis` (`Id_Jenis`),
  CONSTRAINT `t_tipe_ibfk_1` FOREIGN KEY (`Id_Jenis`) REFERENCES `t_jenis_motor` (`Id_Jenis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_tipe` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
