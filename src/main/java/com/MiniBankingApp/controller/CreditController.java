package com.MiniBankingApp.controller;

import com.MiniBankingApp.entity.Credit;
import com.MiniBankingApp.service.CreditService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/credits")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping
    public Credit createCredit(@RequestParam Long userId, @RequestParam BigDecimal amount, @RequestParam int installmentCount) {
        return creditService.createCredit(userId, amount, installmentCount);
    }

    @GetMapping
    public Page<Credit> getCreditsByUserIdAndFilters(
            @RequestParam Long userId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) LocalDateTime date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return creditService.getCreditsByUserIdAndFilters(userId, status, date, page, size);
    }

}