package com.gloss.user.emailaddresses;

import javax.persistence.*;
@Entity
@Table(name = "email_addresses")
public class EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email_address")
    private String emailAddress;

    public EmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EmailAddress(){}
}


