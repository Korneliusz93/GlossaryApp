package com.gloss.user.emailaddresses;

import com.gloss.business.projects.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class EmailAddressManager {
    private final SessionFactory sessionFactory;

    public EmailAddressManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public EmailAddress getEmailAddress(String emailAddress){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<EmailAddress> query = session
                    .createQuery("FROM EmailAddress WHERE email = :addr",EmailAddress.class)
                    .setParameter("addr",emailAddress);
            List <EmailAddress> emailAddresses = query.list();
            session.getTransaction().commit();
            return emailAddresses.get(0);
        }
    }

    public void removeEmailAddress(String email){
            try (Session session = sessionFactory.openSession()) {
                session.getTransaction().begin();
                Query <EmailAddress> query = session
                        .createQuery("DELETE FROM EmailAddress WHERE emailAddress = :address",EmailAddress.class)
                        .setParameter("address",email);
                session.getTransaction().commit();
            }
        }
}
