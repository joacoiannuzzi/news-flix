package com.lab1.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "ARTICLE")
public class Article {

    @Id
    @Column(name = "URL")
    private String url;

    @Column(name = "DIARIO")
    private String diario;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DATE")
    private Calendar date;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "BODY", length = 50000)
    private String body;

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body=body;
    }

    public void setDiarioName(String diarioName){
        this.diario = diarioName;
    }

    public String getDiarioName(){
        return diario;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date=date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String picture) {
        this.image = picture;
    }

}
