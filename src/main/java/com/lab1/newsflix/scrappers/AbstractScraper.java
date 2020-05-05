package com.lab1.newsflix.scrappers;


import com.lab1.newsflix.model.Article;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public abstract class AbstractScraper {


    public abstract void scrap(List<Article> articles);

    public String fixCategory(String raw) {
        String s = raw.replaceAll("-", " ").replaceAll("[^a-zA-Z\\s]", "").trim().replaceAll("[a-zA-Z][a-zA-Z]\\s", "").trim();
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public void saveArticle(String url, String title, String category, String image, String body, Calendar date, String newspaper) {
        Article article = new Article(url, title, category, image, body, date, newspaper);
    }
}
