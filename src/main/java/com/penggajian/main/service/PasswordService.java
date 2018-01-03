package com.penggajian.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penggajian.main.entity.Password;
import com.penggajian.main.repository.PasswordRepository;

@Service
@Transactional
public class PasswordService {
	@Autowired private PasswordRepository passwordrepo;
	
	public List<Password> findAll(){
		
		return passwordrepo.findAll();
	}
	
	public Password savePass(Password password) {
		
		return passwordrepo.save(password);
	}
	
	public Password deletePass(Password password) {
		passwordrepo.delete(password);
		return password;
	}
	
	public Password FindByone(Integer id) {
		
		return passwordrepo.findOne(id);
	}
	
	public Password FindByonePassword(String tanggal) {
		
		return passwordrepo.FindByonePassword(tanggal);
	}

}
