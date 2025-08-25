package com.example.apireststub.controller;

import com.example.apireststub.dto.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class StubController {

    @GetMapping("/status")
    public ResponseEntity<String> getSuccessStatus() {
        simulateLittleDelay();
        return ResponseEntity.ok("{\"login\":\"Login1\",\"status\":\"ok\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthResponse request) {
        simulateLittleDelay();
        return ResponseEntity.ok(request);
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
