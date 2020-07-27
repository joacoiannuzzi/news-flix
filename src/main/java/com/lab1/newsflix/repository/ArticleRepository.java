package com.lab1.newsflix.repository;

import com.lab1.newsflix.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

//    https://attacomsian.com/blog/spring-data-jpa-query-annotation

    Collection<Article> findByNewspaper(String newspaper);

    Collection<Article> findByCategory(String category);

    @Query(value = "select distinct category from article ", nativeQuery = true)
    List<String> getCategories();

    @Query(value = "select distinct newspaper from article ", nativeQuery = true)
    List<String> getNewspapers();

    @Query(value = "SELECT art FROM Article art WHERE art.date BETWEEN :dateFrom AND :dateTo ")
    Collection<Article> getArticlesByDateBetween(@Param("dateFrom") Calendar dateFrom, @Param("dateTo") Calendar dateTo);

//    Collection<Article> getAllByDateBetweenAndNewspaperEqualsAndCategoryEqualsAndBodyContains(Calendar date, Calendar date2, String newspaper, String category, @Size(max = 50000) String body);

//    Collection<Article> getAllByDateBetweenAndNewspaperEqualsAndCategoryEqualsAndBodyMatches(Calendar date, Calendar date2, String newspaper, String category, @Size(max = 50000) String body);


    Boolean existsByUrl(String url);

    Collection<Article> findAllByNewspaperIsNot(String newspaper);

}
