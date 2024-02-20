package com.javaee.exercices.jpa.tutorial;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_GEEK")
@Access(AccessType.PROPERTY)
public class Geek extends Person {
    private String favouriteProgrammingLanguage;

    private List<Project> projects = new ArrayList<>();

    @Column(name = "FAV_PROG_LANG")
    public String getFavouriteProgrammingLanguage() {
        return favouriteProgrammingLanguage;
    }

    public void setFavouriteProgrammingLanguage(String
                                                        favouriteProgrammingLanguage) {
        this.favouriteProgrammingLanguage = favouriteProgrammingLanguage;
    }

    @ManyToMany
    @JoinTable(
            name="T_GEEK_PROJECT",
            joinColumns={@JoinColumn(name="GEEK_ID",
                    referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="PROJECT_ID",
                    referencedColumnName="ID")})
    public List<Project> getProjects() {
        return projects;
    }
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}