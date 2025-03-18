package com.vkr.models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanApplicationId;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    @NotNull(message = "Заявитель не может быть пустым")
    private Applicant applicant;

    @NotNull(message = "Сумма кредита не может быть пустой")
    @DecimalMin(value = "0.01", message = "Сумма кредита должна быть больше нуля")
    private BigDecimal loanAmount;

    @NotNull(message = "Срок кредита не может быть пустым")
    @Min(value = 1, message = "Срок кредита должен быть больше нуля")
    private Integer loanTerm;

    @NotNull(message = "Цель кредита не может быть пустой")
    private String loanPurpose;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String applicationStatus;
    private String approvalStatus;
    private Integer score;
    private String riskAssessment;

    // Конструкторы, геттеры и сеттеры

    public void submitApplication() {
        this.createdAt = LocalDateTime.now();
        this.applicationStatus = "Новая";
    }

    public void updateStatus(String status) {
        this.applicationStatus = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void sendForScoring() {
        // Логика отправки заявки в ScoringService
    }

    public void sendForRiskAssessment() {
        // Логика отправки заявки в RiskService
    }

    public void sendNotification() {
        // Логика отправки уведомлений через NotificationService
    }
}

