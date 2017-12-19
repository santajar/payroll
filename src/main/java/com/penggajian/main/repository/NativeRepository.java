package com.penggajian.main.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.penggajian.main.entity.Gaji;

public interface NativeRepository extends CrudRepository<Gaji, String> {
	
	@Query(nativeQuery=true, value="SELECT p.nama_pegawai, p.nomor_rekening, g.pph21, g.gaji_bersih, g.jumlah_potongan, YEAR(g.tanggal) as tanggal_gaji, MONTH(g.tanggal) as bulan "+ 
			"from gaji g LEFT JOIN pegawai p ON "+
			"g.pegawai_nip = p.nip "+
			"WHERE g.tanggal BETWEEN :date AND :date1")
  public List<Map<String,Object>> findReport(@Param("date") String date,@Param("date1") String date1);

}
