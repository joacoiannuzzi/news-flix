package com.lab1.newsflix.service;

import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void save(Article article) {
        articleRepository.save(article);
    }

    public Collection<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public Collection<Article> findByNewspaper(String newspaper) {
        return articleRepository.findByNewspaper(newspaper);
    }

    public Collection<Object> getNewspapers() {
        return articleRepository.getNewspapers();
    }

    public Collection<Article> findByCategory(String category) {
        return articleRepository.findByCategory(category);
    }

    public Collection<Object> getCategories() {
        return articleRepository.getCategories();
    }

    public Collection<Article> getLatestArticles() {
        Collection<Article> articles = articleRepository.findAll();
        Stream<Article> latestArticles = articles.stream().filter(article -> {
            int days = (int) Duration.between(article.getDate().toInstant(), Calendar.getInstance().toInstant()).toDays();
            return days < 2;
        });
        return latestArticles.sorted(Comparator.comparing(Article::getDate).reversed()).collect(Collectors.toList());
    }




}
