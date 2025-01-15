package com.yaashall.aopaudit.repository;

import com.yaashall.aopaudit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yassine CHALH
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}