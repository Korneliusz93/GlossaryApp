package com.gloss.business.clients;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "client_name")
    private String clientName;

    public Client(String clientName) {
        this.clientName = clientName;
    }

    public Client(){
        this.clientName = "default";
    }
}
