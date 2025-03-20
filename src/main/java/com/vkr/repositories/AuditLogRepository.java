package com.vkr.repositories;

import com.vkr.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByLoanApplicationLoanApplicationId(Long applicationId);
    // Можно добавить дополнительные методы для поиска по различным критериям, если потребуется
}

