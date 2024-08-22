package com.MiniBankingApp.service;
import com.MiniBankingApp.entity.Installment;
import com.MiniBankingApp.repository.InstallmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class InstallmentService {
    @Autowired
    private InstallmentRepository installmentRepository;

    public Installment getInstallmentById(Long id) {
        Optional<Installment> installmentOptional = installmentRepository.findById(id);
        if (installmentOptional.isPresent()) {
            return installmentOptional.get();
        } else {
            throw new RuntimeException("Installment not found with id: " + id);
        }
    }
}
