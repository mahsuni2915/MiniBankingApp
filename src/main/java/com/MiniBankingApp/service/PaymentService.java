package com.MiniBankingApp.service;

import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.entity.Payment;
import com.MiniBankingApp.repository.InstallmentRepository;
import com.MiniBankingApp.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final InstallmentRepository installmentRepository;

    public PaymentService(PaymentRepository paymentRepository, InstallmentRepository installmentRepository) {
        this.paymentRepository = paymentRepository;
        this.installmentRepository = installmentRepository;
    }

    @Transactional
    public Payment applyPayment(Long installmentId, BigDecimal paymentAmount) {
        Installment installment = installmentRepository.findById(installmentId)
                .orElseThrow(() -> new IllegalArgumentException("Installment not found"));

        if (installment.getAmount().compareTo(paymentAmount) < 0) {
            throw new IllegalArgumentException("Payment exceeds the remaining balance.");
        }
        Payment payment = new Payment();
        payment.setInstallment(installment);
        payment.setAmount(paymentAmount);
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        installment.setBalance(installment.getBalance().subtract(paymentAmount));
        if (installment.getBalance().subtract(paymentAmount).compareTo(BigDecimal.ZERO) == 0){
            installment.setPaid(true);
        }
        installment.setPaymentDate(LocalDate.now());
        installmentRepository.save(installment);

        return savedPayment;
    }
}
