package com.nfsesandbox.rps.domain;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ServiceProvider {
    private String document;
    private PersonType personType;
    private String name;
    private String municipalRegistration;
    private Address address;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public ServiceProvider(String document, PersonType personType, String name, String municipalRegistration, Address address) {
        this.document = requireNonBlank(document,"Document");
        if(personType != PersonType.INDIVIDUAL && personType != PersonType.LEGAL_ENTITY) {
            throw new IllegalArgumentException(resourceBundle.getString("validation.required.personType"));
        }
        this.personType = personType;
        this.name = requireNonBlank(name, "Name of the Service Provider");
        this.municipalRegistration = municipalRegistration;
        this.address = address;
    }

    private static String requireNonBlank(String value, String name) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(MessageFormat.format(resourceBundle.getString("validation.required"), name));
        }
        return value.strip();
    }
    public String getDocument() {
        return document;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public String getName() {
        return name;
    }

    public String getMunicipalRegistration() {
        return municipalRegistration;
    }

    public Address getAddress() {
        return address;
    }
}
