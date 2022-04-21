package com.gloss.business.projects;

import com.gloss.business.categories.Category;
import com.gloss.business.clients.Client;
import com.gloss.termbase.entries.TermbaseEntry;
import com.gloss.user.users.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TermbaseEntry> entries;

    public void addEntryToProj(TermbaseEntry tEntry){
        entries.add(tEntry);
    }

    public void removeEntry(TermbaseEntry tEntry){
        this.entries.remove(tEntry);
    }

    @Column(name = "project_name")
    private String projectName;

    @OneToOne
    private Category category;

    @OneToOne
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "projects", cascade = CascadeType.MERGE)
    private Set<User> users = new HashSet<>();

    public void addUser(User user){
        this.users.add(user);
        user.getProjects().add(this);
    }

    public void removeUser(User user){
        boolean check = this.users.contains(user);
        this.users.remove(user);
        user.getProjects().remove(this);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    //
    public Project(String projectName, Category category, Client client)
    {
        this.projectName = projectName;
        this.category = category;
        this.client = client;
        this.entries = new HashSet<>();
        this.users = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return id == project.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Project(){}

    public String toString(){
        return this.projectName;
    }


}
