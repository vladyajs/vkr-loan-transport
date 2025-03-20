package com.vkr.services;

import com.vkr.models.LoanApplication;
import com.vkr.models.enums.ApplicationStatus;
import com.vkr.repositories.LoanApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationProcessor loanApplicationProcessor;
    private final AuditLogService auditLogService;

    @Transactional
    public Long submitLoanApplication(@Valid LoanApplication loanApplication) {
        loanApplicationRepository.save(loanApplication);
        loanApplicationRepository.flush();
        auditLogService.logAction("Создание заявки", loanApplication);
        // Отправляем на обработку
        loanApplicationProcessor.processApplication(loanApplication);

        return loanApplication.getLoanApplicationId();
    }

    @Transactional
    public LoanApplication updateApplicationStatus(Long loanApplicationId, ApplicationStatus status) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));

        if (loanApplication.getApplicationStatus().getValue().equals(ApplicationStatus.APPROVED.getValue())
                && status.equals(ApplicationStatus.REJECTED)) {
            throw new RuntimeException("Невозможно изменить статус с 'Одобрена' на 'Отклонена'");
        }

        loanApplication.updateStatus(status);
        auditLogService.logAction("Изменение статуса заявки на " + status.getValue(), loanApplication);
        return loanApplicationRepository.save(loanApplication);
    }

    public LoanApplication getLoanApplication(Long loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));
    }

    public List<LoanApplication> getAllLoanApplications() {
        return loanApplicationRepository.findAll();
    }
}
