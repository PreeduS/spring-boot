package com.example.user.models;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne; 
@Entity
public class Authorities {
    //@Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String authority;

    @ManyToOne
    @JoinColumn( name = "username", referencedColumnName = "username")
    private User user;

    public String getAuthority() {
        return authority;
    }

    public String getUsername() {
        return user.getUsername();
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}