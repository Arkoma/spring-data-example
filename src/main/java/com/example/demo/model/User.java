package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name="USER")
public class User {

    public User(String firstName, String lastname, String email) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="FIRST_NAME", length = 50, nullable = false, unique = false)
    private String firstName;

    @Column(name="LAST_NAME", length = 50, nullable = false, unique = false)
    private String lastName;

    @Column(name="EMAIL", length = 50, nullable = true, unique = false)
    private String email;

    @Column(name="ORGANIZATION")
    private Organization organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
