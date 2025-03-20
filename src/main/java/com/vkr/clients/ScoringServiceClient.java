package com.vkr.clients;

import com.vkr.models.LoanApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ScoringServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.scoring-service}")
    private String scoringServiceUrl;

    public void sendForScoring(LoanApplication loanApplication) {
        String url = scoringServiceUrl + "/scoring";
        restTemplate.postForObject(url, loanApplication, Void.class);
        log.info("Отправка заявки в ScoringService: {}", loanApplication.getLoanApplicationId());
    }
}

