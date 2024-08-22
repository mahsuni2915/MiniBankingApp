package com.MiniBankingApp.service;

import com.MiniBankingApp.entity.BankingUser;
import com.MiniBankingApp.repository.BankingUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BankingUserService {
    private final BankingUserRepository bankingUserRepository;

    public BankingUserService(BankingUserRepository bankingUserRepository) {
        this.bankingUserRepository = bankingUserRepository;
    }

    public List<BankingUser> getAllUsers() {
        return bankingUserRepository.findAll();
    }

    public Optional<BankingUser> getUserById(Long id) {
        return bankingUserRepository.findById(id);
    }

    @Transactional
    public BankingUser createUser(BankingUser bankingUser) {
        return bankingUserRepository.save(bankingUser);
    }
}
