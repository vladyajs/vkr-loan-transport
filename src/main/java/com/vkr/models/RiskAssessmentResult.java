package com.vkr.models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RiskAssessmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long riskAssessmentResultId;

    @ManyToOne
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

    private String riskLevel;
    private LocalDateTime assessmentDate;
    private String assessmentStatus;

    // Конструкторы, геттеры и сеттеры

    public void evaluateRisk() {
        // Логика оценки рисков
    }

    public void getRiskAssessment() {
        // Логика получения результатов оценки рисков
    }
}

