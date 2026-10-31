package com.nfsesandbox.rps.domain;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Address {
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;
    private String municipalityCode;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public Address(String street, String number, String complement, String neighborhood, String city, String state, String postalCode, String municipalityCode) {
        this.street = requireNonBlank(street, "Street");
        this.number = requireNonBlank(number, "Number");
        this.complement = complement;
        this.neighborhood = requireNonBlank(neighborhood, "Neighborhood");
        this.city = requireNonBlank(city, "City");
        this.state = validateState(state, "State");
        this.postalCode = validatePostalCode(postalCode, "Postalcode");
        this.municipalityCode = validateMunicipality(municipalityCode, "Municipality Code");
    }
    private static String validateState(String value, String name) {
        String result = requireNonBlank(value, name);
        boolean isValid = result.matches("[A-Z]{2}");
        if(!isValid) {
            throw new IllegalArgumentException(resourceBundle.getString("address.state.invalid"));
        }
        return result;
    }
    private static String validatePostalCode(String value, String name) {
        if(value == null || value.isBlank() || value.length() > 8) {
            throw new IllegalArgumentException(resourceBundle.getString("address.postalCode.invalid"));
        }
        return value.strip();
    }
    private static String validateMunicipality(String value, String name) {
        if(value == null || value.isBlank() || value.length() > 7) {
            throw new IllegalArgumentException(resourceBundle.getString("address.municipalityCode.invalid"));
        }
        return value.strip();
    }
    private static String requireNonBlank(String value, String name) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(MessageFormat.format(resourceBundle.getString("validation.required"), name));
        }
        return value.strip();
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }
}