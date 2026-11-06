package com.nfsesandbox.rps.domain;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Rps {
    private String number;
    private String series;
    private LocalDateTime issuedAt;
    private ServiceProvider provider;
    private ServiceCustomer customer;
    private ServiceDetails serviceDetails;
    private RpsStatus status;

    private static final ResourceBundle resourceBundle =
            ResourceBundle.getBundle("messages");
    public Rps(String number, String series, LocalDateTime issuedAt, ServiceProvider provider, ServiceCustomer customer, ServiceDetails serviceDetails) {
        this.number = ValidationSupport.requireNonBlank(number, "Number");
        this.series = ValidationSupport.requireNonBlank(series, "Series");
        this.issuedAt = ValidationSupport.requireNonNull(issuedAt, "IssuedAt");
        this.provider = ValidationSupport.requireNonNull(provider, "Provider");
        this.customer = ValidationSupport.requireNonNull(customer, "Customer");
        this.serviceDetails = ValidationSupport.requireNonNull(serviceDetails, "Service Details");
        this.status = RpsStatus.CREATED;
    }
    public void authorize() {
        if (status != RpsStatus.CREATED) {
            throw new IllegalStateException(
                    resourceBundle.getString("rps.authorization.invalidState")
            );
        }

        this.status = RpsStatus.AUTHORIZED;
    }
    public String getNumber() {
        return number;
    }

    public String getSeries() {
        return series;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public ServiceProvider getProvider() {
        return provider;
    }

    public ServiceCustomer getCustomer() {
        return customer;
    }

    public ServiceDetails getServiceDetails() {
        return serviceDetails;
    }

    public RpsStatus getStatus() {
        return status;
    }
}
