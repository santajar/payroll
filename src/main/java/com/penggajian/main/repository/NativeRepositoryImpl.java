package com.penggajian.main.repository;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class NativeRepositoryImpl implements NativeRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findRport(String date, String date1) {
		// TODO Auto-generated method stub
		Session ss = sessionFactory.getCurrentSession();
		Query q = ss.createSQLQuery("SELECT p.nama_pegawai, p.nomor_rekening, g.pph21, g.gaji_bersih, g.jumlah_potongan, YEAR(g.tanggal) as tanggal_gaji, MONTH(g.tanggal) as bulan "+ 
			"from gaji g LEFT JOIN pegawai p ON "+
			"g.pegawai_nip = p.nip "+
			"WHERE g.tanggal BETWEEN :date AND :date1");
		q.setParameter("date1", date1);
		q.setParameter("date", date);
		q.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String,Object>> aliasToValueMapList=q.list();
		System.out.println(aliasToValueMapList);

		return aliasToValueMapList;
	}

	

	

}
