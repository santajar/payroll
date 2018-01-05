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
	public List<Map<String, Object>> findRport(String bulan, String tahun, String password) {
		// TODO Auto-generated method stub
		Session ss = sessionFactory.getCurrentSession();
		Query q = ss.createSQLQuery("SELECT p.nama_pegawai, p.nomor_rekening, g.pph21, g.gaji_bersih, g.jumlah_potongan, YEAR(g.tanggal) as tanggal_gaji, MONTHNAME(g.tanggal) as bulan "+ 
			"from gaji g LEFT JOIN pegawai p ON "+
			"g.pegawai_nip = p.nip LEFT JOIN password ps ON\n" + 
			"ps.id = g.password_id "+
			"WHERE MONTH(g.tanggal) = :bulan AND YEAR(g.tanggal) = :tahun AND ps.password_enkrip=:password");
		q.setParameter("bulan", bulan);
		q.setParameter("tahun", tahun);
		q.setParameter("password", password);
		q.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String,Object>> aliasToValueMapList=q.list();
		System.out.println(aliasToValueMapList);

		return aliasToValueMapList;
	}

	
	

	

}
