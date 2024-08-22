package com.MiniBankingApp.utils;

import com.MiniBankingApp.entity.Credit;
import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.repository.InstallmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



class LateFeeSchedulerTest {
    @Mock
    private InstallmentRepository installmentRepository;

    @InjectMocks
    private LateFeeScheduler lateFeeScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void updateLateFees() {
        LocalDate today = LocalDate.now();
        Credit credit = new Credit();
        credit.setInterestRate(new BigDecimal("5.0"));  // Örnek bir faiz oranı

        Installment installment1 = new Installment();
        installment1.setDueDate(today.minusDays(5));  // 5 gün gecikmiş
        installment1.setAmount(new BigDecimal("1000.00"));
        installment1.setPaid(false);
        installment1.setLateFee(BigDecimal.ZERO);
        installment1.setCredit(credit);

        List<Installment> overdueInstallments = Arrays.asList(installment1);

        when(installmentRepository.findAllByDueDateBeforeAndLateFeeEqualsAndPaidFalse(today, BigDecimal.ZERO))
                .thenReturn(overdueInstallments);

        lateFeeScheduler.updateLateFees();
        ArgumentCaptor<Installment> installmentCaptor = ArgumentCaptor.forClass(Installment.class);
        verify(installmentRepository, times(1)).save(installmentCaptor.capture());
        Installment savedInstallment = installmentCaptor.getValue();
        BigDecimal expectedLateFee = new BigDecimal("0.70");  // Hesaplanmış örnek geç ödeme ücreti
        assertEquals(expectedLateFee, savedInstallment.getLateFee());
    }

    }

