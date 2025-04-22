package com.eventapp.photoevent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PhotoService {

    @Value("${photo.storage.path}")
    private String photoStoragePath;

    private final AtomicInteger index = new AtomicInteger(0);

    public void savePhoto(MultipartFile file) throws IOException {
        File dir = new File(photoStoragePath);
        if (!dir.exists()) dir.mkdirs();

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path destination = Paths.get(photoStoragePath, filename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public File getNextPhoto() {
        File dir = new File(photoStoragePath);
        File[] files = dir.listFiles((d, name) -> {
            String lower = name.toLowerCase();
            return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png");
        });

        if (files == null || files.length == 0) return null;

        files = java.util.Arrays.stream(files)
                .sorted(Comparator.comparing(File::getName))
                .toArray(File[]::new);

        int fileCount = files.length;
        int idx = index.getAndUpdate(i -> (i + 1) % fileCount);
        return files[idx];
    }
}
