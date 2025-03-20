package com.vkr.models;

import com.vkr.models.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
@Getter
@Setter
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanApplicationId;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    @NotNull(message = "Заявитель не может быть пустым")
    private Applicant applicant;

    @NotNull(message = "Сумма кредита не может быть пустой")
    @DecimalMin(value = "0.01", message = "Сумма кредита должна быть больше нуля")
    @Column(nullable = false)
    private BigDecimal loanAmount;

    @NotNull(message = "Срок кредита не может быть пустым")
    @Min(value = 1, message = "Срок кредита должен быть больше нуля")
    @Column(nullable = false)
    private Integer loanTerm;

    @NotNull(message = "Цель кредита не может быть пустой")
    @Column(nullable = false)
    private String loanPurpose;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)  // Используем EnumType.STRING для сохранения значения enum
    @Column(nullable = false)  // Добавляем аннотацию для столбца в базе данных
    private ApplicationStatus applicationStatus;

    private String approvalStatus;
    private Integer score;
    private String riskAssessment;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.applicationStatus = ApplicationStatus.NEW;  // Устанавливаем начальный статус через enum
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(ApplicationStatus status) {
        this.applicationStatus = status;
    }
}
