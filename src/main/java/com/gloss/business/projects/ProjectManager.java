package com.gloss.business.projects;

import com.gloss.business.categories.Category;
import com.gloss.business.categories.CategoryManager;
import com.gloss.business.clients.Client;
import com.gloss.business.clients.ClientManager;
import com.gloss.user.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProjectManager {
    private final SessionFactory sessionFactory;

    public ProjectManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Integer> addProject(String projectName, Category category, Client client) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new Project(projectName,category,client));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

    public Project getProject(String proj){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Project> query = session
                    .createQuery("FROM Project WHERE projectName = :proj",Project.class)
                    .setParameter("proj",proj);
            List<Project> projects = query.list();
            session.getTransaction().commit();
            return projects.get(0);
        }
    }

    public void deleteProject(String projName) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query <Project> query = session
                    .createQuery("DELETE FROM Project WHERE projectName = :projName",Project.class)
                    .setParameter("projName",projName);
            session.getTransaction().commit();
        }
    }

}
