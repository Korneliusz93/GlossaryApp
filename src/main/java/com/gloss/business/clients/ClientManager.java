package com.gloss.business.clients;

import com.gloss.business.categories.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ClientManager {
    private final SessionFactory sessionFactory;

    public ClientManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Dodaj uzytkownika do bazy
    public Optional<Integer> addClient(String clientName) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new Client(clientName));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

    public Client getClient(String clientName){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Client> query = session
                    .createQuery("FROM Client WHERE clientName = :cName",Client.class)
                    .setParameter("cName",clientName);
            List<Client> clientList = query.list();
            session.getTransaction().commit();
            return clientList.get(0);
        }
    }
}
