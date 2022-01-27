package com.sparta.projectapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "logins", indexes = {
        @Index(name = "user_id", columnList = "user_id"),
        @Index(name = "username", columnList = "username", unique = true)
})
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "username", length = 128)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "current_token", length = 40)
    private String currentToken;

    public Login(User user, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = password;
    }

    public Login() {
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}