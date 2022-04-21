package com.gloss.user.users;

import com.gloss.business.projects.Project;
import com.gloss.user.emailaddresses.EmailAddress;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public void updatePassword(String pass){
        this.password = pass;
    }

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<EmailAddress> emailAddress = new HashSet<>();

    public void addEmailAddress(EmailAddress emailAddress) {
        this.emailAddress.add(emailAddress);
    }

    public void removeEmailAddress(EmailAddress emailAddress){
        this.emailAddress.remove(emailAddress);
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_projects",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Project> projects;

    public void setProjects(Set<Project> projects ) {
        this.projects = projects;
    }

    //
    public User(String firstName, String lastName, String username, String password) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = new HashSet<>();
        this.projects = new HashSet<>();
    }

    public User(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public String toString(){
        return this.username;
    }
}
