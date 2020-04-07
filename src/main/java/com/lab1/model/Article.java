package com.lab1.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
@Table(name = "ARTICLE")
public class Article {

    @Id
    @Column(name = "URL")
    private String url;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "GRADE")
    private int grade;

    @Column(name = "DATE")
    private Calendar date;

    @Column(name = "MAINWORD")
    private String main_word;

    @Column(name = "imageURL")
    private String image;



    public void setMainWord(String mainword) {
        this.main_word = mainword;
    }

    public String getMainWord() {
        return main_word;
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
