package com.vkr.clients;

import com.vkr.models.LoanApplication;
import com.vkr.models.ScoringResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ScoringServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.scoring-service}")
    private String scoringServiceUrl;

    public ScoringResult sendForScoring(LoanApplication loanApplication) {
        String url = scoringServiceUrl + "/scoring/calculate";

        try {
            log.info("Отправка заявки в ScoringService: {}", loanApplication.getLoanApplicationId());
            return restTemplate.postForObject(url, loanApplication, ScoringResult.class);
        } catch (HttpClientErrorException e) {
            log.error("Scoring error: {} Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        }
    }
}

