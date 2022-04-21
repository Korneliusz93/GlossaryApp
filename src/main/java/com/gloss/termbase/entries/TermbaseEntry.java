package com.gloss.termbase.entries;

import com.gloss.termbase.languages.Language;
import com.gloss.user.users.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "termbase_entries")
public class TermbaseEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "term")
    private String term;

    @Column(name = "term_description")
    private String termDescription;

    @OneToOne
    private Language language;

    @OneToOne
    private User creator;

    @OneToOne
    private User updater;

    @Column(name = "entry_creation_date")
    private LocalDate entryCreationDate;

    @Column(name = "entry_update_date")
    private LocalDate entryUpdateDate;

    public TermbaseEntry(String term, String termDescription, Language language, User creator) {
        this.term = term;
        this.termDescription = termDescription;
        this.language = language;
        this.creator = creator;
        this.entryCreationDate = LocalDate.now();
    }

    public TermbaseEntry(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TermbaseEntry)) return false;
        TermbaseEntry that = (TermbaseEntry) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void update(String termDescription, User user){
        this.updater = user;
        this.termDescription = termDescription;
        this.entryUpdateDate = LocalDate.now();
    }

}
