package com.vkr.repositories;

import com.vkr.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // Можно добавить дополнительные методы для поиска по различным критериям, если потребуется
}

