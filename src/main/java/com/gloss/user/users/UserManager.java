package com.gloss.user.users;

import com.gloss.user.emailaddresses.EmailAddress;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;


public class UserManager {

    private final SessionFactory sessionFactory;

    public UserManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Dodaj uzytkownika do bazy
    public Optional<Integer> addUser(String firstName, String lastName, String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new User(firstName, lastName, username, password));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }
    // Usun uzytkownika z bazy
    public void deleteUser(String username) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query <User> query = session
                    .createQuery("DELETE FROM Customer WHERE username = :usr",User.class)
                    .setParameter("usr",username);
            session.getTransaction().commit();
        }
    }
    // Zmien haslo
    public void setPassword(User user, String newPassword) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            /*Query query = session
                    .createQuery("UPDATE Customer SET password = :pass WHERE username = :usr")
                    .setParameter("pass",newPassword)
                    .setParameter("usr",username);*/
            //int affected = query.executeUpdate();
            user.updatePassword(newPassword);
            session.getTransaction().commit();
        }
    }
    // usun adres e-mail
    //czy potrzebuje tutaj przekazywac string? przeladowanie funkcji
    public void deleteEmail(User user, EmailAddress emailAddress){
        user.removeEmailAddress(emailAddress);
    }
    // Dodaj adres e-mail
    public void addEmail(User user, String emailAddress){
        EmailAddress emailAddressUser = new EmailAddress(emailAddress);
        user.addEmailAddress(emailAddressUser);
    }

    //Pobierz uzytkownika
    public User getUser(String username){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query <User> query = session
                    .createQuery("FROM User WHERE username = :usr",User.class)
                    .setParameter("usr",username);
            List<User> users = query.list();
            session.getTransaction().commit();
            return users.get(0);
        }
    }

    //wypisz uzytkownikow
    public List<User> getUsers() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> customers = session.createQuery("FROM Customer", User.class).list();
            session.getTransaction().commit();
            return customers;
        }
    }

}
