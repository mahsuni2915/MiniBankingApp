package com.MiniBankingApp.service;

import com.MiniBankingApp.entity.Credit;
import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.repository.CreditRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditService {

    private final CreditRepository creditRepository;

    public CreditService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public Credit createCredit(Long userId, BigDecimal amount, int installmentCount) {
        Credit credit = new Credit();
        credit.setUserId(userId);
        credit.setAmount(amount);
        credit.setInstallmentCount(installmentCount);

        List<Installment> installments = new ArrayList<>();
        BigDecimal installmentAmount = amount.divide(BigDecimal.valueOf(installmentCount), 2, BigDecimal.ROUND_HALF_UP);
        LocalDate dueDate = LocalDate.now();

        for (int i = 0; i < installmentCount; i++) {
            dueDate = dueDate.plusDays(30);
            if (dueDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                dueDate = dueDate.plusDays(2);
            } else if (dueDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                dueDate = dueDate.plusDays(1);
            }

            Installment installment = new Installment();
            installment.setDueDate(dueDate);
            installment.setAmount(installmentAmount);
            installment.setCredit(credit);
            installments.add(installment);
        }

        credit.setInstallments(installments);
        return creditRepository.save(credit);
    }

    public List<Credit> getCreditsByUserId(Long userId) {
        return creditRepository.findByUserId(userId);
    }
}