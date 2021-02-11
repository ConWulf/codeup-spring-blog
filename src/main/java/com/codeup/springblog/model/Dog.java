package com.codeup.springblog.model;

import jdk.jfr.Unsigned;

import javax.persistence.*;

@Entity
@Table(name = "dogs", uniqueConstraints = {@UniqueConstraint(columnNames = {"reside_state", "name"})})
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint(20) unsigned")
    private long id;

    @Column(nullable = false)
    private short age;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(
            name = "reside_state",
            columnDefinition = "char(2) default 'XX'",
            nullable = false
    )
    private String resideState;

    public Dog() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return resideState;
    }

    public void setState(String state) {
        this.resideState = state;
    }
}
