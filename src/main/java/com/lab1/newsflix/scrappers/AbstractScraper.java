package com.lab1.newsflix.scrappers;


import com.lab1.newsflix.controller.ArticleController;
import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public abstract class AbstractScraper {



    public abstract void scrap(List<Article> articles);

    public void saveArticle(String url, String title, String category, String image, String body, Calendar date, String newspaper) {
        Article article = new Article(url, title, category, image, body, date, newspaper);
    }
}
