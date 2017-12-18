package com.penggajian.main.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penggajian.main.entity.Gaji;
//import com.penggajian.main.entity.Transaksi;
import com.penggajian.main.repository.GajiRepository;
//import com.penggajian.main.repository.TransaksiRepository;

@Service
@Transactional
public class GajiService {
@Autowired private GajiRepository gajiRepository;
	
	public List<Gaji> FindAll(){
		
		return gajiRepository.findAll();
	}
	public Gaji createGaji(Gaji gaji){
		return gajiRepository.save(gaji);
	}	
	
	public Gaji getGajiID(int no_gaji){
		return gajiRepository.findOne(no_gaji);
	}
	
	
	public List<Gaji> findByone(String id) {
		
		return  gajiRepository.findBynoGaji(id);
		
	}
	
	public List<Map<String,Object>> findReport(String date, String date1){
		
		return gajiRepository.findReport(date,date1);
	}
}
