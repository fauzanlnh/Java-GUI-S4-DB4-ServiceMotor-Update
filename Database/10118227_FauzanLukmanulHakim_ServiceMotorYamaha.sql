/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 8.0.19 : Database - 10118227_fauzanlukmanulhakim_servicemotoryamaha
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`10118227_fauzanlukmanulhakim_servicemotoryamaha` /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `10118227_fauzanlukmanulhakim_servicemotoryamaha`;

/*Table structure for table `t_customer` */

DROP TABLE IF EXISTS `t_customer`;

CREATE TABLE `t_customer` (
  `Id_Customer` int NOT NULL AUTO_INCREMENT,
  `Nama_Customer` varchar(50) NOT NULL,
  `Alamat` varchar(50) DEFAULT NULL,
  `No_Telp` varchar(13) NOT NULL,
  PRIMARY KEY (`Id_Customer`)
) ENGINE=InnoDB AUTO_INCREMENT=10118228 DEFAULT CHARSET=latin1;

/*Data for the table `t_customer` */

insert  into `t_customer`(`Id_Customer`,`Nama_Customer`,`Alamat`,`No_Telp`) values 
(1,'FAUZAN LUKMANUL HAKIM','JL. KOPO','089713243456'),
(2,'ASEP SAEPULOH','JL. PETA','087554674837'),
(3,'AHMAD AMIRUDIN','JL. BUAH BATU','089676986654'),
(4,'TEGUH YUSRAN','JL. SUKAMENAK','089743254237'),
(5,'DAFFA SUPARLAN','JL. ASTANAANYAR','089743214329'),
(6,'DJORDIE SECTIO','JL. KLININGAN','089742326734'),
(7,'ABIDI KASPI','JL. PELAJAR PEJUANG','085254209854'),
(8,'DONI RAMDHANI','JL. KIARACONDONG','089749237597'),
(9,'FAHMI SALIMUDIN','JL. PASIR KOJA','089053477031'),
(10,'FAHREIZA SIDIK','JL. CIBADUYUT','089872497953'),
(11,'GHIAR MULYAWAN','JL. KATAPANG','089234759874'),
(12,'IRGI AHMAD MAULAN','JL. CIPAGALO','089429757425'),
(13,'M. ZOELVAN RIDAN','JL. KIARACONDONG','084975982579'),
(14,'M. ARIEF FAUZAN','JL. PANYILEUKAN','089874326578'),
(15,'OKI SAPUTRA','JL. KIARACONDONG','088979723429'),
(16,'RADEN ANDRA','JL. BINONG','080472893759'),
(17,'RADEN BAIHAQI','JL. ASTANAANYAR','089975283883'),
(18,'RADEN FACHRUL','JL. MOCH TOHA','089742346858'),
(19,'RAMA ANDRYANA','JL. BUAH BATU','089742384677'),
(20,'RAMADHAN AZHAR','JL. SUKAMENAK','089732479836'),
(10118226,'YANUAR HIKMAH','JL. GATOT SUBROTO','089243214321'),
(10118227,'DEDI KUSNANDAR','JL. JATINANGOR','089743242345');

/*Table structure for table `t_det_faktur_jasa` */

DROP TABLE IF EXISTS `t_det_faktur_jasa`;

