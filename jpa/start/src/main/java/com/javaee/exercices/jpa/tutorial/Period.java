package com.javaee.exercices.jpa.tutorial;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Period {
    private Date startDate;
    private Date endDate;
    @Column(name ="START_DATE")
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    @Column(name ="END_DATE")
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}