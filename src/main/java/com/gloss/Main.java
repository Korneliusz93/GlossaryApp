package com.gloss;

import com.gloss.business.categories.Category;
import com.gloss.business.categories.CategoryManager;
import com.gloss.business.clients.Client;
import com.gloss.business.clients.ClientManager;
import com.gloss.business.permissions.UserPermissionsManager;
import com.gloss.business.projects.Project;
import com.gloss.business.projects.ProjectManager;
import com.gloss.termbase.entries.TermbaseEntryManager;
import com.gloss.termbase.languages.Language;
import com.gloss.termbase.entries.TermbaseEntry;
import com.gloss.termbase.languages.LanguageManager;
import com.gloss.user.emailaddresses.EmailAddress;
import com.gloss.user.users.User;
import com.gloss.user.users.UserManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("Glossary system");
        SessionFactory SessionFactory = new Configuration()
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Project.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(TermbaseEntry.class)
                .addAnnotatedClass(EmailAddress.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        UserManager userManager = new UserManager(SessionFactory);
        ClientManager clientManager = new ClientManager(SessionFactory);
        CategoryManager categoryManager = new CategoryManager(SessionFactory);
        ProjectManager projectManager = new ProjectManager(SessionFactory);
        UserPermissionsManager userPermissionsManager = new UserPermissionsManager(SessionFactory);
        TermbaseEntryManager termbaseEntryManager = new TermbaseEntryManager(SessionFactory);
        LanguageManager languageManager = new LanguageManager(SessionFactory);


        userManager.addUser("Jan","Kowalski","kowalj", "dupa8");
        userManager.addUser("Wojciech","Malinowski","malinw", "dupa8");
        User kowalskiJan = userManager.getUser("kowalj");
        User malinowskiWojciech = userManager.getUser("malinw");
        userManager.addEmail(kowalskiJan,"jankowalski@wp.pl");
        userManager.addEmail(malinowskiWojciech,"malinowskiwoj@wp.pl");

        clientManager.addClient("Warner");
        clientManager.addClient("Unicorn");
        Client warnerClient = clientManager.getClient("Warner");
        Client unicornClient = clientManager.getClient("Unicorn");

        categoryManager.addCategory("Media");
        Category categoryMedia = categoryManager.getCategory("Media");

        projectManager.addProject("Warner-Media",categoryMedia,warnerClient);
        projectManager.addProject("Unicorn-Media",categoryMedia,unicornClient);

        Project mediaWarner = projectManager.getProject("Warner-Media");
        Project mediaUnicorn = projectManager.getProject("Unicorn-Media");
        userPermissionsManager.addUserToProject(mediaWarner,kowalskiJan);
        userPermissionsManager.addUserToProject(mediaUnicorn,kowalskiJan);
        userPermissionsManager.deleteUserFromProject(mediaWarner,kowalskiJan);
        userPermissionsManager.addUserToProject(mediaWarner,malinowskiWojciech);

        languageManager.addLanguage("fr-fr");
        Language french = languageManager.getLanguage("fr-fr");
        Optional<Integer> idTest = termbaseEntryManager.addTE("endevour","placeholder",mediaWarner,french,kowalskiJan);
        TermbaseEntry wordTest = termbaseEntryManager.getTE(idTest);
        termbaseEntryManager.linkTEToProj(wordTest,mediaUnicorn);


    }
}
