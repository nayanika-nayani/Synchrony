package com.synchrony.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private DataService dataService;

    // Create User
    @PostMapping
    public User saveOrUpdateUser(@RequestBody User user) {
        return dataService.saveOrUpdateUser(user);
    }

    // Get User by ID
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return dataService.getUser(id);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        dataService.deleteUser(id);
    }

    // List all Users
    @GetMapping
    public List<User> getAllUsers() {
        return dataService.getAllUsers();
    }

    // Perform Concurrent Tasks (DB + Cache)
    @GetMapping("/perform-tasks/{userId}")
    public String performTasks(@PathVariable Long userId) {
        dataService.performConcurrentTasks(userId);
        return "Concurrent tasks executed!";
    }
}

