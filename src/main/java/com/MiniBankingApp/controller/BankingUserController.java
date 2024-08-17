package com.MiniBankingApp.controller;

import com.MiniBankingApp.entity.BankingUser;
import com.MiniBankingApp.service.BankingUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class BankingUserController {
    private final BankingUserService bankingUserService;


    public BankingUserController(BankingUserService bankingUserService) {
        this.bankingUserService = bankingUserService;
    }

    @GetMapping
    public List<BankingUser> getAllUsers() {
        return bankingUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankingUser> getUserById(@PathVariable Long id) {
        return bankingUserService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BankingUser createUser(@RequestBody BankingUser bankingUser) {
        return bankingUserService.createUser(bankingUser);
    }

}
