package com.lab1.newsflix.scraper;

import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public abstract class AbstractScraper {

    public static String SEPARATION = "\\{\\{\\{SPLIT\\}\\}\\}";

    @Autowired
    ArticleService articleService;

    public abstract void scrap();

    public String fixCategory(String raw) {
        String s = raw.replaceAll("-", " ").replaceAll("[^a-zA-Z\\s]", "").trim();
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public void save(String url, String title, String category, String image, String body, Calendar date, String newspaper) {
        if (articleService.existsByUrl(url))
            return;

        Article article = new Article(url, title, fixCategory(category), image, body, date, newspaper);
        articleService.save(article);
    }
}
