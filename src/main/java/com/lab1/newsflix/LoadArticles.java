package com.lab1.newsflix;


import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.repository.ArticleRepository;
import com.lab1.newsflix.scrappers.AbstractScraper;
import com.lab1.newsflix.scrappers.ClarinScraper;
import com.lab1.newsflix.scrappers.InfobaeScraper;
import com.lab1.newsflix.scrappers.LaNacionScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class LoadArticles {

    @Autowired
    ArticleRepository articleRepository;

    private final List<AbstractScraper> scrapers = new ArrayList<>();

    LoadArticles() {
        scrapers.add(new LaNacionScraper());
        scrapers.add(new ClarinScraper());
        scrapers.add(new InfobaeScraper());
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            List<Article> articles = new ArrayList<>();
//            articleRepository.save(new Article("kand", "afaefa", "dafasdas", "asdadas", "sdfsdf", Calendar.getInstance(), "sdfsdf"));
            scrapers.forEach(scraper -> {
                scraper.scrap(articles);
                articles.forEach(article -> {
                    try {
                        articleRepository.save(article);
                    } catch (Exception e) {
                        System.out.println("Some error with an article");
                }
                });
                articles.clear();
            });
        };
    }
}
