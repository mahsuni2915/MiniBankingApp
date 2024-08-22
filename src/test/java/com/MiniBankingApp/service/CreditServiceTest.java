package com.MiniBankingApp.service;

import com.MiniBankingApp.entity.request.CreditRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import com.MiniBankingApp.entity.BankingUser;
import com.MiniBankingApp.entity.Credit;
import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.repository.BankingUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditServiceTest {

    @Mock
    private BankingUserRepository bankingUserRepository;

    @InjectMocks
    private CreditService creditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCreditFromRequest() {
        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setAmount(new BigDecimal("1000.00"));
        creditRequest.setInstallmentCount(12);
        creditRequest.setInterestRate(new BigDecimal("5.0"));

        Credit credit = creditService.createCreditFromRequest(creditRequest);

        assertNotNull(credit);
        assertEquals(LocalDate.now(), credit.getCreatedAt());
        assertEquals(new BigDecimal("1000.00"), credit.getAmount());
        assertEquals(12, credit.getInstallmentCount());
        assertEquals(1, credit.getStatus());
        assertEquals(new BigDecimal("5.0"), credit.getInterestRate());
    }

    @Test
    void testFindBankingUserById_Success() {
        Long userId = 1L;
        BankingUser bankingUser = new BankingUser();
        bankingUser.setId(userId);

        when(bankingUserRepository.findById(userId)).thenReturn(Optional.of(bankingUser));

        BankingUser foundUser = creditService.findBankingUserById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void testFindBankingUserById_UserNotFound() {
        Long userId = 1L;

        when(bankingUserRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            creditService.findBankingUserById(userId);
        });
    }

    @Test
    void testCreateInstallmentsForCredit() {
        Credit credit = new Credit();
        credit.setAmount(new BigDecimal("1200.00"));
        int installmentCount = 12;

        List<Installment> installments = creditService.createInstallmentsForCredit(credit, installmentCount);

        assertNotNull(installments);
        assertEquals(installmentCount, installments.size());

        BigDecimal expectedInstallmentAmount = new BigDecimal("100.00");
        for (Installment installment : installments) {
            assertEquals(expectedInstallmentAmount, installment.getAmount());
            assertEquals(expectedInstallmentAmount, installment.getBalance());
            assertFalse(installment.isPaid());
            assertEquals(credit, installment.getCredit());
            assertNotNull(installment.getDueDate());
        }
    }

    @Test
    void testAdjustDueDate_Weekday() {
        LocalDate weekday = LocalDate.of(2024, 8, 22);

        LocalDate adjustedDate = creditService.adjustDueDate(weekday);

        assertEquals(weekday, adjustedDate);
    }

    @Test
    void testAdjustDueDate_Saturday() {
        LocalDate saturday = LocalDate.of(2024, 8, 24);

        LocalDate adjustedDate = creditService.adjustDueDate(saturday);

        assertEquals(LocalDate.of(2024, 8, 26), adjustedDate);
    }

    @Test
    void testAdjustDueDate_Sunday() {
        LocalDate sunday = LocalDate.of(2024, 8, 25);

        LocalDate adjustedDate = creditService.adjustDueDate(sunday);

        assertEquals(LocalDate.of(2024, 8, 26), adjustedDate);
    }

    @Test
    void testCreateInstallment() {
        Credit credit = new Credit();
        BigDecimal amount = new BigDecimal("100.00");
        LocalDate dueDate = LocalDate.now().plusDays(30);

        Installment installment = creditService.createInstallment(credit, amount, dueDate);

        assertNotNull(installment);
        assertEquals(amount, installment.getAmount());
        assertEquals(amount, installment.getBalance());
        assertEquals(dueDate, installment.getDueDate());
        assertEquals(credit, installment.getCredit());
        assertFalse(installment.isPaid());
    }
}