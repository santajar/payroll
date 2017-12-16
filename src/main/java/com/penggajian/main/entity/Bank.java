package com.penggajian.main.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQuery(name="Bank.findAll", query="SELECT p FROM Bank p")
public class Bank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id_bank;

	
	
	public String nama_bank;
	
	
	
	public String alamat_bank;
	
	public Bank() {
	}

	public int getid_bank() {
		return this.id_bank;
	}

	public void setid_bank(int id_bank) {
		this.id_bank = id_bank;
	}
	
	
	
	
	public String getalamat_bank() {
		return this.alamat_bank ;
	}
	public void setalamat_bank(String alamat_bank) {
		this.alamat_bank = alamat_bank;
	}
	
	public String getnama_bank() {
		return this.nama_bank ;
	}
	public void setnama_bank(String nama_bank) {
		this.nama_bank = nama_bank;
	}
	
	
}