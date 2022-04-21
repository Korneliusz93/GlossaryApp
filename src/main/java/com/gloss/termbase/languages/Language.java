package com.gloss.termbase.languages;

import javax.persistence.*;

@Entity
@Table(name = "languages")
public class Language {

    @Id
    @Column(name = "language_code")
    private String languageCode;

    public Language(String languageCode) {
        this.languageCode = languageCode;
    }

    public Language(){}

}
