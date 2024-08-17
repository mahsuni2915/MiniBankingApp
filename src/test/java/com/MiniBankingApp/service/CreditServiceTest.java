package com.MiniBankingApp.service;

import com.MiniBankingApp.entity.Credit;
import com.MiniBankingApp.repository.BankingUserRepository;
import com.MiniBankingApp.repository.CreditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

class CreditServiceTest {
    private CreditService creditService;

    @Mock
    private BankingUserRepository bankingUserRepository;

    @Mock
    private CreditRepository creditRepository;



    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        creditService = new CreditService(creditRepository, bankingUserRepository);
    }
    @Test
    void createCredit() {
        long id =12;
        Credit credit = new Credit();
        credit.setAmount(BigDecimal.valueOf(10000));
        credit.setInstallmentCount(5);
        when(creditRepository.save(credit)).thenReturn(credit);

        creditService.createCredit(id,BigDecimal.valueOf(10000),5);

    }
}