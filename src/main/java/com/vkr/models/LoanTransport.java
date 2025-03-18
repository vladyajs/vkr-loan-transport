package com.vkr.models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class LoanTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanApplicationId;

    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private String currentStep;

    // Конструкторы, геттеры и сеттеры

    public void sendToNextService() {
        // Логика передачи заявки в следующий сервис
    }

    public void updateStatus(String status) {
        this.status = status;
        this.lastUpdatedAt = LocalDateTime.now();
    }
}

