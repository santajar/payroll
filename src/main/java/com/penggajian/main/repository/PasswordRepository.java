package com.penggajian.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.penggajian.main.entity.Password;

public interface PasswordRepository extends JpaRepository<Password, Integer> {
	
	@Query(
	        value = "SELECT * FROM password t where t.bulan = MONTH(:tanggal) AND t.tahun = YEAR(:tanggal)", 
	        nativeQuery=true
	    )
	public Password FindByonePassword(@Param("tanggal") String tanggal);
	
	Password findByBulanInAndTahun(String bulan, String tahun);
}
