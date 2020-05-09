package com.lab1.newsflix;


import com.lab1.newsflix.scrappers.AbstractScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadArticles implements CommandLineRunner {

    @Autowired
    private List<AbstractScraper> scrapers;

    @Override
    public void run(String... args) { // este metodo se llama automaticamente cuando se inicia la aplicacion porque implenta CommandLineRunner
        scrapers.forEach(AbstractScraper::scrap);
    }
}
