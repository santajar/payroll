package com.penggajian.main.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the password database table.
 * 
 */
@Entity
@NamedQuery(name="Password.findAll", query="SELECT p FROM Password p")
public class Password implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String bulan;

	@Column(name="password_enkrip")
	private String passwordEnkrip;

	private String tahun;

	//bi-directional many-to-one association to Gaji
	@JsonIgnore
	@OneToMany(mappedBy="password",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Gaji> gajis;

	public Password() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBulan() {
		return this.bulan;
	}

	public void setBulan(String bulan) {
		this.bulan = bulan;
	}

	public String getPasswordEnkrip() {
		return this.passwordEnkrip;
	}

	public void setPasswordEnkrip(String passwordEnkrip) {
		this.passwordEnkrip = passwordEnkrip;
	}

	public String getTahun() {
		return this.tahun;
	}

	public void setTahun(String tahun) {
		this.tahun = tahun;
	}

	public List<Gaji> getGajis() {
		return this.gajis;
	}

	public void setGajis(List<Gaji> gajis) {
		this.gajis = gajis;
	}

	

}