package com.MiniBankingApp.controller;

import com.MiniBankingApp.entity.Payment;
import com.MiniBankingApp.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<Payment> applyPayment(@RequestParam Long installmentId, @RequestParam BigDecimal amount) {
        Payment payment = paymentService.applyPayment(installmentId, amount);
        return ResponseEntity.ok(payment);
    }
}