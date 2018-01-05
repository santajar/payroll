package com.penggajian.main.repository;

import java.util.List;
import java.util.Map;


public interface NativeRepository{	
	
	
	public List<Map<String, Object>> findRport(String bulan,String tahun, String password);

	

}
