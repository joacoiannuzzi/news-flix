package com.lab1.newsflix.scrappers;

import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.service.ArticleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public abstract class AbstractScraper {

    @Autowired
    ArticleService articleService;

    public abstract void scrap();

    public String fixCategory(@NotNull String raw) {
        String s = raw.replaceAll("-", " ").replaceAll("[^a-zA-Z\\s]", "").trim();
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public void save(String url, String title, String category, String image, String body, Calendar date, String newspaper) {
        Article article = new Article(url, title, fixCategory(category), image, body, date, newspaper);
        articleService.save(article);
    }
}
