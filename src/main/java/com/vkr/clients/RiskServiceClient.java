package com.vkr.clients;

import com.vkr.models.LoanApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RiskServiceClient {

    // Пример метода для отправки заявки в RiskService
    public void sendForRiskAssessment(LoanApplication loanApplication) {
        // Логика отправки заявки в RiskService для оценки рисков
        log.info("Отправка заявки в RiskService для оценки рисков: " + loanApplication.getLoanApplicationId());
    }
}

