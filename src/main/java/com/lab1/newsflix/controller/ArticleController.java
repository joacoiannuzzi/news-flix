package com.lab1.newsflix.controller;


import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles")
    Collection<Article> categories() {
        return articleRepository.findAll();
    }

    //article/2
    @GetMapping("/article/{id}")
    ResponseEntity<?> getArticle(@PathVariable Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/newspaper/{newspaper}")
    Collection<Article> getNewspaper(@PathVariable String newspaper) {
        return articleRepository.findByNewspaper(newspaper);
    }

    @GetMapping("/newspapers")
    Collection<Object> getNewspapers() {
        return articleRepository.getNewspapers();
    }

    @GetMapping("/category/{category}")
    Collection<Article> getByCategory(@PathVariable String category) {
        return articleRepository.findByCategory(category);
    }

    @GetMapping("/categories")
    Collection<Object> getCategories() {
        return articleRepository.getCategories();
    }

//    @PostMapping("/find-new-articles")
//    ResponseEntity<Article> findNewArticles() throws URISyntaxException {
//        Article result = articleService.save(article);
//        return ResponseEntity.created(new URI("/api/article" + result.getUrl())).body(result);
//
//    }
//
//    @PutMapping("/article/{id}")
//    ResponseEntity<Article> updateArticle(@Valid @RequestBody Article article) {
//        Article result = articleService.save(article);
//        return ResponseEntity.ok().body(result);
//    }
//
//
//    @DeleteMapping("/article/{id}")
//    ResponseEntity<?> deleteArticle(@PathVariable String id) {
//        articleService.deleteById(id);
//        return ResponseEntity.ok().build();
//    }
}
