package com.vkr.services;

import com.vkr.models.AuditLog;
import com.vkr.models.LoanApplication;
import com.vkr.repositories.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Логируем действия в системе
    @Transactional
    public void logAction(String action, LoanApplication loanApplication) {
        AuditLog auditLog = new AuditLog();
        auditLog.setLoanApplication(loanApplication);
        auditLog.setAction(action);
        auditLog.setActionTimestamp(LocalDateTime.now());
        auditLog.setUserId(loanApplication.getApplicant().getApplicantId());  // Для примера, предполагаем, что заявка имеет связь с пользователем

        // Сохраняем лог в базе данных
        auditLogRepository.save(auditLog);

        // Логируем в консоль для отладки
        log.info("Action logged: {} for LoanApplication ID: {}", action, loanApplication.getLoanApplicationId());
    }
}

