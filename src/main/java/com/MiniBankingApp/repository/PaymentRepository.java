package com.MiniBankingApp.repository;

import com.MiniBankingApp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}