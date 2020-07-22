package com.lab1.newsflix.service;

import com.lab1.newsflix.exception.ResourceNotFoundException;
import com.lab1.newsflix.matcher.ArticleMatcher;
import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.payload.CommentRequest;
import com.lab1.newsflix.payload.SearchRequest;
import com.lab1.newsflix.repository.ArticleRepository;
import com.lab1.newsflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleMatcher articleMatcher;

    public Article save(Article article) {
        return articleRepository.save(article);
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

    public Collection<String> getNewspapers() {
        return articleRepository.getNewspapers();
    }

    public Collection<Article> findByCategory(String category) {
        return articleRepository.findByCategory(category);
    }

    public Collection<String> getCategories() {
        return articleRepository.getCategories();
    }

    public Boolean existsByUrl(String url) {
        return articleRepository.existsByUrl(url);
    }

    public Collection<Article> getLatestArticles() {
        return findAll().stream()
                .filter(article -> Duration.between(article.getDate().toInstant(), Calendar.getInstance().toInstant()).toDays() < 2)
                .sorted(Comparator.comparing(Article::getDate).reversed())
                .collect(Collectors.toList());
    }

    public Collection<Article> findSimilar(Long id) {
        return findById(id).map(articleToCompare -> {

                    // agarra todos los articulos de la base de datos y los filtra segun la fecha --> todo habria que ver como directamente pedirlos filtrados
                    Collection<List<Article>> values = findAll().stream()
                            .filter(article -> Duration.between(articleToCompare.getDate().toInstant(), article.getDate().toInstant()).toDays() < 3
                                    && !article.getNewspaper().equals(articleToCompare.getNewspaper())
                            )
                            .collect(Collectors.groupingBy(Article::getNewspaper)) // los separa segun newsapaper en un map
                            .values(); // devuelve solo los articulos

                    return articleMatcher.findSimilar(articleToCompare, values);

                }
        ).orElse(Collections.emptyList());
    }


    public Collection<Article> queryArticles(String query) {
        if (query == null) return Collections.emptyList();
        String[] queries = query.toLowerCase().replaceAll("[^-a-zA-Z0-9\\s]", "").trim().split(" ");

        return findAll().stream()
                .filter(article -> stringContainsItemFromList(article.getTitle() + " " + article.getBody(), queries))
                .collect(Collectors.toList());
    }

    public Collection<Article> queryArticlesbyFilter(SearchRequest searchRequest) {

        String query = searchRequest.getQuery();
        if (query == null) return Collections.emptyList();
        System.out.println(searchRequest);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        String newspaperquery = "";
        String category = "";

        String[] queries = query.toLowerCase().replaceAll("[^-a-zA-Z0-9\\s]", "").trim().split(" ");

        try {

            newspaperquery = searchRequest.getNewspaper();
            category = searchRequest.getCategory();

            cal1.setTime(searchRequest.getDateFrom());
            cal2.setTime(searchRequest.getDateTo());

            cal1.set(Calendar.HOUR_OF_DAY, 0); //This is so it includes 00:00:00
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.SECOND, 0);

            cal2.set(Calendar.HOUR_OF_DAY, 23); //This is so it includes 00:00:00
            cal2.set(Calendar.MINUTE, 59);
            cal2.set(Calendar.SECOND, 59);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid arguments");
        }

        final String newspaperfinalquery = newspaperquery; //Super Hack
        final String categoryfinalquery = category;        //Super Hack x2

        Predicate<Article> matchNewspaper = article -> article.getNewspaper().equals(newspaperfinalquery);
        Predicate<Article> matchCategory = article -> article.getCategory().equals(categoryfinalquery);
        Predicate<Article> matchQuery = article -> stringContainsItemFromList(article.getTitle() + " " + article.getBody(), queries);

        Predicate<Article> filterCondition = matchNewspaper.and(matchCategory).and(matchQuery);

        return articleRepository.getArticlesByDateBetween(cal1, cal2).stream()
                .filter(filterCondition)
                .collect(Collectors.toList());

    }

    private boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().allMatch(inputStr.toLowerCase()::contains);
    }

    private boolean validDate(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().allMatch(inputStr.toLowerCase()::contains);
    }

    public Article addComment(CommentRequest commentRequest) {

        Article article = findById(commentRequest.getArticleId()).orElseThrow(() -> new ResourceNotFoundException("Article", "id", commentRequest.getArticleId()));
        User user = userRepository.findById(commentRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", commentRequest.getUserId()));

        article.addComment(user, commentRequest.getBody());

        articleRepository.save(article);
        return article;
    }

}
