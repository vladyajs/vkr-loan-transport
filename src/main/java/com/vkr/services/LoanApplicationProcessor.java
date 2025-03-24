package com.vkr.services;

import com.vkr.clients.NotificationServiceClient;
import com.vkr.clients.RiskServiceClient;
import com.vkr.clients.ScoringServiceClient;
import com.vkr.models.LoanApplication;
import com.vkr.models.ScoringResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanApplicationProcessor {

    private final ScoringServiceClient scoringServiceClient;
    private final RiskServiceClient riskServiceClient;
    private final NotificationServiceClient notificationServiceClient;

    @Async
    @Transactional
    public void processApplication(LoanApplication loanApplication) {
        try {
            sendToScoringService(loanApplication);
            sendForRiskAssessment(loanApplication);
            /*sendNotification(loanApplication);*/
            log.info("Processing application {}. ОТПРАВИЛИ ВСЮДУ", loanApplication.getApplicant());
        } catch (Exception e) {
            log.error("Ошибка в обработке заявки: {}", loanApplication.getLoanApplicationId(), e);
        }
    }

    private void sendToScoringService(LoanApplication loanApplication) {
        try {
            ScoringResult res = scoringServiceClient.sendForScoring(loanApplication);
            if (res != null) {
                loanApplication.setScore(res.getScore());
                notificationServiceClient.sendNotification(loanApplication);
                log.info("recieved scoring result for loanApp with id {}: {}",loanApplication.getLoanApplicationId(), res);
            }
            log.info("Заявка {} отправлена на скоринг", loanApplication.getLoanApplicationId());
        } catch (Exception e) {
            log.error("Ошибка отправки в ScoringService: {}", loanApplication.getLoanApplicationId(), e);
        }
    }

    private void sendForRiskAssessment(LoanApplication loanApplication) {
        try {
            riskServiceClient.sendForRiskAssessment(loanApplication);
            log.info("Заявка {} отправлена на оценку рисков", loanApplication.getLoanApplicationId());
        } catch (Exception e) {
            log.error("Ошибка отправки в RiskService: {}", loanApplication.getLoanApplicationId(), e);
        }
    }

    private void sendNotification(LoanApplication loanApplication) {
        try {
            notificationServiceClient.sendNotification(loanApplication);
            log.info("Уведомление отправлено заявителю: {}", loanApplication.getLoanApplicationId());
        } catch (Exception e) {
            log.error("Ошибка при отправке уведомления: {}", loanApplication.getLoanApplicationId(), e);
        }
    }
}
