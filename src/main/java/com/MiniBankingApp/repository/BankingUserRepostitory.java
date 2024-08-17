package com.MiniBankingApp.repository;

import com.MiniBankingApp.entity.BankingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankingUserRepostitory extends JpaRepository<BankingUser, Long> {
}
