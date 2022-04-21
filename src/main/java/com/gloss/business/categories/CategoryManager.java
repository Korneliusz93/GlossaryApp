package com.gloss.business.categories;

import com.gloss.user.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CategoryManager {
    private final SessionFactory sessionFactory;

    public CategoryManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Dodaj uzytkownika do bazy
    public Optional<Integer> addCategory(String categoryName) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new Category(categoryName));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

    public Category getCategory(String categoryName){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Category> query = session
                    .createQuery("FROM Category WHERE categoryName = :cat",Category.class)
                    .setParameter("cat",categoryName);
            List<Category> categoryList = query.list();
            session.getTransaction().commit();
            return categoryList.get(0);
        }
    }
}
