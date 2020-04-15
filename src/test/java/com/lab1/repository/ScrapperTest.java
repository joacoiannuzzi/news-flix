package com.lab1.repository;

import com.lab1.scrappers.ClarinScraper;
import com.lab1.scrappers.InfobaeScraper;
import com.lab1.scrappers.LaNacionScraper;
import com.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ScrapperTest {

    private EntityManagerFactory managerFactory;

    @Before
    public void setUp() {
        managerFactory = Persistence.createEntityManagerFactory("test");
        EntityManagers.setFactory(managerFactory);
    }

    @After
    public void close() {
        managerFactory.close(); //Make sure it only closes once, or when running multiple tests = fail.
    }

    @Test
    public void createLaNacionScrapper() {
        final LaNacionScraper laNacionScraper = new LaNacionScraper();

        laNacionScraper.scrap();
    }
//    @Test
//    public void createClarinScrapper() {
//        final ClarinScraper clarinScraper = new ClarinScraper();
//
//        clarinScraper.scrap();
//    }

    @Test
    public void createInfobaeScrapper() {
        final InfobaeScraper infobaeScraper = new InfobaeScraper();

        infobaeScraper.scrap();
    }

}
