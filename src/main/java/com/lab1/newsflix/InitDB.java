package com.lab1.newsflix;

import com.lab1.newsflix.model.Role;
import com.lab1.newsflix.model.RoleName;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.repository.RoleRepository;
import com.lab1.newsflix.repository.UserRepository;
import com.lab1.newsflix.scraper.ScraperManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class InitDB implements CommandLineRunner {



    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) { // este metodo se llama automaticamente cuando se inicia la aplicacion porque implenta CommandLineRunner
        roleRepository.save(new Role(RoleName.ROLE_USER));
        roleRepository.save(new Role(RoleName.ROLE_ADMIN));

        userRepository.save(new User("Aiden", "Pearson", "aiden.pearson@example.com", "123456"));

    }
}
