package com.MiniBankingApp.service;

import com.MiniBankingApp.entity.BankingUser;
import com.MiniBankingApp.entity.Credit;
import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.entity.request.CreditRequest;
import com.MiniBankingApp.repository.BankingUserRepository;
import com.MiniBankingApp.repository.CreditRepository;
import com.MiniBankingApp.repository.InstallmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditService {

    private  final InstallmentRepository installmentRepository;

    private final CreditRepository creditRepository;

    private final BankingUserRepository bankingUserRepository;

    public CreditService(InstallmentRepository installmentRepository, CreditRepository creditRepository, BankingUserRepository bankingUserRepository) {
        this.installmentRepository = installmentRepository;
        this.creditRepository = creditRepository;
        this.bankingUserRepository = bankingUserRepository;
    }

    public List<Credit> getAllCredit(Long userId) {
        return creditRepository.getCreditsByUserId(userId);
    }

    public Page<Credit> getCreditsByUserIdAndFilters(Long userId, Integer status, LocalDateTime date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return creditRepository.findCreditsByUserIdAndFilters(userId, status, date, pageable);
    }


    public Credit createCredit(CreditRequest creditRequest) {

        Credit credit = createCreditFromRequest(creditRequest);
        BankingUser bankingUser = findBankingUserById(creditRequest.getUserId());
        credit.setBankingUser(bankingUser);

        List<Installment> installments = createInstallmentsForCredit(credit, creditRequest.getInstallmentCount());
        credit.setInstallments(installments);

        return creditRepository.save(credit);
    }

    Credit createCreditFromRequest(CreditRequest creditRequest) {
        Credit credit = new Credit();
        credit.setCreatedAt(LocalDate.now());
        credit.setAmount(creditRequest.getAmount());
        credit.setInstallmentCount(creditRequest.getInstallmentCount());
        credit.setStatus(1);
        credit.setInterestRate(creditRequest.getInterestRate());
        return credit;
    }

    BankingUser findBankingUserById(Long userId) {
        return bankingUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }

    List<Installment> createInstallmentsForCredit(Credit credit, int installmentCount) {
        List<Installment> installments = new ArrayList<>();
        BigDecimal installmentAmount = credit.getAmount().divide(BigDecimal.valueOf(installmentCount));
        LocalDate countDate = LocalDate.now();

        for (int i = 0; i < installmentCount; i++) {
            countDate = adjustDueDate(countDate.plusDays(30));
            Installment installment = createInstallment(credit, installmentAmount, countDate);
            installments.add(installment);
        }
        return installments;
    }

    LocalDate adjustDueDate(LocalDate dueDate) {
        if (dueDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return dueDate.plusDays(2);
        } else if (dueDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return dueDate.plusDays(1);
        }
        return dueDate;
    }

    Installment createInstallment(Credit credit, BigDecimal amount, LocalDate dueDate) {
        Installment installment = new Installment();
        installment.setDueDate(dueDate);
        installment.setAmount(amount);
        installment.setBalance(amount);
        installment.setCredit(credit);
        installment.setPaid(false);
        return installment;
    }
}