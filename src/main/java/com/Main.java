package com;

import com.nfsesandbox.rps.domain.Address;
import com.nfsesandbox.rps.domain.PersonType;
import com.nfsesandbox.rps.domain.ServiceDetails;
import com.nfsesandbox.rps.domain.ServiceProvider;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Address address = new Address("Whashington",
                "1",
                "Casa",
                "Casita",
                "Sao Jose", "C" + "D",
                "12345678",
                "1234567");
        System.out.println("hello world");

        ServiceProvider serviceProvider = new ServiceProvider("11223456787",
                PersonType.INDIVIDUAL,
                "Carlos",
                "221122",
                address);

        ServiceDetails serviceDetails = new ServiceDetails("12",
                "Description",
                new BigDecimal("1000.00"),
                new BigDecimal("5"));
    }

}