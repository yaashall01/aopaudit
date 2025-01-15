package com.yaashall.aopaudit.aspect;

import com.yaashall.aopaudit.annotation.Loggable;
import com.yaashall.aopaudit.entity.AuditLog;
import com.yaashall.aopaudit.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class AuditLoggingAspect {

    private final HttpServletRequest request;

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLoggingAspect(HttpServletRequest request, AuditLogRepository auditLogRepository) {
        this.request = request;
        this.auditLogRepository = auditLogRepository;
    }

    @Pointcut("execution(* com.yaashall.aopaudit.service.*.*(..))")
    public void serviceLayer() {}

    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String ipAddress = request.getRemoteAddr();
        String methodName = joinPoint.getSignature().getName();
        String arguments = Arrays.toString(joinPoint.getArgs());
        LocalDateTime timestamp = LocalDateTime.now();

        AuditLog auditLog = new AuditLog();
        auditLog.setMethodName(methodName);
        auditLog.setArguments(arguments);
        auditLog.setIpAddress(ipAddress);
        auditLog.setTimestamp(timestamp);
        auditLog.setReturnValue(result != null ? result.toString() : "null");

        auditLogRepository.save(auditLog);
    }

    @Pointcut("@annotation(com.yaashall.aopaudit.annotation.Loggable)")
    public void loggableMethods() {}

    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void logAfterReturningCustomized(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Loggable loggable = signature.getMethod().getAnnotation(Loggable.class);

        String action = loggable.action();
        String ipAddress = request.getRemoteAddr();
        String methodName = signature.getName();
        String arguments = Arrays.toString(joinPoint.getArgs());
        LocalDateTime timestamp = LocalDateTime.now();

        AuditLog auditLog = new AuditLog();
        auditLog.setMethodName(methodName);
        auditLog.setArguments(arguments);
        auditLog.setIpAddress(ipAddress);
        auditLog.setTimestamp(timestamp);
        auditLog.setReturnValue(result != null ? result.toString() : "null");
        auditLog.setAction(action);

        auditLogRepository.save(auditLog);
    }}
