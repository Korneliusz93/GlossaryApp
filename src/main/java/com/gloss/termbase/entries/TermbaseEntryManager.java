package com.gloss.termbase.entries;

import com.gloss.business.projects.Project;
import com.gloss.termbase.languages.Language;
import com.gloss.user.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class TermbaseEntryManager {

    private final SessionFactory sessionFactory;

    public TermbaseEntryManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Integer> addTE(String term, String termDescription, Project project, Language language, User creator) {
        try (Session session = sessionFactory.openSession()) {
            //Add checking user permissions to project
            //...
            if (project.getUsers().contains(creator)){
                session.beginTransaction();
                Integer id = (Integer) session.save(new TermbaseEntry(term,termDescription,language,creator));
                session.getTransaction().commit();
                return Optional.ofNullable(id);
            }
            System.out.println(String.format("No access to %s project for user: %s",project.toString(),creator.toString()));
            return Optional.empty();
        }
    }

    public void linkTEToProj(TermbaseEntry termbaseEntry, Project project){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            //update project
            project.addEntryToProj(termbaseEntry);
            session.saveOrUpdate(project);
            session.getTransaction().commit();
        }
    }

    public void updateTE(TermbaseEntry termbaseEntry, String description, User user) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            termbaseEntry.update(description,user);
            session.getTransaction().commit();
        }
    }

    public TermbaseEntry getTE(String term){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<TermbaseEntry> query = session
                    .createQuery("FROM TermbaseEntry WHERE term = :name",TermbaseEntry.class)
                    .setParameter("name",term);
            List<TermbaseEntry> termbaseEntries = query.list();
            session.getTransaction().commit();
            return termbaseEntries.get(0);
        }
    }

    public TermbaseEntry getTE(Optional<Integer> id){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<TermbaseEntry> query = session
                    .createQuery("FROM TermbaseEntry WHERE id = :teID",TermbaseEntry.class)
                    .setParameter("teID",id.get());
            List<TermbaseEntry> termbaseEntries = query.list();
            session.getTransaction().commit();
            return termbaseEntries.get(0);
            
        }
    }

}
