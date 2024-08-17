package com.MiniBankingApp.controller;

import com.MiniBankingApp.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/installments")
public class InstallmentController {
    @Autowired
    private InstallmentService installmentService;


    @PostMapping("/{installmentId}/pay")
    public String payInstallment(@PathVariable Long installmentId,
                                 @RequestParam BigDecimal paymentAmount) {
        installmentService.payInstallment(installmentId, paymentAmount);
        return "Paid " + paymentAmount + " for installment with ID " + installmentId + ".";
    }
}
