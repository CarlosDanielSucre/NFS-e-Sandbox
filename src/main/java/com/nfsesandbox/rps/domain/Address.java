package com.nfsesandbox.rps.domain;

public class Address {
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;
    private String municipalityCode;

    public Address(String street, String number, String complement, String neighborhood, String city, String state, String postalCode, String municipalityCode) {
        this.street = requireNonBlank(street, "Street");
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.municipalityCode = municipalityCode;
    }

    private static String requireNonBlank(String value, String name) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " cannot be blank or null");
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