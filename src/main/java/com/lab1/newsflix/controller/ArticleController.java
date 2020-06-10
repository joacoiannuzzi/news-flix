package com.lab1.newsflix.controller;


import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.payload.CommentRequest;
import com.lab1.newsflix.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @GetMapping("/all")
    Collection<Article> getAllArticles() {
        return articleService.findAll();
    }

    @GetMapping("/latest")
    Collection<Article> getLatestArticles() {
        return articleService.getLatestArticles();
    }

    //article/2
    @GetMapping("/{id}")
    ResponseEntity<?> getArticle(@PathVariable Long id) {
        Optional<Article> article = articleService.findById(id);
        return article.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/newspapers/{newspaper}")
    Collection<Article> getNewspaper(@PathVariable String newspaper) {
        return articleService.findByNewspaper(newspaper);
    }

    @GetMapping("/newspapers/all")
    Collection<Object> getNewspapers() {
        return articleService.getNewspapers();
    }

    @GetMapping("/categories/{category}")
    Collection<Article> getByCategory(@PathVariable String category) {
        return articleService.findByCategory(category);
    }

    @GetMapping("/categories/all")
    Collection<Object> getCategories() {
        return articleService.getCategories();
    }

    @GetMapping("/similar/{id}")
    Collection<Article> findSimilar(@PathVariable Long id) {
        return articleService.findSimilar(id);
    }

    @GetMapping("/query")
    Collection<Article> queryArticles(@RequestParam String query) {
        return articleService.queryArticles(query);
    }

//    @PostMapping("/comments/add")
//    ResponseEntity<?> addComment(@Valid @RequestBody CommentRequest commentRequest) {
//        Article updated = articleService.addComment(commentRequest);
//        return ResponseEntity.ok().body(updated);
//
//    }

}
