package com.penggajian.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penggajian.main.entity.Gaji;

public interface GajiRepository extends JpaRepository<Gaji, Integer> {
	
	List<Gaji> findBynoGaji(String gaji);

}
