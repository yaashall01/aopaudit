package com.yaashall.aopaudit.entity;

/**
 * @author Yassine CHALH
 */
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String methodName;

    @Column(length = 2000)
    private String arguments;

    private String ipAddress;

    private LocalDateTime timestamp;

    @Column(length = 2000)
    private String returnValue;

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }
}


