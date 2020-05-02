package com.lab1.newsflix.controller;


import com.lab1.newsflix.model.User;
import com.lab1.newsflix.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    Collection<User> categories() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    ResponseEntity<?> getUser(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/user")
    ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
        User result = userRepository.save(user);
        return ResponseEntity.created(new URI("/api/user" + result.getEmail())).body(result);

    }

    @PutMapping("/user/{id}")
    ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        User result = userRepository.save(user);
        return ResponseEntity.ok().body(result);
    }


    @DeleteMapping("/user/{id}")
    ResponseEntity<?> deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
