package com.lab1.Article;

import java.time.LocalDateTime;

public abstract class ArticleAbstract implements IArticle {

    private final String title;
    private final String text;
    private final String url;
    private final int grade;
    private final LocalDateTime date;

    public ArticleAbstract(String title, String text, String url, int grade, LocalDateTime date){
        this.title=title;
        this.text=text;
        this.url=url;
        if(grade > 10 || grade < 0 ) throw new IllegalArgumentException("Grade can't be lower than 0, or greater than 10");
        this.grade=grade;
        this.date=date;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public int getGrade() {
        return grade;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }
}
