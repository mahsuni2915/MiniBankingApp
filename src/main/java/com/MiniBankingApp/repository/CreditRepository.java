package com.MiniBankingApp.repository;

import com.MiniBankingApp.entity.Credit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    @Query("SELECT c FROM Credit c WHERE c.bankingUser.id = :userId")
    List<Credit> getCreditsByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT c FROM Credit c " +
            "LEFT JOIN FETCH c.installments " +
            "WHERE c.bankingUser.id = :userId " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (:date IS NULL OR c.createdAt >= :date)")
    Page<Credit> findCreditsByUserIdAndFilters(@Param("userId") Long userId,
                                               @Param("status") Integer status,
                                               @Param("date") LocalDateTime date,
                                               Pageable pageable);
}