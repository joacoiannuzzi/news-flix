package com.lab1.newsflix.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Calendar;

@NoArgsConstructor
@Entity
@Data
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String url;

    private String title;

    private String category;

    private String image;

    @Size(max = 50000)
    private String body;

    private Calendar date;

    private String newspaper;

    public Article(String url, String title, String category, String image, String body, Calendar date, String newspaper) {
        this.url = url;
        this.title = title;
        this.category = category;
        this.image = image;
        this.body = body;
        this.date = date;
        this.newspaper = newspaper;
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

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
    }
}
