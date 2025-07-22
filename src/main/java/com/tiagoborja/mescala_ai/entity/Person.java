package com.tiagoborja.mescala_ai.entity;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public class Person {

    private String name;
    private List<LocalDate> unavailable;
    private List<Group> groups;

    public Person(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LocalDate> getUnavailable() {
        return unavailable;
    }

    public void setUnavailable(List<LocalDate> unavailable) {
        this.unavailable = unavailable;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
