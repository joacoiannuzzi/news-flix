package com.lab1.newsflix.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScraperManager {

    @Autowired
    private List<AbstractScraper> scrapers;

    public void scrap() {
        scrapers.forEach(AbstractScraper::scrap);
    }
}
