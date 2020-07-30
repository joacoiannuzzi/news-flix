package com.lab1.newsflix;

import com.lab1.newsflix.model.Role;
import com.lab1.newsflix.model.RoleName;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.repository.RoleRepository;
import com.lab1.newsflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;

@Service
public class InitDB implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) { // este metodo se llama automaticamente cuando se inicia la aplicacion porque implenta CommandLineRunner

        Role adminRole = new Role(RoleName.ROLE_ADMIN);
        Role userRole = new Role(RoleName.ROLE_USER);

        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 4); //MAY 4th


        User admin = new User("Admin", "", "admin@admin.com", passwordEncoder.encode("admin"));
        admin.setRoles(Collections.singleton(adminRole));
        userRepository.save(admin);

    }
}
