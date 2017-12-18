package com.penggajian.main.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.penggajian.main.entity.Gaji;

public interface GajiRepository extends JpaRepository<Gaji, Integer> {
	
	List<Gaji> findBynoGaji(String gaji);
	
	@Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")
    List<Map<String,Object>> findReport(@Param("date") String date,@Param("date1") String date1);

}
