package com.vkr.services;

import com.vkr.clients.NotificationServiceClient;
import com.vkr.clients.RiskServiceClient;
import com.vkr.clients.ScoringServiceClient;
import com.vkr.models.LoanApplication;
import com.vkr.repositories.LoanApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class LoanApplicationService {

    private LoanApplicationRepository loanApplicationRepository;
    private ScoringServiceClient scoringServiceClient;  // Клиент для взаимодействия с ScoringService
    private RiskServiceClient riskServiceClient;  // Клиент для взаимодействия с RiskService
    private NotificationServiceClient notificationServiceClient;  // Клиент для взаимодействия с NotificationService
    private AuditLogService auditLogService;  // Сервис для логирования действий

    // Метод для создания и подачи заявки
    @Transactional
    public Long submitLoanApplication(@Valid LoanApplication loanApplication) {

        loanApplication.submitApplication();
        loanApplicationRepository.save(loanApplication);

        auditLogService.logAction("creating request", loanApplication);

        processApplicationAsync(loanApplication);

        return loanApplication.getLoanApplicationId();
    }

    @Async
    protected void processApplicationAsync(LoanApplication application) {
        try {
            // step 1
            sendToScoringService(application);
            // step 2
            sendForRiskAssessment(application);
            // step 3
            sendNotification(application);
        } catch (Exception e) {
            log.error("Async processing failed for {}", application.getLoanApplicationId(), e);
        }
    }

    private void sendToScoringService(LoanApplication loanApplication) {
        try {
            scoringServiceClient.sendForScoring(loanApplication);
            log.info("Заявка успешно отправлена на скоринг, ID: {}", loanApplication.getLoanApplicationId());
        } catch (Exception e) {
            log.error("Ошибка при отправке заявки на скоринг, ID: {}", loanApplication.getLoanApplicationId(), e);
            throw new RuntimeException("Не удалось отправить заявку на скоринг");
        }
    }

    private void sendForRiskAssessment(LoanApplication loanApplication) {
        try {
            riskServiceClient.sendForRiskAssessment(loanApplication);
            log.info("Заявка успешно отправлена на оценку рисков, ID: {}", loanApplication.getLoanApplicationId());
        } catch (Exception e) {
            log.error("Ошибка при отправке заявки на оценку рисков, ID: {}", loanApplication.getLoanApplicationId(), e);
            throw new RuntimeException("Не удалось отправить заявку на оценку рисков");
        }
    }

    private void sendNotification(LoanApplication loanApplication) {
        try {
            notificationServiceClient.sendNotification(loanApplication);
            log.info("Уведомление отправлено заявителю, ID: {}", loanApplication.getLoanApplicationId());
        } catch (Exception e) {
            log.error("Ошибка при отправке уведомления заявителю, ID: {}", loanApplication.getLoanApplicationId(), e);
            throw new RuntimeException("Не удалось отправить уведомление заявителю");
        }
    }

    /// Метод для обновления статуса заявки
    @Transactional
    public LoanApplication updateApplicationStatus(Long loanApplicationId, String status) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));

        // Проверка на допустимость изменения статуса
        if (loanApplication.getApplicationStatus().equals("Одобрена") && status.equals("Отклонена")) {
            throw new RuntimeException("Невозможно изменить статус заявки с 'Одобрена' на 'Отклонена'");
        }

        loanApplication.updateStatus(status);

        // Логируем изменение статуса
        auditLogService.logAction("Изменение статуса заявки на " + status, loanApplication);

        return loanApplicationRepository.save(loanApplication);
    }

    // Метод для получения информации о заявке
    public LoanApplication getLoanApplication(Long loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));
    }

    // Метод для отправки заявки на пересмотр
    @Transactional
    public LoanApplication resubmitLoanApplication(Long loanApplicationId) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));

        if (!loanApplication.getApplicationStatus().equals("Отклонена")) {
            throw new RuntimeException("Только отклоненные заявки могут быть отправлены на пересмотр");
        }

        // Переход в статус "На рассмотрении"
        loanApplication.updateStatus("На рассмотрении");

        // Логируем действие
        auditLogService.logAction("Пересмотр заявки", loanApplication);

        // Отправляем заявку на повторное скоринг и оценку рисков
        scoringServiceClient.sendForScoring(loanApplication);
        riskServiceClient.sendForRiskAssessment(loanApplication);

        return loanApplicationRepository.save(loanApplication);
    }

}



