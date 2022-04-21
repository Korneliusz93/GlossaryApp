package com.gloss.business.permissions;

import com.gloss.business.projects.Project;
import com.gloss.business.projects.ProjectManager;
import com.gloss.user.users.UserManager;
import org.hibernate.Session;

import com.gloss.user.users.User;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.Set;

public class UserPermissionsManager {
    private final SessionFactory sessionFactory;

    public UserPermissionsManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //Add user to project
    public void addUserToProject(Project updProj, User updUser){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            updProj.addUser(updUser);
            session.saveOrUpdate(updProj);
            session.saveOrUpdate(updUser);
            session.getTransaction().commit();
        }
    }

    //Delete user from project
    public void deleteUserFromProject(Project updProj, User updUser){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            updProj.removeUser(updUser);
            session.saveOrUpdate(updProj);
            session.saveOrUpdate(updUser);
            session.getTransaction().commit();
        }
    }
}
