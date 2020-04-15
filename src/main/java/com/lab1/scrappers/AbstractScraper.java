package com.lab1.scrappers;

import com.lab1.entity.Articles;
import com.lab1.model.Article;

import javax.persistence.EntityExistsException;
import java.util.Calendar;

public abstract class AbstractScraper {

    public abstract void scrap();

    void createAndPersistArticle(String url, String title, String category, String image, String body, Calendar date, String diario) {
        Article article = new Article();

        article.setUrl(url);
        article.setTitle(title);
        article.setCategory(category);
        article.setImage(image);
        article.setBody(body);
        article.setDiarioName(diario);
        article.setDate(date);

        try {
            Articles.persist(article); //Persist them into database
        } catch (EntityExistsException ignored) {

        }
    }
}
