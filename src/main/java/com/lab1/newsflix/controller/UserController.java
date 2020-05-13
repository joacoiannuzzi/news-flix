package com.lab1.newsflix.controller;


import com.lab1.newsflix.exception.ResourceNotFoundException;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.payload.UserIdentityAvailability;
import com.lab1.newsflix.payload.UserProfile;
import com.lab1.newsflix.payload.UserSummary;
import com.lab1.newsflix.repository.UserRepository;
import com.lab1.newsflix.security.CurrentUser;
import com.lab1.newsflix.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/all")
    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getEmail(), currentUser.getFirstName(), currentUser.getLastName());
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/{id}")
    public UserProfile getUserProfile(@PathVariable(value = "id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return new UserProfile(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

//    @RequestMapping(value = "/secured/loginSuccess", method = RequestMethod.POST)
//    public User loginSuccess(User user) {
//
//        Set<Role> roles = new HashSet<>();
//        roles.addAll((Collection<? extends Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//        user.setRole(roles);
//        user.setIsActive(true);
//
//        return user;
//    }

}
