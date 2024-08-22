package com.MiniBankingApp.controller;

import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/installments")
public class InstallmentController {
    @Autowired
    private InstallmentService installmentService;

    @GetMapping("/{id}")
    public ResponseEntity<Installment> getInstallmentById(@PathVariable("id") Long id) {
        try {
            Installment installment = installmentService.getInstallmentById(id);
            return new ResponseEntity<>(installment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
