package com.nfsesandbox.rps.domain;

import java.time.LocalDateTime;

public class Authorization {
        private String nfseNumber;
        private String protocol;
        private LocalDateTime authorizedAt;

    public Authorization(String nfseNumber, String protocol, LocalDateTime authorizedAt) {
        this.nfseNumber = ValidationSupport.requireNonBlank(nfseNumber, "NFS-e Number");
        this.protocol = ValidationSupport.requireNonBlank(protocol, "Protocol");
        this.authorizedAt = ValidationSupport.requireNonNull(authorizedAt, "Authorized At");
    }

    public String getNfseNumber() {
        return nfseNumber;
    }

    public String getProtocol() {
        return protocol;
    }

    public LocalDateTime getAuthorizedAt() {
        return authorizedAt;
    }
}
