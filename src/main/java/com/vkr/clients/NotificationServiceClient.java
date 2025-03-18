package com.vkr.clients;

import com.vkr.models.LoanApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceClient {

    // Пример метода для отправки уведомления
    public void sendNotification(LoanApplication loanApplication) {
        // Логика отправки уведомления пользователю через внешний сервис
        log.info("Отправка уведомления заявителю о статусе заявки: {}", loanApplication.getLoanApplicationId());
    }
}

