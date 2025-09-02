package com.tiagoborja.mescala_ai.model.entity;

import java.time.LocalDate;

public class Schedule {

    private Group group;
    private Person person;
    private LocalDate day;

    public Schedule() {
    }

    public Schedule(Group group, Person person, LocalDate day) {
        this.group = group;
        this.person = person;
        this.day = day;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
}
