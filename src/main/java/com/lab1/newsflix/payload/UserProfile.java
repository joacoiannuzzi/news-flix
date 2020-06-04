package com.lab1.newsflix.payload;

import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.security.UserPrincipal;

import java.util.Set;

public class UserProfile {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Article> favorites;


    public UserProfile(Long id, String email, String firstName, String lastName, Set<Article> favorites) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.favorites = favorites;
    }

    public UserProfile(User user) {
        this(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getFavorites());
    }

    public UserProfile(UserPrincipal user) {
        this(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getFavorites());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Set<Article> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Article> favorites) {
        this.favorites = favorites;
    }
}
