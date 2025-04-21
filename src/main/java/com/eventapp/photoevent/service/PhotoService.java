package com.eventapp.photoevent.service;

import com.eventapp.photoevent.model.Photo;
import com.eventapp.photoevent.repository.PhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Service
public class PhotoService {

    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final String[] ALLOWED_TYPES = {"image/jpeg", "image/png"};

    private final PhotoRepository photoRepository;

    @Value("${file.storage.path:/app/storage}")
    private String storagePath;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo uploadPhoto(MultipartFile file) throws IOException {
        // Validate file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds 10MB");
        }
        if (!Arrays.asList(ALLOWED_TYPES).contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid file type. Only JPEG and PNG are allowed");
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(storagePath, fileName);

        // Ensure storage directory exists
        Files.createDirectories(filePath.getParent());

        // Save file to volume
        Files.copy(file.getInputStream(), filePath);

        // Save photo metadata
        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setContentType(file.getContentType());
        photo.setUploadedAt(LocalDateTime.now());
        photo.setDisplayed(false);

        logger.info("Uploaded photo: {}", fileName);
        return photoRepository.save(photo);
    }

    public Photo getNextPhotoMetadata() {
        Photo photo = photoRepository.findFirstByDisplayedFalseOrderByUploadedAtAsc()
                .orElseThrow(() -> new RuntimeException("No undisplayed photos available"));

        // Mark as displayed
        photo.setDisplayed(true);
        photoRepository.save(photo);

        logger.info("Serving photo: {}", photo.getFileName());
        return photo;
    }

    public Path getPhotoPath(String fileName) {
        return Paths.get(storagePath, fileName);
    }
}