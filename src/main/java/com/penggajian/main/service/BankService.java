package com.penggajian.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penggajian.main.entity.Bank;
import com.penggajian.main.repository.BankRepository;


@Service
@Transactional
public class BankService {
	
	@Autowired private BankRepository bankRepository;
	
	public Bank createBank(Bank bank){
		
		return bankRepository.save(bank);
		
	}	
	
	public Bank deleteBank(Bank bank){
		
		 bankRepository.delete(bank);
		
		 return bank;
		
	}	
	
	public List<Bank> FindAll(){
		
		return bankRepository.findAll();
	}
	
	public Bank getBankID(int id_bank){
		
		return bankRepository.findOne(id_bank);
	}

}
