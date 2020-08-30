package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Authorities {
    //@Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //private String username;
    private String authority;

    @ManyToOne
    //@JoinColumn(name = "username", referencedColumnName = "username")
    //@JoinColumn(name = "userUsername", referencedColumnName = "userUsername")
    @JoinColumn( name = "username", referencedColumnName = "username")
    //@JoinColumn(referencedColumnName = "username")
    // name - name of the foreign key column on this table
    // referencedColumnName - name of the column name on the other table
    @Fetch(FetchMode.JOIN)
    private User user;

    public String getAuthority() {
        return authority;
    }

    public String getUsername() {
        return user.getUsername();
    }
/*
    public void setUsername(String username) {
        this.username = username;
    }
*/
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}