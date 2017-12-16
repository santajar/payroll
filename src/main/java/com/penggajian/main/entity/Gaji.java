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
	private String no_gaji;

	@Column(name="gaji_bersih")
	private String gajiBersih;

	@Column(name="gaji_kotor")
	private String gajiKotor;

	@Column(name="jumlah_potongan")
	private String jumlahPotongan;

	@Column(name="password_enkrip")
	private String passwordEnkrip;

	private String pph21;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date tanggal;

	//bi-directional many-to-one association to Pegawai
	@ManyToOne
	private Pegawai pegawai;

	public Gaji() {
	}

	public String getNo_gaji() {
		return no_gaji;
	}

	public void setNo_gaji(String no_gaji) {
		this.no_gaji = no_gaji;
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

	public String getPasswordEnkrip() {
		return this.passwordEnkrip;
	}

	public void setPasswordEnkrip(String passwordEnkrip) {
		this.passwordEnkrip = passwordEnkrip;
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

	@Override
	public String toString() {
		return "Gaji [no_gaji=" + no_gaji + ", gajiBersih=" + gajiBersih + ", gajiKotor=" + gajiKotor
				+ ", jumlahPotongan=" + jumlahPotongan + ", passwordEnkrip=" + passwordEnkrip + ", pph21=" + pph21
				+ ", tanggal=" + tanggal + ", pegawai=" + pegawai + "]";
	}

}