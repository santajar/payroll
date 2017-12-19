package com.penggajian.main.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.penggajian.main.entity.Gaji;

public interface NativeRepository extends CrudRepository<Gaji, String> {
	
	@Query("SELECT p FROM Gaji p WHERE tanggal between :date and :date1")
  public List<Map<String,Object>> findReport(@Param("date") String date,@Param("date1") String date1);

}
