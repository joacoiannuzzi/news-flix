package com.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;


    @Column(name = "grade")
    private int grade;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "main_word")
    private String mainword;

    @Column(name = "picture")
    private String picture;



    public void setMainWord(String mainword) {
        this.mainword = mainword;
    }

    public String getMainWord() {
        return mainword;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate() {
        date = LocalDateTime.now();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
