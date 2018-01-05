package com.penggajian.main.repository;

import java.util.List;
import java.util.Map;

import com.penggajian.main.entity.Password;


public interface NativeRepository{	
	
	
	public List<Map<String, Object>> findRport(String date,String date1, String password);

	

}
