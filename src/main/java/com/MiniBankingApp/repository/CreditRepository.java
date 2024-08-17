package com.MiniBankingApp.repository;

import com.MiniBankingApp.entity.Credit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    @Query("SELECT c FROM Credit c WHERE c.bankingUser.id = :userId")
    List<Credit> getCreditsByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Credit c WHERE c.bankingUser.id = :userId")
    Page<Credit> getCreditsByUserId(@Param("userId") Long userId, Pageable pageable);
}