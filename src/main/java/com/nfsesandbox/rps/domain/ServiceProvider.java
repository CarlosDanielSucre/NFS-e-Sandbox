package com.nfsesandbox.rps.domain;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ServiceProvider extends ServiceParty{
    private String municipalRegistration;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public ServiceProvider(String document, PersonType personType, String name, String municipalRegistration, Address address) {
        super(document, personType, name, address);
        this.municipalRegistration = ValidationSupport.requireNonBlank(municipalRegistration, "Municipal Registration");
    }

    public String getMunicipalRegistration() {
        return municipalRegistration;
    }

}
