package com.penggajian.main.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pegawai database table.
 * 
 */
@Entity
@NamedQuery(name="Pegawai.findAll", query="SELECT p FROM Pegawai p")
public class Pegawai implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int nip;

	private String email;

	@Column(name="nama_pegawai")
	private String nama_pegawai;

	@Column(name="no_ktp")
	private String no_ktp;

	@Column(name="nomor_rekening")
	private String nomor_rekening;

	private String npwp;

	private String telpon;

	//bi-directional many-to-one association to Gaji
	@OneToMany(mappedBy="pegawai",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Gaji> gajis;

	public Pegawai() {
	}

	public int getNip() {
		return this.nip;
	}

	public void setNip(int nip) {
		this.nip = nip;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNama_pegawai() {
		return nama_pegawai;
	}

	public void setNama_pegawai(String nama_pegawai) {
		this.nama_pegawai = nama_pegawai;
	}	

	public String getNo_ktp() {
		return no_ktp;
	}

	public void setNo_ktp(String no_ktp) {
		this.no_ktp = no_ktp;
	}

	public String getNomor_rekening() {
		return nomor_rekening;
	}

	public void setNomor_rekening(String nomor_rekening) {
		this.nomor_rekening = nomor_rekening;
	}

	public String getNpwp() {
		return this.npwp;
	}

	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}

	public String getTelpon() {
		return this.telpon;
	}

	public void setTelpon(String telpon) {
		this.telpon = telpon;
	}

	public List<Gaji> getGajis() {
		return this.gajis;
	}

	public void setGajis(List<Gaji> gajis) {
		this.gajis = gajis;
	}

	
	

}