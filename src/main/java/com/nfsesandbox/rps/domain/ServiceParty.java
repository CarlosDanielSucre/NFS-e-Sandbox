package com.nfsesandbox.rps.domain;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public abstract class ServiceParty {
    private final String document;
    private final PersonType personType;
    private final String name;
    private final Address address;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    protected ServiceParty(String document, PersonType personType, String name, Address address) {
        if(personType == null) {
            throw new IllegalArgumentException(resourceBundle.getString("validation.required.personType"));
        }
        this.document = validateDocument(document,personType);
        this.personType = personType;
        this.name = requireNonBlank(name, "Name");
        if(address == null) {
            throw new IllegalArgumentException(MessageFormat.format(resourceBundle.getString("validation.required"), "Address"));
        }
        this.address = address;
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

    protected static String requireNonBlank(String value, String name) {
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

    public Address getAddress() {
        return address;
    }
}
