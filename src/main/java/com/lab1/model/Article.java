package com.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Article{

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name="TITLE")
    private String title;
    @Column(name="TEXT")
    private String text;
    @Column(name="URL")
    private String url;
    @Column(name="GRADE")
    private int grade;
    @Column(name="DATE")
    private LocalDateTime date;
    @Column(name="MAINWORD")
    private String mainword;

    public void setMainWord(String mainword){this.mainword=mainword;}
    public String getMainWord(){return mainword;}
    
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title){
        this.title=title;        
    }

    public String getText() {
        return text;
    }
    
    public void setText(String text){
        this.text=text;        
    }

    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url){
        this.url=url;
    }

    public int getGrade() {
        return grade;
    }
    
    public void setGrade(int grade){
        this.grade=grade;
    }

    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(){
        date=LocalDateTime.now();
    }
    

}
