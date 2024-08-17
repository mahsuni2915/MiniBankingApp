package com.MiniBankingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;


    @Column(name = "installment_count", nullable = false)
    private int installmentCount;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;


    @ManyToOne
    @JoinColumn(name = "banking_user_id", nullable = false)
    private BankingUser bankingUser;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Installment> installments;

    @Column(nullable = false)
    private BigDecimal interestRate;

    @Column(nullable = false)
    private BigDecimal lateFee;

    @Column(nullable = false)
    private boolean late;

    public void calculateLateFee(LocalDate currentDate) {
        if (currentDate.isAfter(createdAt) && !late) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(createdAt, currentDate);
            BigDecimal dailyInterest = interestRate.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
            BigDecimal lateFeePerDay = dailyInterest.multiply(amount).divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP);
            this.lateFee = lateFeePerDay.multiply(BigDecimal.valueOf(daysLate));
            this.late = true;
        }
    }
}