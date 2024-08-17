package com.MiniBankingApp.service;

import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.repository.InstallmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class InstallmentService {
    private InstallmentRepository installmentRepository;


    @Transactional
    public void payInstallment(Long installmentId, BigDecimal paymentAmount) {
        Installment installment = installmentRepository.findById(installmentId)
                .orElseThrow(() -> new IllegalArgumentException("Installment not found with ID: " + installmentId));

        if (installment.isPaid()) {
            throw new IllegalStateException("Installment is already fully paid.");
        }

        installment.applyPayment(paymentAmount);
        installmentRepository.save(installment);
    }
}
