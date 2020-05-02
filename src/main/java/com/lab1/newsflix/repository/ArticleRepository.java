package com.lab1.newsflix.repository;

import com.lab1.newsflix.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Collection<Article> findByNewspaper(String newspaper);

    Collection<Article> findByCategory(String category);

    @Query(value="select distinct CATEGORY from article ", nativeQuery=true)
    List<Object> getCategories();

    @Query(value="select distinct DIARIO from article ", nativeQuery=true)
    List<Object> getNewspapers();

}
