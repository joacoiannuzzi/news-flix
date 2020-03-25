package com.lab1.Article;

import java.time.LocalDateTime;

public class Article extends ArticleAbstract {

    public Article(String title, String body, String url, int grade){
        super(title,body,url,grade, LocalDateTime.now());
    }

}
