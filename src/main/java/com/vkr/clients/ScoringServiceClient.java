package com.vkr.clients;

import com.vkr.models.LoanApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScoringServiceClient {

    // Пример метода для отправки заявки в ScoringService
    public void sendForScoring(LoanApplication loanApplication) {
        // Логика отправки заявки в ScoringService
        log.info("Отправка заявки в ScoringService для скоринга: {}", loanApplication.getLoanApplicationId());
    }
}

