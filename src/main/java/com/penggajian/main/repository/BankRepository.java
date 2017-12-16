package com.penggajian.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penggajian.main.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer> {

}
