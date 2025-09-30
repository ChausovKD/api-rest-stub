package com.example.apireststub.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;

@Component
public class FileWorker {
    private final ObjectMapper objectMapper;

    public FileWorker(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void writeEntityToFile(Object entity) {
        try {
            String json = objectMapper.writeValueAsString(entity);
            Files.createDirectories(Paths.get("tmp"));
            Files.write(Paths.get("tmp/users.txt"), (json + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write entity to file", e);
        }
    }

    public String readRandomLineFromFile() {
        try (InputStream inputStream = getClass().getResourceAsStream("/static/data/predefined.json")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Predefined file not found in classpath: /static/data/predefined.json");
            }
            List<String> lines = new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .filter(line -> !line.trim().isEmpty())
                    .toList();
            if (lines.isEmpty()) {
                throw new IllegalStateException("Predefined file is empty");
            }
            Random random = new Random();
            return lines.get(random.nextInt(lines.size())).trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read random line from file", e);
        }
    }
}
