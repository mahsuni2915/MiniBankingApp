package com.MiniBankingApp.service;

import com.MiniBankingApp.entity.BankingUser;
import com.MiniBankingApp.entity.Credit;
import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.repository.BankingUserRepository;
import com.MiniBankingApp.repository.CreditRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreditService {
    private final CreditRepository creditRepository;

    private final BankingUserRepository bankingUserRepository;

    public CreditService(CreditRepository creditRepository, BankingUserRepository bankingUserRepository) {
        this.creditRepository = creditRepository;
        this.bankingUserRepository = bankingUserRepository;
    }

    @Transactional
    public void calculateLateFeesForAllCredits() {
        List<Credit> credits = creditRepository.findAll();

        LocalDate currentDate = LocalDate.now();

        for (Credit credit : credits) {
            credit.calculateLateFee(currentDate);
            creditRepository.save(credit);
        }
    }

    public List<Credit> getAllCredit(Long userId) {
        return creditRepository.getCreditsByUserId(userId);
    }

    public Page<Credit> getUserCredits(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("amount").descending());
        return creditRepository.getCreditsByUserIdPageble(userId, pageable);
    }

    public Page<Credit> getCreditsByUserIdAndFilters(Long userId, Integer status, LocalDateTime date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return creditRepository.findCreditsByUserIdAndFilters(userId, status, date, pageable);
    }


    public Credit createCredit(Long userId, BigDecimal amount, int installmentCount) {


        Credit credit = new Credit();
        credit.setAmount(amount);
        credit.setInstallmentCount(installmentCount);

        Optional<BankingUser> bankingUser = bankingUserRepository.findById(userId);

        bankingUser.ifPresentOrElse(
                credit::setBankingUser,
                () -> { throw new EntityNotFoundException("User not found with ID: " + userId); }
        );

        List<Installment> installments = new ArrayList<>();
        BigDecimal installmentAmount = amount.divide(BigDecimal.valueOf(installmentCount));
        LocalDate countDate = LocalDate.now();

        for (int i = 0; i < installmentCount; i++) {
            countDate = countDate.plusDays(30);
            if (countDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                countDate = countDate.plusDays(2);
            } else if (countDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                countDate = countDate.plusDays(1);
            }

            Installment installment = new Installment();
            installment.setDueDate(countDate);
            installment.setAmount(installmentAmount);
            installment.setBalance(installmentAmount);
            installment.setCredit(credit);
            installments.add(installment);
        }
        credit.setInstallments(installments);
        return creditRepository.save(credit);
    }
}