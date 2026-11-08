package com.nfsesandbox.rps.domain;

import java.time.LocalDateTime;

public class Rejection {
    private String code;
    private String reason;
    private LocalDateTime rejectedAt;

    public String getCode() {
        return code;
    }

    public Rejection(String code, String reason, LocalDateTime rejectedAt) {
        this.code = ValidationSupport.requireNonBlank(code, "Code");
        this.reason = ValidationSupport.requireNonBlank(reason, "Reason");
        this.rejectedAt = ValidationSupport.requireNonNull(rejectedAt, "rejectedAt");
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }
}
