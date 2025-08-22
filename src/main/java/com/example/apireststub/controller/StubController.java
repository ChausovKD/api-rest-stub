package com.example.apireststub.controller;

import com.example.apireststub.dto.AuthResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class StubController {
    private static final String BASE_STRINGIFY_JSON_OK_STATUS = "{\"login\":\"Login1\",\"status\":\"ok\"}";
    private static final Random RANDOM = new Random();
    private static final int MIN_DELAY_MS = 1000;
    private static final int MAX_DELAY_MS = 2000;

    @GetMapping("/status")
    public String getSuccessStatus() {
        simulateLittleDelay();
        return BASE_STRINGIFY_JSON_OK_STATUS;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthResponse request) {
        simulateLittleDelay();
        return new AuthResponse(request.getLogin(), request.getPassword());
    }

    private void simulateLittleDelay() {
        try {
            int delay = MIN_DELAY_MS + RANDOM.nextInt(MAX_DELAY_MS - MIN_DELAY_MS + 1);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
