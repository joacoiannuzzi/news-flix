package com.lab1.newsflix.controller;


import com.lab1.newsflix.exception.ResourceNotFoundException;
import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.payload.FavoriteRequest;
import com.lab1.newsflix.payload.UserIdentityAvailability;
import com.lab1.newsflix.payload.UserProfile;
import com.lab1.newsflix.repository.UserRepository;
import com.lab1.newsflix.security.CurrentUser;
import com.lab1.newsflix.security.UserPrincipal;
import com.lab1.newsflix.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/all")
    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserProfile getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserProfile(currentUser);
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/{id}")
    public UserProfile getUserProfile(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return new UserProfile(user);
    }

    @PostMapping("/addFavorite")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserProfile addFavorite(@Valid @RequestBody FavoriteRequest favoriteRequest) {
        Long articleId = favoriteRequest.getArticleId();
        Article article = articleService.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Long userId = favoriteRequest.getUserId();
        User updatedUser = userRepository.findById(userId).map(user -> {
            user.addOrRemoveFavorite(article);
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return new UserProfile(updatedUser);
    }

}
