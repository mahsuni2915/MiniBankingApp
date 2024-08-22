package com.MiniBankingApp.repository;


import com.MiniBankingApp.entity.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
    List<Installment> findAllByDueDateBeforeAndLateFeeEqualsAndPaidFalse(LocalDate dueDate, BigDecimal lateFee);
}