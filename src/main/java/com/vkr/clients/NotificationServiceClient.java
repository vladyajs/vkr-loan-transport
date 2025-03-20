package com.vkr.clients;

import com.vkr.models.LoanApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class NotificationServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.notification-service}")
    private String notificationServiceUrl;

    public void sendNotification(LoanApplication loanApplication) {
        String url = notificationServiceUrl + "/notify";
        restTemplate.postForObject(url, loanApplication, Void.class);
        log.info("Отправка уведомления о заявке: {}", loanApplication.getLoanApplicationId());
    }
}

