package com.lab1.repository;

import com.lab1.scrappers.ClarinScraper;
import com.lab1.scrappers.InfobaeScraper;
import com.lab1.scrappers.LaNacionScraper;
import com.lab1.util.EntityManagers;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ScrapperTest {

    @Before
    public void setUp() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("test");
        EntityManagers.setFactory(managerFactory);
    }

    @Test
    public void createLaNacionScrapper() {
        final LaNacionScraper laNacionScraper = new LaNacionScraper();

        laNacionScraper.scrap();
    }

}
