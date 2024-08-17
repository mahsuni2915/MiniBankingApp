package com.MiniBankingApp.utils;

import com.MiniBankingApp.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LateFeeScheduler {

    @Autowired
    private CreditService creditService;

    @Scheduled(cron = "0 0 0 * * ?")  // Works every midnight
    public void scheduleLateFeeCalculation() {
        creditService.calculateLateFeesForAllCredits();
    }
}