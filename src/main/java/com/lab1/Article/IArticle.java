package com.lab1.Article;

import java.time.LocalDateTime;

public interface IArticle {

    String getTitle();

    String getText();

    String getUrl();

    int getGrade();

    LocalDateTime getDate();

}
