package com.penggajian.main.entity;

import java.io.Serializable;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;


@Entity
@Table(name="transaksi")
@NamedQuery(name="Transaksi.findAll", query="SELECT p FROM Transaksi p")
public class Transaksi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int no_gaji;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date tanggal;
	
	private Integer pph21;
	private Integer gaji_kotor;
	private Integer jumlah_potongan;
	private Integer gaji_bersih;
	private String password_enkrip;
	
	@ManyToOne
	@JoinColumn(name="FK_nip")
	private Pegawai pegawai;

	public Transaksi() {
	}
	public int getNo_gaji() {
		return this.no_gaji;
	}
	public void setNo_gaji(int no_gaji) {
		this.no_gaji = no_gaji;
	}
	public Date getTanggal() {
		return this.tanggal;
	}
	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}
	public Integer getPph21() {
		return this.pph21;
	}
	public void setPph21(Integer pph21) {
		this.pph21 = pph21;
	}
	public Integer getGaji_kotor() {
		return this.gaji_kotor;
	}
	public void setGaji_kotor(Integer gaji_kotor) {
		this.gaji_kotor = gaji_kotor;
	}
	public Integer getJumlah_potongan() {
		return this.jumlah_potongan;
	}
	public void setJumlah_potongan(Integer jumlah_potongan) {
		this.jumlah_potongan = jumlah_potongan;
	}
	public Integer getGaji_bersih() {
		return this.gaji_bersih;
	}
	public void setGaji_bersih(Integer gaji_bersih) {
		this.gaji_bersih = gaji_bersih;
	}
	public String getPassword_enkrip() {
		return this.password_enkrip;
	}
	public void setPassword_enkrip(String password_enkrip) {
		this.password_enkrip = password_enkrip;
	}
	public Pegawai getPegawai() {
		return this.pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}
}