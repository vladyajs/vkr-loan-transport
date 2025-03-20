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
public class ScoringResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scoringResultId;

    @ManyToOne
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

    private Integer score;
    private LocalDateTime scoringDate;
    private String scoringStatus;

}

