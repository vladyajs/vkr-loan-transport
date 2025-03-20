package com.vkr.clients;

import com.vkr.models.LoanApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RiskServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.risk-service}")
    private String riskServiceUrl;

    public void sendForRiskAssessment(LoanApplication loanApplication) {
        String url = riskServiceUrl + "/risk";
        restTemplate.postForObject(url, loanApplication, Void.class);
        log.info("Отправка заявки в RiskService: {}", loanApplication.getLoanApplicationId());
    }
}

