package com.example.apireststub.controller;

import com.example.apireststub.dao.DataBaseWorker;
import com.example.apireststub.dto.AuthResponse;
import com.example.apireststub.entity.User;
import com.example.apireststub.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class StubController {

    private static final List<byte[]> memoryLeak = new ArrayList<>();

    @Autowired
    private DataBaseWorker dataBaseWorker;

    @GetMapping("/status")
    public ResponseEntity<String> getSuccessStatus() {
        simulateLittleDelay();
        return ResponseEntity.ok("{\"login\":\"Login1\",\"status\":\"ok\"}");
    }

    @GetMapping("/leak")
    public ResponseEntity<String> createMemoryLeak() {
        simulateLittleDelay();
        int bytesToLeak = Integer.parseInt(System.getProperty("leak.size.bytes", "0"));
        memoryLeak.add(new byte[bytesToLeak]);
        int totalLeakedKB = (int) Math.ceil(memoryLeak.size() * bytesToLeak / 1024.0);
        return ResponseEntity.ok("{\"totalLeakedKB\":" + totalLeakedKB + "}");
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody AuthResponse request) {
        simulateLittleDelay();
        User newUser = new User(request.getLogin(), request.getPassword(),
                request.getDate(), request.getEmail());
        dataBaseWorker.createUser(newUser);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        simulateLittleDelay();
        User foundUser = dataBaseWorker.selectUserByLogin(login);
        if (foundUser != null) {
            return ResponseEntity.ok(foundUser);
        } else {
            throw new UserNotFoundException("User with login '" + login + "' not found in database");
        }
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
