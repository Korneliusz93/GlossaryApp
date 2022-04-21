package com.gloss.termbase.languages;

import com.gloss.business.categories.CategoryManager;
import com.gloss.business.clients.ClientManager;
import com.gloss.business.projects.Project;
import com.gloss.user.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class LanguageManager {

    private final SessionFactory sessionFactory;

    public LanguageManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<String> addLanguage(String label) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String languageCode = (String) session.save(new Language(label));
            session.getTransaction().commit();
            return Optional.ofNullable(languageCode);
        }
    }

    public Language getLanguage(String languageCode){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Language> query = session
                    .createQuery("FROM Language WHERE languageCode = :lang",Language.class)
                    .setParameter("lang",languageCode);
            List<Language> languages = query.list();
            session.getTransaction().commit();
            return languages.get(0);
        }
    }

}
