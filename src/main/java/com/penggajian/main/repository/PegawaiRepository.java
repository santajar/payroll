package com.penggajian.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penggajian.main.entity.Pegawai;

public interface PegawaiRepository extends JpaRepository<Pegawai, Integer> {

}
