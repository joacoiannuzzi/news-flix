package com.lab1.newsflix.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @Column(unique = true)
    private String url;

    private String title;

    private String category;

    private String image;

    @Size(max = 50000)
    private String body;

    private Calendar date;

    private String newspaper;

    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments = new HashSet<>();

    public Article() {
    }

    public Article(String url, String title, String category, String image, String body, Calendar date, String newspaper) {
        this.url = url;
        this.title = title;
        this.category = category;
        this.image = image;
        this.body = body;
        this.date = date;
        this.newspaper = newspaper;
    }

    public void addComment(User user, String body) {

        Comment comment = new Comment(user, this, body);
        if (!comments.contains(comment))
        {
        comments.add(comment);
        //user.addComment(comment);
        }else{
            throw new IllegalArgumentException("Spam filtered");
        }
    }

    public void removeComment(Long id, User user) {
        Comment comment = new Comment();
        comment.setId(id);
        comments.remove(comment);
        user.getComments().remove(comment);
        comment.setId(null);
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public Long getId() {
        return id;
    }

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
