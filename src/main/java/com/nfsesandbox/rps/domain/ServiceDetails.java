package com.nfsesandbox.rps.domain;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ServiceDetails {
    private String code;
    private String description;
    private BigDecimal amount;
    private BigDecimal issRate;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public ServiceDetails(String code, String description, BigDecimal amount, BigDecimal issRate) {
        this.code = requireNonBlank(code,"Code");
        this.description = requireNonBlank(description, "Description");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(resourceBundle.getString("amount.validation"));
        }
        this.amount = amount;
        if (issRate == null || issRate.compareTo(BigDecimal.ZERO) <= 0 || issRate.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new IllegalArgumentException(resourceBundle.getString("issRate.validation"));
        }
        this.issRate = issRate;
    }
    private static String requireNonBlank(String value, String name) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(MessageFormat.format(resourceBundle.getString("validation.required"), name));
        }
        return value.strip();
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getIssRate() {
        return issRate;
    }
}
