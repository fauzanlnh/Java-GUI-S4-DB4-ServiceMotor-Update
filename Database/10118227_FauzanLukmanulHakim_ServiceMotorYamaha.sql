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
) ENGINE=InnoDB AUTO_INCREMENT=242009 DEFAULT CHARSET=latin1;

/*Data for the table `t_customer` */

insert  into `t_customer`(`Id_Customer`,`Nama_Customer`,`Alamat`,`No_Telp`) values 
(1,'FAUZAN LUKMANUL HAKIM','MARKEN','0897'),
(2,'SAEPULOH','KOPO','8080'),
(3,'STEPHAN','MANGLID','1'),
(234,'AS','AS','23245'),
(2334,'ADA','GBA','08979'),
(13132,'SOHIBUL','CIJAGRA','089894579'),
(32459,'DJORDIE','CIJAGRA','0898048'),
(242008,'DARSI','CIJAURA','04023840');

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_faktur_jasa` */

insert  into `t_det_faktur_jasa`(`Id_DetJasa`,`Id_Faktur`,`Id_Jasa`,`Harga`,`Qty`,`Total_Per_Detail`) values 
(1,1,1,70000,1,70000),
(2,2,1,70000,1,70000),
(3,3,1,70000,1,70000),
(4,7,1,70000,1,70000),
(5,7,1,70000,2,140000),
(6,7,1,70000,2,140000),
(7,7,1,70000,2,140000),
(8,8,4,10000,1,10000),
(9,9,1,70000,1,70000),
(10,5,1,70000,1,70000),
(11,9,1,70000,1,70000),
(12,9,1,70000,1,70000),
(13,4,1,70000,2,140000),
(14,9,1,70000,1,70000),
(15,5,1,70000,1,70000),
(16,4,1,70000,1,70000),
(17,10,1,70000,2,140000),
(18,11,1,70000,1,70000);

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_faktur_sparepart` */

insert  into `t_det_faktur_sparepart`(`Id_DetSparepart`,`Id_Faktur`,`Id_Sparepart`,`Harga`,`Qty`,`Total_Per_Detail`) values 
(1,1,'2DPF580500000',75000,2,150000),
(2,2,'2DPF580500000',75000,1,75000),
(3,6,'2DPF580500000',75000,2,150000),
(4,6,'2DPF580500000',75000,2,150000),
(5,6,'2DPF580500000',75000,2,150000),
(6,6,'2DPF580500000',75000,2,150000),
(7,7,'90793AJ44500',42500,2,85000),
(8,7,'2DPF580500000',75000,2,150000),
(9,7,'2DPF580500000',75000,2,150000),
(10,7,'2DPF580500000',75000,2,150000),
(11,8,'90793AJ44500',42500,1,42500),
(12,8,'2PHF624600000',15000,2,30000),
(13,9,'2DPF580500000',75000,2,150000),
(14,11,'2PHF624600000',15000,2,30000);

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_pendaftaran_jasa` */

insert  into `t_det_pendaftaran_jasa`(`Id_Det_Pend_Jasa`,`Id_Faktur`,`Id_Jasa`,`Harga`) values 
(1,1,1,70000),
(2,2,1,70000),
(3,3,1,70000),
(4,4,1,70000),
(5,5,1,70000),
(6,7,1,70000),
(7,8,4,10000),
(8,11,1,70000);

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_pendaftaran_sparepart` */

