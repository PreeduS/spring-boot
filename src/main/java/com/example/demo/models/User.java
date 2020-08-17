package com.example.demo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "users")
public class User implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@NaturalId
    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private boolean enabled;
    
    //@Transient
    //private String roles;

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    //@OneToMany(mappedBy = "username")
    List<Authorities> roles = new ArrayList<Authorities>();
    //private Authorities roles2;

    

    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public List<Authorities> getRoles() {
        return roles;
    }

    public void setRoles(List<Authorities> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

}