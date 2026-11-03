package com;

import com.nfsesandbox.rps.domain.Address;
import com.nfsesandbox.rps.domain.PersonType;
import com.nfsesandbox.rps.domain.ServiceProvider;

public class Main {
    public static void main(String[] args) {
        Address address = new Address("Whashington",
                "1",
                "CAsa",
                "Casita",
                "Sao Jose", "C" + "D",
                "12345678",
                "1234567");
        System.out.println("hello world");

        ServiceProvider serviceProvider = new ServiceProvider("1122",
                PersonType.INDIVIDUAL,
                "Carlos",
                "221122",
                address);
    }
}