insert  into `t_det_pendaftaran_sparepart`(`Id_Det_Pend_Spare`,`Id_Faktur`,`Id_Sparepart`,`Harga`) values 
(1,1,'2DPF580500000',75000),
(2,2,'2DPF580500000',75000),
(3,6,'2DPF580500000',75000),
(4,8,'90793AJ44500',42500),
(5,11,'2PHF624600000',15000);

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_sparepart_masuk` */

insert  into `t_det_sparepart_masuk`(`Id_DetBrgMasuk`,`Id_Sprt_Masuk`,`Id_Sparepart`,`Jmlh_Masuk`,`Harga_Masuk`,`Total_Per_Detail`) values 
(10,'TES1','2DPF580500000',300,70000,21000000),
(11,'TES1','90793AJ44500',100,65000,6500000),
(12,'','2DPF580500000',400,80000,32000000),
(13,'TES2','44DE540070000',200,15000,3000000),
(14,'TES2','2PHF624600000',300,10000,3000000),
(15,'TES3','JHASDFKHG',100,45000,4500000),
(16,'TES3','2DPF580500000',10,38000,380000);

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `t_faktur` */

insert  into `t_faktur`(`Id_Faktur`,`Tanggal`,`Id_Teknisi`,`Id_Kasir`,`Id_Customer`,`Status`,`No_Polisi`,`Total_Sparepart`,`Total_Jasa`,`Total_Bayar`) values 
(1,'2020-05-31',1,1,1,'BERES','D 4997 ZCC',150000,70000,220000),
(2,'2020-01-31',1,1,3,'BERES','D 1313 ZSD',75000,70000,145000),
(3,'2020-05-31',1,1,2,'BERES','D 6783 XC',0,70000,70000),
(4,'2020-06-04',6,1,2,'BERES','D 6783 XC',0,70000,70000),
(5,'2020-06-04',5,1,2,'BERES','D 6783 XC',0,70000,70000),
(6,'2020-05-31',1,1,1,'BERES','D 4997 ZCC',150000,0,150000),
(7,'2020-06-03',5,1,1,'BERES','D 4997 ZCC',150000,140000,290000),
(8,'2020-06-03',7,1,3,'BERES','D 1313 ZSD',72500,10000,82500),
(9,'2020-06-04',0,1,1,'BERES','D 4997 ZCC',0,70000,70000),
(10,NULL,NULL,NULL,242008,'PROSES','D 450 ZCX',NULL,NULL,NULL),
(11,'2020-06-04',5,1,1,'BERES','D 4997 ZCC',30000,70000,100000);

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
('','2020-05-04',32000000,NULL),
('TES1','2020-05-01',27500000,NULL),
('TES2','2020-05-31',6000000,NULL),
('TES3','2020-06-03',4880000,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `t_jenis_jasa` */

insert  into `t_jenis_jasa`(`Id_Jasa`,`Nama_Jasa`,`Id_Jenis`,`Harga_Jasa`) values 
(1,'SERVICE RINGAN',2,70000),
(2,'SERVICE RINGAN',1,90000),
(3,'SERVICE RINGAN',3,110000),
(4,'ONGKOS PASANG 1',2,10000),
(5,'ONGKOS PASANG 2',2,15000),
(6,'ONGKOS PASANG 3',2,18000),
(7,'ONGKOS PASANG 4',2,20000),
(8,'ONGKOS PASANG 5',2,25000);

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
('2DPF580500000','BRAKE PAD KIT',2,698,75000),
('2PHF624600000','END, GRIPP',2,296,15000),
('44DE540070000','ELEMENT',2,200,18000),
('90793AJ44500','YAMALUBE POWER MATIC',2,97,42500),
('JHASDFKHG','YAMALUBE',1,100,45000);

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
('ABDUL','ABDUL','SPAREPART',8),
('ADMIN','ADMIN2','ADMIN',0),
('fauzan','bapamana','ADMIN',9),
('KASIR','KASIR','KASIR',1),
('MAMAN','MAMAN','TEKNISI',5),
('SAEPUL','SAEPUL','KASIR',4),
('SPAREPART','SPAREPART','SPAREPART',2),
('SUEB','SUEB','TEKNISI',7),
('TEKNISI','TEKNISI','TEKNISI',3),
('UDIN','UDIN','TEKNISI',6);

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

insert  into `t_motor`(`No_Polisi`,`No_Rangka`,`No_Mesin`,`Id_Customer`,`Id_Tipe`) values 
('D 1313 ZSD','131313DAD','3ASDKLJL1J3A',3,1),
('D 3215 DG','KJFDSKJ43414','ADKLK34UF98',13132,2),
('D 3264 XZY','ADSDFSD5','ASDAS4323',2334,2),
('D 450 ZCX','LK423480','KASHD283',242008,1),
('D 4997 ZCC','13FALSJFI','13HISFHAIH',1,1),
('D 6783 XC','23748458C','JDKJI43142',2,2);

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
(0,'ADMIN','-','-','ADMIN'),
(1,'KASIR','-','-','KASIR'),
(2,'SPAREPART','-','-','SPAREPART'),
(3,'TEKNISI','-','-','TEKNISI'),
(4,'SAEPUL','ASTANAANYAR','089243245742','KASIR'),
(5,'MAMAN','CIWASTRA','089764241345','TEKNISI'),
(6,'UDIN','CIBIRU','089877224321','TEKNISI'),
(7,'SUEB','UJUNG BERUNG','089632455422','TEKNISI'),
(8,'ABDUL','CIGONDEWAH','089898537693','SPAREPART'),
(9,'fauzan','marken','089623134231','ADMIN');

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

insert  into `t_pegawai_kasir`(`Id_Pegawai`,`Nama_Pegawai`,`Alamat`,`No_Telp`) values 
(1,'SAEPUL','ASTANAANYAR','08089');

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

insert  into `t_pegawai_teknisi`(`Id_Pegawai`,`Nama_Pegawai`,`Alamat`,`No_Telp`) values 
(1,'MAMAT','BUAH BATU','0889359379'),
(2,'UDIN','CIWASTRA','0088594759'),
(3,'SUEB','Cijerah','0808407065');

/*Table structure for table `t_tipe` */

DROP TABLE IF EXISTS `t_tipe`;

CREATE TABLE `t_tipe` (
  `Id_Tipe` int(11) NOT NULL AUTO_INCREMENT,
  `Nama_Tipe` varchar(25) NOT NULL,
  `Id_Jenis` int(11) NOT NULL,
  PRIMARY KEY (`Id_Tipe`),
  KEY `FK_IdJenis` (`Id_Jenis`),
  CONSTRAINT `t_tipe_ibfk_1` FOREIGN KEY (`Id_Jenis`) REFERENCES `t_jenis_motor` (`Id_Jenis`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_tipe` */

insert  into `t_tipe`(`Id_Tipe`,`Nama_Tipe`,`Id_Jenis`) values 
(1,'NEW MIO M3 CW',2),
(2,'R15',3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
