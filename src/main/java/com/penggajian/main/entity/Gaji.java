package com.penggajian.main.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;


/**
 * The persistent class for the gaji database table.
 * 
 */
@Entity
@NamedQuery(name="Gaji.findAll", query="SELECT g FROM Gaji g")
public class Gaji implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="no_gaji")
	private String noGaji;

	@Column(name="gaji_bersih")
	private String gajiBersih;

	@Column(name="gaji_kotor")
	private String gajiKotor;

	@Column(name="jumlah_potongan")
	private String jumlahPotongan;

	private String pph21;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date tanggal;

	
	//bi-directional many-to-one association to Pegawai
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "pegawai_nip", updatable = false)
	private Pegawai pegawai;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "password_id", updatable = false)
	private Password password;

	public Gaji() {
	}

	public String getNoGaji() {
		return noGaji;
	}

	public void setNoGaji(String noGaji) {
		this.noGaji = noGaji;
	}

	public String getGajiBersih() {
		return this.gajiBersih;
	}

	public void setGajiBersih(String gajiBersih) {
		this.gajiBersih = gajiBersih;
	}

	public String getGajiKotor() {
		return this.gajiKotor;
	}

	public void setGajiKotor(String gajiKotor) {
		this.gajiKotor = gajiKotor;
	}

	public String getJumlahPotongan() {
		return this.jumlahPotongan;
	}

	public void setJumlahPotongan(String jumlahPotongan) {
		this.jumlahPotongan = jumlahPotongan;
	}

	public String getPph21() {
		return this.pph21;
	}

	public void setPph21(String pph21) {
		this.pph21 = pph21;
	}

	public Date getTanggal() {
		return this.tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public Pegawai getPegawai() {
		return this.pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}	

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	

}