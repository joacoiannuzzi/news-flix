package com.lab1.newsflix.service;

import com.lab1.newsflix.matcher.ArticleMatcher;
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

    @Autowired
    ArticleMatcher articleMatcher;

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

    public Boolean existsByUrl(String url) {
        return articleRepository.existsByUrl(url);
    }
    public Collection<Article> getLatestArticles() {
        Collection<Article> articles = articleRepository.findAll();
        Stream<Article> latestArticles = articles.stream().filter(article -> {
            int days = (int) Duration.between(article.getDate().toInstant(), Calendar.getInstance().toInstant()).toDays();
            return days < 2;
        });
        return latestArticles.sorted(Comparator.comparing(Article::getDate).reversed()).collect(Collectors.toList());
    }

    public Collection<Article> findSimilar(Long id) {
        return findById(id).map(articleToCompare -> {

                    // agarra todos los articulos de la base de datos y los filtra segun la fecha --> todo habria que ver como directamente pedirlos filtrados
                    Collection<List<Article>> values = findAll().stream()
                            .filter(article -> Math.abs(article.getDate().get(Calendar.DAY_OF_MONTH) - articleToCompare.getDate().get(Calendar.DAY_OF_MONTH)) <= 3
                                    && Math.abs(article.getDate().get(Calendar.YEAR) - articleToCompare.getDate().get(Calendar.YEAR)) == 0
                                    && Math.abs(article.getDate().get(Calendar.MONTH) - articleToCompare.getDate().get(Calendar.MONTH)) == 0
                                    && !article.getNewspaper().equals(articleToCompare.getNewspaper()))
                            .collect(Collectors.groupingBy(Article::getNewspaper)) // los separa segun newsapaper en un map
                            .values(); // devuelve solo los articulos

                    return articleMatcher.findSimilar(articleToCompare, values);

                }
        ).orElse(Collections.emptyList());
    }


}
