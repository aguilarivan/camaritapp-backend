package com.eventophoto.photoapp.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class FileStorageConfig {

    @Value("${file.storage.path}")
    private String storagePath;

    @PostConstruct
    public void init() throws IOException {
        Path path = Path.of(storagePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}