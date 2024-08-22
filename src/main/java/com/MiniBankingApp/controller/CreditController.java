package com.MiniBankingApp.controller;

import com.MiniBankingApp.entity.Credit;
import com.MiniBankingApp.entity.request.CreditRequest;
import com.MiniBankingApp.service.CreditService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }


    @PostMapping
    public ResponseEntity<Credit> createCredit(@RequestBody @Valid CreditRequest creditRequest) {
        Credit createdCredit = creditService.createCredit(creditRequest);
        return ResponseEntity.ok(createdCredit);
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