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

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public Address(String street, String number, String complement, String neighborhood, String city, String state, String postalCode, String municipalityCode) {
        this.street = ValidationSupport.requireNonBlank(street, "Street");
        this.number = ValidationSupport.requireNonBlank(number, "Number");
        this.complement = complement == null? null : complement.strip();
        this.neighborhood = ValidationSupport.requireNonBlank(neighborhood, "Neighborhood");
        this.city = ValidationSupport.requireNonBlank(city, "City");
        this.state = validateState(state, "State");
        this.postalCode = validatePostalCode(postalCode, "Postalcode");
        this.municipalityCode = validateMunicipality(municipalityCode, "Municipality Code");
    }
    private static String validateState(String value, String name) {
        String result = ValidationSupport.requireNonBlank(value, name);
        boolean isValid = result.matches("[A-Z]{2}");
        if(!isValid) {
            throw new IllegalArgumentException(resourceBundle.getString("address.state.invalid"));
        }
        return result;
    }
    private static String validatePostalCode(String value, String name) {
        String result = ValidationSupport.requireNonBlank(value, name);
        boolean isValid = result.matches("[0-9]{8}");
        if(!isValid) {
            throw new IllegalArgumentException(resourceBundle.getString("address.postalCode.invalid"));
        }
        return result;
    }
    private static String validateMunicipality(String value, String name) {
        String result = ValidationSupport.requireNonBlank(value, name);
        boolean isValid = result.matches("[0-9]{7}");
        if(!isValid) {
            throw new IllegalArgumentException(resourceBundle.getString("address.municipalityCode.invalid"));
        }
        return result;
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