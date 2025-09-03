package com.example.apireststub.controller;

import com.example.apireststub.dto.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class StubController {

    private static final List<byte[]> memoryLeak = new ArrayList<>();

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
