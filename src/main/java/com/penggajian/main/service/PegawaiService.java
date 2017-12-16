package com.penggajian.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penggajian.main.entity.Pegawai;
import com.penggajian.main.repository.PegawaiRepository;


@Service
@Transactional
public class PegawaiService {
	
	@Autowired private PegawaiRepository pegawaiRepository;
	
	public Pegawai createPegawai(Pegawai pegawai){
		
		return pegawaiRepository.save(pegawai);
		
	}	
	
	public Pegawai deletePegawai(Pegawai pegawai){
		
		 pegawaiRepository.delete(pegawai);
		
		 return pegawai;
		
	}	
	
	public List<Pegawai> FindAll(){
		
		return pegawaiRepository.findAll();
	}
	
	public Pegawai getPegawaiID(int nip){
		
		return pegawaiRepository.findOne(nip);
	}

}
