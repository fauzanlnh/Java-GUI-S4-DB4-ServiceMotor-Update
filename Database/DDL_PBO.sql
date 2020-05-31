SELECT T_Det_Sparepart_Masuk.Id_Sparepart, T_Sparepart.Nama_Sparepart, SUM(T_Det_Sparepart_Masuk.Jmlh_Masuk) AS JmlhMasuk,SUM(T_Det_Sparepart_Masuk.Total_Per_Detail) AS TotalPerBarang
FROM T_Det_Sparepart_Masuk,T_Sparepart 
WHERE T_Sparepart.Id_Sparepart = T_Det_Sparepart_Masuk.Id_Sparepart 
GROUP BY T_Det_Sparepart_Masuk.Id_Sparepart

SELECT T_Det_Sparepart_Masuk.Id_Sparepart, T_Sparepart.Nama_Sparepart, SUM(T_Det_Sparepart_Masuk.Jmlh_Masuk) AS JmlhMasuk,SUM(T_Det_Sparepart_Masuk.Total_Per_Detail) AS TotalPerBarang
FROM T_Det_Sparepart_Masuk,T_Sparepart, T_Faktur_Sparepart_Masuk
WHERE T_Sparepart.Id_Sparepart = T_Det_Sparepart_Masuk.Id_Sparepart AND T_Faktur_Sparepart_Masuk.Id_Sprt_Masuk = T_Det_Sparepart_Masuk.Id_Sprt_Masuk AND T_Faktur_Sparepart_Masuk.Tanggal BETWEEN '2020-05-04' AND  '2020-05-05'
GROUP BY T_Det_Sparepart_Masuk.Id_Sparepart
	
SELECT T_Det_Sparepart.Id_DetSparepart, T_Sparepart.Nama_Sparepart, SUM(T_Det_Sparepart.Qty) AS JmlhPenjualan, SUM(T_Det_Sparepart.Harga) AS TotalPemasukan
FROM T_Det_Sparepart, T_Sparepart
WHERE T_Sparepart.Id_Sparepart = T_Det_Sparepart.Id_Sparepart 
GROUP BY T_Det_Sparepart.Id_Sparepart

SELECT T_Det_Sparepart.Id_DetSparepart, T_Sparepart.Nama_Sparepart, SUM(T_Det_Sparepart.Qty) AS JmlhPenjualan, SUM(T_Det_Sparepart.Harga) AS TotalPemasukan 
FROM T_Det_Sparepart, T_Sparepart, T_Faktur 
WHERE T_Sparepart.Id_Sparepart = T_Det_Sparepart.Id_Sparepart AND T_Faktur.Id_Faktur = T_Det_Sparepart.Id_Faktur
GROUP BY T_Det_Sparepart.Id_Sparepart

//Menu Sparepart
SELECT T_Customer.Nama_Customer, T_Motor.No_Polisi, T_Motor.No_Mesin, T_Motor.No_Rangka, T_Tipe.Nama_Tipe 
FROM T_Motor, T_Customer, T_Tipe
WHERE T_Motor.Id_Customer = T_Customer.Id_Customer AND T_Tipe.Id_Tipe = T_Motor.Id_Tipe;

//Menu Kasir
  //DATA Customer
  SELECT T_Customer.Nama_Customer, T_Motor.No_Polisi, T_Motor.No_Mesin, T_Motor.No_Rangka, T_Tipe.Nama_Tipe 
  FROM T_Motor, T_Customer, T_Tipe
  WHERE T_Motor.Id_Customer = T_Customer.Id_Customer AND T_Tipe.Id_Tipe = T_Motor.Id_Tipe;

  //Get KdJenisMotor
  SELECT * FROM T_Motor, T_Tipe WHERE No_Polisi = "D 1313 ZSD" AND T_Motor.Id_Tipe = T_Tipe.Id_Tipe
  
  // Transasksi
  SELECT * FROM T_Faktur WHERE STATUS = "PROSES"