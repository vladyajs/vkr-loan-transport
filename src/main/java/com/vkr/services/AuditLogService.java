package com.vkr.services;

import com.vkr.models.AuditLog;
import com.vkr.models.LoanApplication;
import com.vkr.repositories.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public void logAction(String action, LoanApplication loanApplication) {
        AuditLog auditLog = new AuditLog();
        auditLog.setLoanApplication(loanApplication);
        auditLog.setAction(action);
        auditLog.setActionTimestamp(LocalDateTime.now());
        auditLog.setUserId(loanApplication.getApplicant().getApplicantId());

        auditLogRepository.save(auditLog);

        log.info("Action logged: {} for LoanApplication ID: {}", action, loanApplication.getLoanApplicationId());
    }

    public List<AuditLog> getAuditLogsByApplicationId(Long applicationId) {
        return auditLogRepository.findByLoanApplicationLoanApplicationId(applicationId);
    }
}
