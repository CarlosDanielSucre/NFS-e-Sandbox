package com.nfsesandbox.rps.domain;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ServiceProvider extends ServiceParty{
    private String municipalRegistration;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public ServiceProvider(String document, PersonType personType, String name, String municipalRegistration, Address address) {
        super(document, personType, name, address);
        this.municipalRegistration = requireNonBlank(municipalRegistration, "Municipal Registration");
    }

    private static String validateDocument(String value, PersonType personType) {
        String result = requireNonBlank(value, "Document");
        boolean isValid = false;
        if(personType == PersonType.INDIVIDUAL) {
            isValid = result.matches("[0-9]{11}");
        } else if(personType == PersonType.LEGAL_ENTITY) {
            isValid = result.matches("[A-Z0-9]{14}");
        }
        if(!isValid) {
            throw new IllegalArgumentException(resourceBundle.getString("validation.invalid.document"));
        }
        return result;
    }



    public String getMunicipalRegistration() {
        return municipalRegistration;
    }

}
