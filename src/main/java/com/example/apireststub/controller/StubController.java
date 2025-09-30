package com.example.apireststub.controller;

import com.example.apireststub.dao.DataBaseWorker;
import com.example.apireststub.dto.AuthResponse;
import com.example.apireststub.entity.User;
import com.example.apireststub.exception.UserNotFoundException;
import com.example.apireststub.util.FileWorker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class StubController {
    @Autowired
    private DataBaseWorker dataBaseWorker;

    @Autowired
    private FileWorker fileWorker;

    @GetMapping("/users/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        simulateLittleDelay();
        User foundUser = dataBaseWorker.selectUserByLogin(login);
        if (foundUser != null) {
            fileWorker.writeEntityToFile(foundUser);
            return ResponseEntity.ok(foundUser);
        } else {
            throw new UserNotFoundException("User with login '" + login + "' not found in database");
        }
    }

    @GetMapping("randomUser")
    public ResponseEntity<String> getRandomUser() {
        simulateLittleDelay();
        String randomUserJson = fileWorker.readRandomLineFromFile();
        return ResponseEntity.ok(randomUserJson);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody AuthResponse request) {
        simulateLittleDelay();
        User newUser = new User(request.getLogin(), request.getPassword(),
                LocalDateTime.now(), request.getEmail());
        dataBaseWorker.createUser(newUser);
        return ResponseEntity.ok(newUser);
    }

    private void simulateLittleDelay() {
        Random random = new Random();
        try {
            int delay = 1000 + random.nextInt(2000 - 1000 + 1);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
