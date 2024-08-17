package com.MiniBankingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "local_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean paid;

    @Column
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "credit_id", nullable = false)
    private Credit credit;

    @Column(nullable = false)
    private BigDecimal balance;

    public void applyPayment(BigDecimal payment) {
        if (balance.compareTo(payment) < 0) {
            throw new IllegalArgumentException("Payment exceeds the remaining balance.");
        }
        balance = balance.subtract(payment);
        if (balance.compareTo(BigDecimal.ZERO) == 0) {
            paid = true;
            paymentDate = LocalDate.now();
        }
    }
}
