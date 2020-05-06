package com.lab1.newsflix.controller;


import com.lab1.newsflix.matcher.ArticleMatcherRunner;
import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    Collection<Article> getAllArticles() {
        return articleService.findAll();
    }

    @GetMapping("/articles/latest")
    Collection<Article> getLatestArticles() {
        return articleService.getLatestArticles();
    }

    //article/2
    @GetMapping("/article/{id}")
    ResponseEntity<?> getArticle(@PathVariable Long id) {
        Optional<Article> article = articleService.findById(id);
        return article.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/newspaper/{newspaper}")
    Collection<Article> getNewspaper(@PathVariable String newspaper) {
        return articleService.findByNewspaper(newspaper);
    }

    @GetMapping("/newspapers")
    Collection<Object> getNewspapers() {
        return articleService.getNewspapers();
    }

    @GetMapping("/category/{category}")
    Collection<Article> getByCategory(@PathVariable String category) {
        return articleService.findByCategory(category);
    }

    @GetMapping("/categories")
    Collection<Object> getCategories() {
        return articleService.getCategories();
    }


    @GetMapping("/articles/similar/{id}")
    Collection<Article> findSimilar(@PathVariable Long id) {
        Optional<Article> byId = articleService.findById(id);
        return byId.map(article -> ArticleMatcherRunner.findSimilar(article, articleService.findAll())).orElse(Collections.emptyList());
    }

}
