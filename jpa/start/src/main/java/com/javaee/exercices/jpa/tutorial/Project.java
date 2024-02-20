package com.javaee.exercices.jpa.tutorial;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_PROJECT")
public class Project {
    private Long id;
    private String title;
    private List<Geek> geeks = new ArrayList<>();

    private Period projectPeriod;

    private List<Period> billingPeriods = new ArrayList<>();

    private ProjectType projectType;
    public enum ProjectType {
        FIXED, TIME_AND_MATERIAL
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @ManyToMany(mappedBy = "projects")
    public List<Geek> getGeeks() {
        return geeks;
    }
    public void setGeeks(List<Geek> geeks) {
        this.geeks = geeks;
    }

    @Embedded
    public Period getProjectPeriod() {
        return projectPeriod;
    }
    public void setProjectPeriod(Period projectPeriod) {
        this.projectPeriod = projectPeriod;
    }

    @ElementCollection
    @CollectionTable(
            name="T_BILLING_PERIOD",
            joinColumns=@JoinColumn(name="PROJECT_ID")
    )
    public List<Period> getBillingPeriods() {
        return billingPeriods;
    }
    public void setBillingPeriods(List<Period> billingPeriods) {
        this.billingPeriods = billingPeriods;
    }

    @Enumerated(EnumType.ORDINAL)
    public ProjectType getProjectType() {
        return projectType;
    }
    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }
}