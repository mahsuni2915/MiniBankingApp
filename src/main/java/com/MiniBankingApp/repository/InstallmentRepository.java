package com.MiniBankingApp.repository;


import com.MiniBankingApp.entity.Installment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
}