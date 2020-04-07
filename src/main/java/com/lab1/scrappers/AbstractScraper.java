package com.lab1.scrappers;

import com.lab1.entity.Articles;
import com.lab1.model.Article;

import java.time.LocalDateTime;
import java.util.Calendar;

public abstract class AbstractScraper {

    protected Calendar calendar = Calendar.getInstance();

    public abstract void scrap();

    Article createAndPersistArticle(String url, String title, String mainword, String image, int grade) {
        Article article = new Article();
        article.setUrl(url);
        article.setTitle(title);
        article.setMainWord(mainword);
        article.setImage(image);
        article.setGrade(grade);
        article.setDate(calendar);

        Articles.persist(article); //Persist them into database
        return article;

    }
}
