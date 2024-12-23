package com.synchrony.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class DataService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private UserRepository userRepository;

    // Create or Update User (CRUD)
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    // Retrieve User from MySQL
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Delete User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Cache Data and Handle Redis
    public void performConcurrentTasks(Long userId) {
        Callable<User> dbTask = () -> {
            // Perform DB read (get user from MySQL)
            User user = getUser(userId);
            if (user != null) {
                redisCacheService.cacheData("user_" + userId, user); // Cache the user
            }
            return user;
        };

        Callable<User> cacheTask = () -> {
            // Retrieve data from Redis cache
            User cachedUser = (User) redisCacheService.getCachedData("user_" + userId);
            return cachedUser;
        };

        List<Callable<User>> tasks = new ArrayList<>();
        tasks.add(dbTask);
        tasks.add(cacheTask);

        try {
            List<Future<User>> results = executorService.invokeAll(tasks);
            for (Future<User> result : results) {
                System.out.println("Task Result: " + result.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    // List all Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