CREATE TABLE `t_det_faktur_jasa` (
  `Id_DetJasa` int NOT NULL AUTO_INCREMENT,
  `Id_Faktur` varchar(11) DEFAULT NULL,
  `Id_Jasa` int DEFAULT NULL,
  `Harga` int DEFAULT NULL,
  `Qty` int DEFAULT NULL,
  `Total_Per_Detail` int DEFAULT NULL,
  PRIMARY KEY (`Id_DetJasa`),
  KEY `FK_IdFakturDetJasa` (`Id_Faktur`),
  KEY `FK_IdJasaDetJasa` (`Id_Jasa`),
  CONSTRAINT `t_det_faktur_jasa_ibfk_1` FOREIGN KEY (`Id_Jasa`) REFERENCES `t_jenis_jasa` (`Id_Jasa`),
  CONSTRAINT `t_det_faktur_jasa_ibfk_2` FOREIGN KEY (`Id_Faktur`) REFERENCES `t_faktur` (`Id_Faktur`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_faktur_jasa` */

insert  into `t_det_faktur_jasa`(`Id_DetJasa`,`Id_Faktur`,`Id_Jasa`,`Harga`,`Qty`,`Total_Per_Detail`) values 
(1,'20208-001',1,60000,1,60000),
(2,'20208-002',7,5000,1,5000);

/*Table structure for table `t_det_faktur_sparepart` */

DROP TABLE IF EXISTS `t_det_faktur_sparepart`;

CREATE TABLE `t_det_faktur_sparepart` (
  `Id_DetSparepart` int NOT NULL AUTO_INCREMENT,
  `Id_Faktur` varchar(11) DEFAULT NULL,
  `Id_Sparepart` varchar(15) DEFAULT NULL,
  `Harga` int DEFAULT NULL,
  `Qty` int DEFAULT NULL,
  `Total_Per_Detail` int DEFAULT NULL,
  PRIMARY KEY (`Id_DetSparepart`),
  KEY `FK_IdFakturDetSparepart` (`Id_Faktur`),
  KEY `FK_IdSparepartDetSparepart` (`Id_Sparepart`),
  CONSTRAINT `FK_IdSparepartDetSparepart` FOREIGN KEY (`Id_Sparepart`) REFERENCES `t_jenis_sparepart` (`Id_Sparepart`),
  CONSTRAINT `t_det_faktur_sparepart_ibfk_1` FOREIGN KEY (`Id_Faktur`) REFERENCES `t_faktur` (`Id_Faktur`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_faktur_sparepart` */

insert  into `t_det_faktur_sparepart`(`Id_DetSparepart`,`Id_Faktur`,`Id_Sparepart`,`Harga`,`Qty`,`Total_Per_Detail`) values 
(1,'20208-001','RD2',45000,1,45000),
(2,'20208-002','OLI1',46000,1,46000);

/*Table structure for table `t_det_pendaftaran_jasa` */

DROP TABLE IF EXISTS `t_det_pendaftaran_jasa`;

CREATE TABLE `t_det_pendaftaran_jasa` (
  `Id_Det_Pend_Jasa` int NOT NULL AUTO_INCREMENT,
  `Id_Faktur` varchar(11) DEFAULT NULL,
  `Id_Jasa` int DEFAULT NULL,
  `Harga` int DEFAULT NULL,
  PRIMARY KEY (`Id_Det_Pend_Jasa`),
  KEY `Id_Jasa` (`Id_Jasa`),
  KEY `t_det_pendaftaran_jasa_ibfk_2` (`Id_Faktur`),
  CONSTRAINT `t_det_pendaftaran_jasa_ibfk_1` FOREIGN KEY (`Id_Jasa`) REFERENCES `t_jenis_jasa` (`Id_Jasa`),
  CONSTRAINT `t_det_pendaftaran_jasa_ibfk_2` FOREIGN KEY (`Id_Faktur`) REFERENCES `t_faktur` (`Id_Faktur`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_pendaftaran_jasa` */

insert  into `t_det_pendaftaran_jasa`(`Id_Det_Pend_Jasa`,`Id_Faktur`,`Id_Jasa`,`Harga`) values 
(2,'20208-001',1,60000);

/*Table structure for table `t_det_pendaftaran_sparepart` */

DROP TABLE IF EXISTS `t_det_pendaftaran_sparepart`;

CREATE TABLE `t_det_pendaftaran_sparepart` (
  `Id_Det_Pend_Spare` int NOT NULL AUTO_INCREMENT,
  `Id_Faktur` varchar(11) DEFAULT NULL,
  `Id_Sparepart` varchar(15) DEFAULT NULL,
  `Harga` int DEFAULT NULL,
  PRIMARY KEY (`Id_Det_Pend_Spare`),
  KEY `Id_Sparepart` (`Id_Sparepart`),
  KEY `t_det_pendaftaran_sparepart_ibfk_2` (`Id_Faktur`),
  CONSTRAINT `t_det_pendaftaran_sparepart_ibfk_1` FOREIGN KEY (`Id_Sparepart`) REFERENCES `t_jenis_sparepart` (`Id_Sparepart`),
  CONSTRAINT `t_det_pendaftaran_sparepart_ibfk_2` FOREIGN KEY (`Id_Faktur`) REFERENCES `t_faktur` (`Id_Faktur`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_pendaftaran_sparepart` */

insert  into `t_det_pendaftaran_sparepart`(`Id_Det_Pend_Spare`,`Id_Faktur`,`Id_Sparepart`,`Harga`) values 
(1,'20208-002','OLI1',46000);

/*Table structure for table `t_det_sparepart_masuk` */

DROP TABLE IF EXISTS `t_det_sparepart_masuk`;

CREATE TABLE `t_det_sparepart_masuk` (
  `Id_DetBrgMasuk` int NOT NULL AUTO_INCREMENT,
  `Id_Sprt_Masuk` varchar(15) DEFAULT NULL,
  `Id_Sparepart` varchar(15) DEFAULT NULL,
  `Jmlh_Masuk` int DEFAULT NULL,
  `Harga_Masuk` int DEFAULT NULL,
  `Total_Per_Detail` int DEFAULT NULL,
  PRIMARY KEY (`Id_DetBrgMasuk`),
  KEY `FK_IdSparepartDetSparepartMasuk` (`Id_Sprt_Masuk`),
  KEY `FK_IdSpprtMasukDetSparepartMasuk` (`Jmlh_Masuk`),
  KEY `Id_Sparepart` (`Id_Sparepart`),
  CONSTRAINT `t_det_sparepart_masuk_ibfk_2` FOREIGN KEY (`Id_Sparepart`) REFERENCES `t_jenis_sparepart` (`Id_Sparepart`),
  CONSTRAINT `t_det_sparepart_masuk_ibfk_3` FOREIGN KEY (`Id_Sprt_Masuk`) REFERENCES `t_faktur_sparepart_masuk` (`Id_Sprt_Masuk`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

/*Data for the table `t_det_sparepart_masuk` */

insert  into `t_det_sparepart_masuk`(`Id_DetBrgMasuk`,`Id_Sprt_Masuk`,`Id_Sparepart`,`Jmlh_Masuk`,`Harga_Masuk`,`Total_Per_Detail`) values 
(2,'COBA1','OLI1',70,35000,2450000),
(3,'FAKTURMASUK2','OLI2',30,40000,1200000),
(4,'FAKTUR 102','BUSI1',200,10000,2000000),
(5,'FAKTUR 102','BUSI3',200,15000,3000000),
(6,'FAKTUR 102','BUSI2',200,10000,2000000),
(7,'FAKTUR 412','KARBU1',100,25000,2500000),
(8,'FAKTUR 412','KARBU2',100,25000,2500000),
(9,'FAKTUR 412','KARBU3',100,30000,3000000),
(10,'FAKTUR 021','OLI3',100,50000,5000000),
(11,'FAKTUR REM','RD3',100,50000,5000000),
(12,'FAKTUR REM','RD2',100,40000,4000000),
(13,'FAKTUR REM','RD1',100,45000,4500000),
(14,'FAKTUR REM','RB1',100,40000,4000000),
(15,'FAKTUR REM','RB2',100,38000,3800000),
(16,'FAKTUR REM','RB3',100,45000,4500000),
(17,'FAKTUR LAMPU','LMPB1',100,15000,1500000),
(18,'FAKTUR LAMPU','LMPB2',100,18000,1800000),
(19,'FAKTUR LAMPU','LMPB3',100,20000,2000000),
(20,'FAKTUR LAMPU','LMPD1',100,20000,2000000),
(21,'FAKTUR LAMPU','LMPD2',100,20000,2000000),
(22,'FAKTUR LAMPU','LMPD3',100,30000,3000000);

/*Table structure for table `t_faktur` */

DROP TABLE IF EXISTS `t_faktur`;

CREATE TABLE `t_faktur` (
  `Id_Faktur` varchar(11) NOT NULL,
  `Tanggal` date DEFAULT NULL,
  `Id_Teknisi` varchar(10) DEFAULT NULL,
  `Id_Kasir` varchar(10) DEFAULT NULL,
  `Id_Customer` int DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `No_Polisi` varchar(15) NOT NULL,
  `Total_Sparepart` int DEFAULT NULL,
  `Total_Jasa` int DEFAULT NULL,
  `Total_Bayar` int DEFAULT NULL,
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

insert  into `t_faktur`(`Id_Faktur`,`Tanggal`,`Id_Teknisi`,`Id_Kasir`,`Id_Customer`,`Status`,`No_Polisi`,`Total_Sparepart`,`Total_Jasa`,`Total_Bayar`) values 
('20208-001','2020-08-17','TEK-001','ADM-000',1,'BERES','D 4321 ZXX',45000,60000,105000),
('20208-002','2020-08-17','TEK-001','ADM-000',1,'BERES','D 4321 ZXX',46000,5000,51000);

/*Table structure for table `t_faktur_sparepart_masuk` */

DROP TABLE IF EXISTS `t_faktur_sparepart_masuk`;

CREATE TABLE `t_faktur_sparepart_masuk` (
  `Id_Sprt_Masuk` varchar(15) NOT NULL,
  `Tanggal` date DEFAULT NULL,
  `Total_Harga` int DEFAULT NULL,
  `Id_Pegawai` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Id_Sprt_Masuk`),
  KEY `FK_IdBsparepartFakturSparepartMasuk` (`Total_Harga`),
  KEY `Id_Pegawai` (`Id_Pegawai`),
  CONSTRAINT `t_faktur_sparepart_masuk_ibfk_1` FOREIGN KEY (`Id_Pegawai`) REFERENCES `t_pegawai` (`Id_Pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_faktur_sparepart_masuk` */

insert  into `t_faktur_sparepart_masuk`(`Id_Sprt_Masuk`,`Tanggal`,`Total_Harga`,`Id_Pegawai`) values 
('COBA1','2020-08-02',2450000,'ADM-000'),
('FAKTUR 021','2020-08-17',5000000,'ADM-000'),
('FAKTUR 102','2020-08-17',7000000,'ADM-000'),
('FAKTUR 412','2020-08-17',8000000,'ADM-000'),
('FAKTUR LAMPU','2020-08-17',12300000,'ADM-000'),
('FAKTUR REM','2020-08-17',25800000,'ADM-000'),
('FAKTURMASUK2','2020-08-15',1200000,'SPR-000');

/*Table structure for table `t_jenis_jasa` */

DROP TABLE IF EXISTS `t_jenis_jasa`;

CREATE TABLE `t_jenis_jasa` (
  `Id_Jasa` int NOT NULL AUTO_INCREMENT,
  `Nama_Jasa` varchar(30) DEFAULT NULL,
  `Id_Jenis` int DEFAULT NULL,
  `Harga_Jasa` int DEFAULT NULL,
  PRIMARY KEY (`Id_Jasa`),
  KEY `FK_IdJenisJasa` (`Id_Jenis`),
  CONSTRAINT `t_jenis_jasa_ibfk_1` FOREIGN KEY (`Id_Jenis`) REFERENCES `t_jenis_motor` (`Id_Jenis`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `t_jenis_jasa` */

insert  into `t_jenis_jasa`(`Id_Jasa`,`Nama_Jasa`,`Id_Jenis`,`Harga_Jasa`) values 
(1,'SERVICE RINGAN MATIC',2,60000),
(2,'SERVICE RINGAN GIGI',1,70000),
(3,'SERVICE RINGAN SPORT',3,80000),
(4,'SERVICE BERAT GIGI',1,150000),
(5,'SERVICE BERAT MATIC',2,120000),
(6,'SERVICE BERAT SPORT',3,200000),
(7,'GANTI OLI GIGI',1,5000),
(8,'GANTI OLI MATIC',2,5000),
(9,'GANTI OLI SPORT',3,5000),
(10,'SERVICE INJEKSI GIGI',1,60000),
(11,'SERVICE INJEKSI MATIC',2,50000),
(12,'SERVICE INJEKSI SPORT',3,80000),
(13,'SERVICE CVT MATIC',2,200000),
(14,'TUNE UP GIGI',1,80000),
(15,'TUNE UP MATIC',2,75000),
(16,'TUNE UP SPORT',3,150000),
(17,'THROTTLE BODY GIGI',1,85000),
(18,'THROTTLE BODY MATIC',2,80000),
(19,'THROTTLE BODY SPORT',3,100000);

/*Table structure for table `t_jenis_motor` */

DROP TABLE IF EXISTS `t_jenis_motor`;

CREATE TABLE `t_jenis_motor` (
  `Id_Jenis` int NOT NULL AUTO_INCREMENT,
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
  `Id_Jenis` int DEFAULT NULL,
  `Stok` int DEFAULT NULL,
  `Harga_Sparepart` int DEFAULT NULL,
  PRIMARY KEY (`Id_Sparepart`),
  KEY `FK_IdJenisSparepart` (`Id_Jenis`),
  CONSTRAINT `t_jenis_sparepart_ibfk_1` FOREIGN KEY (`Id_Jenis`) REFERENCES `t_jenis_motor` (`Id_Jenis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_jenis_sparepart` */

insert  into `t_jenis_sparepart`(`Id_Sparepart`,`Nama_Sparepart`,`Id_Jenis`,`Stok`,`Harga_Sparepart`) values 
('BUSI1','BUSI GIGI',1,200,15000),
('BUSI2','BUSI MATIC',2,200,15000),
('BUSI3','BUSI SPORT',3,200,20000),
('KARBU1','KARBURATOR GIGI',1,100,30000),
('KARBU2','KARBURATOR MATIC',2,100,28000),
('KARBU3','KARBURATOR SPORT',3,100,35000),
('LMPB1','LAMPU MOTOR BELAKANG GIGI',1,100,22000),
('LMPB2','LAMPU MOTOR BELAKANG MATIC',2,100,24000),
('LMPB3','LAMPU MOTOR BELAKANG SPORT',3,100,26000),
('LMPD1','LAMPU MOTOR DEPAN GIGI',1,100,28000),
('LMPD2','LAMPU MOTOR DEPAN MATIC',2,100,25000),
('LMPD3','LAMPU MOTOR DEPAN SPORT',3,100,35000),
('OLI1','YAMALUBE POWER MATIC',2,69,46000),
('OLI2','YAMALUBE POWER GIGI',1,30,50000),
('OLI3','YAMBALU POWER SPORT',3,100,65000),
('RB1','REM BELAKANG GIGI',1,100,45000),
('RB2','REM BELAKANG MATIC',2,100,40000),
('RB3','REM BELAKANG SPORT',3,100,50000),
('RD1','REM DEPAN GIGI',1,100,50000),
('RD2','REM DEPAN MATIC',2,99,45000),
('RD3','REM DEPAN SPORT',3,100,55000);

/*Table structure for table `t_login` */

DROP TABLE IF EXISTS `t_login`;

CREATE TABLE `t_login` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `level` varchar(20) DEFAULT NULL,
  `Id_Pegawai` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `Id_Pegawai` (`Id_Pegawai`),
  CONSTRAINT `t_login_ibfk_1` FOREIGN KEY (`Id_Pegawai`) REFERENCES `t_pegawai` (`Id_Pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_login` */

insert  into `t_login`(`username`,`password`,`level`,`Id_Pegawai`) values 
('ADMIN','ADMIN','ADMIN','ADM-000'),
('KASIR','KASIR','KASIR','KSR-000'),
('SPAREPART','SPAREPART','SPAREPART','SPR-000');

/*Table structure for table `t_motor` */

DROP TABLE IF EXISTS `t_motor`;

CREATE TABLE `t_motor` (
  `No_Polisi` varchar(15) NOT NULL,
  `No_Rangka` varchar(20) NOT NULL,
  `No_Mesin` varchar(20) NOT NULL,
  `Id_Customer` int DEFAULT NULL,
  `Id_Tipe` int DEFAULT NULL,
  PRIMARY KEY (`No_Polisi`),
  KEY `FK_IdCustomer` (`Id_Customer`),
  KEY `FK_IdTipe` (`Id_Tipe`),
  CONSTRAINT `FK_IdCustomer` FOREIGN KEY (`Id_Customer`) REFERENCES `t_customer` (`Id_Customer`),
  CONSTRAINT `FK_IdTipe` FOREIGN KEY (`Id_Tipe`) REFERENCES `t_tipe` (`Id_Tipe`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_motor` */

insert  into `t_motor`(`No_Polisi`,`No_Rangka`,`No_Mesin`,`Id_Customer`,`Id_Tipe`) values 
('D 1024 DDD','AFIJO425OI7879WR8723','HOSFHO23598275AFOHO3',18,21),
('D 1234 ZXX','SADFVDFQW42498344231','ASDFZXCVNCBVDFGHQRWE',10118227,11),
('D 2948 DFS','230498DFSKP235980DSF','830925KSD0FK90385034',14,16),
('D 3123 AAA','FJOSDFAUOIUGUOF31231','FIO4YY8AFSHJ24H837AK',2,14),
('d 3404 XAD','ASFOJO3487097SJ0407S','ASFF39FU98D7F3FDOSFD',20,25),
('D 3849 DSA','JDSFJO24UOIJDASJ3470','HKDHF2568934799ASDJ9',10118226,22),
('D 4231 ABC','JDKLJFI34789IDFHI482','JDFSKL324I78723UASJ9',3,2),
('D 4278 DAS','491840982035FDJODJO2','IAOFU234098ASDI0233A',19,23),
('D 4299 DAA','W0E85902385SFIP23598','AFI30948SDFKPK458050',15,17),
('D 4321 ZXX','ASDF6847CEUNFEHU2347','2134SARFFGJRFKSOFFSL',1,1),
('D 4372 DSA','QIFAI348973HHSDJHF28','JASKLJD2347297939329',4,3),
('D 4454 KJF','FHH352Y73Y57F7YS7Y72','JIDJF8235Y8358YFHAIK',7,6),
('D 4593 SJK','RIQWEUR2387JSDFK2487','ALSJR387IO5UKFJD3570',5,4),
('D 4627 DXA','DFSH23748798FAJ93479','H9HDF3984689YQ6S9DFK',13,15),
('D 4729 DSE','4AHFIAH23746ASDHIH44','AHFSKJH4323986ASFH28',17,19),
('D 4732 HJS','ASHF83Q48973JDGSIJ85','JHSDJH37257SDG357SKO',10,10),
('D 4738 SLK','RKOLKDF9324976JDGS83','LDKF23875SHD3Y75Y7SH',6,5),
('D 4817 JAS','RASFOU23487DFJO23570','HSDIF23596DFHO358792',16,18),
('D 4831 JJJ','948SJDFOJOIUERIO47AS','91JFDSOKW3587ASJDOOD',11,12),
('D 4864 ZDS','RWOEI59234JDSU8374OO','UIUERW975234HJHASIFH',9,9),
('D 4893 ZXX','FADKF029358474ASUO77','HASFU387597DFJH357SY',12,13),
('D 4989 JDA','RJEIJ83Y5NSDUHF34879','IJAS8DF735844J8S824H',8,7);

/*Table structure for table `t_pegawai` */

DROP TABLE IF EXISTS `t_pegawai`;

CREATE TABLE `t_pegawai` (
  `Id_Pegawai` varchar(10) NOT NULL,
  `Nama_Pegawai` varchar(50) DEFAULT NULL,
  `Alamat` varchar(50) DEFAULT NULL,
  `No_Telp` varchar(15) DEFAULT NULL,
  `Bagian` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id_Pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_pegawai` */

insert  into `t_pegawai`(`Id_Pegawai`,`Nama_Pegawai`,`Alamat`,`No_Telp`,`Bagian`) values 
('ADM-000','ADMIN','-','-','ADMIN'),
('KSR-000','KASIR','-','-','KASIR'),
('SPR-000','SPAREPART','-','-','SPAREPART'),
('TEK-001','M. RIDWAN','JL. JAKARTA','089642344123','TEKNISI'),
('TEK-002','STEVEN KURNIAWAN','JL. AHMAD YANI','089831278463','TEKNISI'),
('TEK-003','EKO JULIANTO','JL. DAGO','089237467473','TEKNISI');

/*Table structure for table `t_tipe` */

DROP TABLE IF EXISTS `t_tipe`;

CREATE TABLE `t_tipe` (
  `Id_Tipe` int NOT NULL AUTO_INCREMENT,
  `Nama_Tipe` varchar(25) NOT NULL,
  `Id_Jenis` int NOT NULL,
  PRIMARY KEY (`Id_Tipe`),
  KEY `FK_IdJenis` (`Id_Jenis`),
  CONSTRAINT `t_tipe_ibfk_1` FOREIGN KEY (`Id_Jenis`) REFERENCES `t_jenis_motor` (`Id_Jenis`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;

/*Data for the table `t_tipe` */

insert  into `t_tipe`(`Id_Tipe`,`Nama_Tipe`,`Id_Jenis`) values 
(1,'MIO M3 CW',2),
(2,'NMAX 125CC',2),
(3,'FREEGO',2),
(4,'MIO S',2),
(5,'MIO M3 AKS',2),
(6,'MIO Z',2),
(7,'FINO GRANDE',2),
(9,'FINO PREMIUM',2),
(10,'FINO SPORTY',2),
(11,'YZF R15',3),
(12,'YZF R25',3),
(13,'YZF R25-ABS',3),
(14,'MX KING 150',1),
(15,'ALL NEW R1',3),
(16,'ALL NEW R1M',3),
(17,'MX KING 150 DOXOU',1),
(18,'JUPITER MX 150',1),
(19,'VEGA FORCE',1),
(21,'JUPITER Z1',1),
(22,'MT-15',3),
(23,'VIXION',3),
(24,'VIXION R',3),
(25,'XABRE',3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
