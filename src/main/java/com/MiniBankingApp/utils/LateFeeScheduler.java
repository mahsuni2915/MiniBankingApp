package com.MiniBankingApp.utils;

import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.repository.InstallmentRepository;
import com.MiniBankingApp.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class LateFeeScheduler {

    @Autowired
    private InstallmentRepository  installmentRepository;

    @Scheduled(cron = "0 0 0 * * ?")  // Works every midnight
    public void updateLateFees() {
        LocalDate today = LocalDate.now();
        List<Installment> installments = installmentRepository.findAllByDueDateBeforeAndLateFeeEqualsAndPaidFalse(today, BigDecimal.ZERO);

        for (Installment installment : installments) {
            long daysLate = ChronoUnit.DAYS.between(installment.getDueDate(), today);
            BigDecimal dailyInterest = installment.getCredit().getInterestRate().divide(BigDecimal.valueOf(100)).multiply(installment.getAmount()).divide(BigDecimal.valueOf(360), 2, RoundingMode.HALF_UP);
            BigDecimal lateFee = dailyInterest.multiply(BigDecimal.valueOf(daysLate));
            installment.setLateFee(installment.getLateFee().add(lateFee));
            installmentRepository.save(installment);
        }
    }

}