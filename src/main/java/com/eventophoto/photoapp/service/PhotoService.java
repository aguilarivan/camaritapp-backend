package com.eventophoto.photoapp.service;


import com.eventophoto.photoapp.exception.ResourceNotFoundException;
import com.eventophoto.photoapp.model.Photo;
import com.eventophoto.photoapp.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Value("${file.storage.path}")
    private String storagePath;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo uploadPhoto(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Path.of(storagePath, fileName);
        Files.write(filePath, file.getBytes());

        Photo photo = new Photo();
        photo.setFilePath(filePath.toString());
        photo.setUploadedAt(LocalDateTime.now());
        photo.setDisplayed(false);

        return photoRepository.save(photo);
    }

    public Photo getNextPhoto() {
        Photo photo = photoRepository.findFirstByDisplayedFalseOrderByUploadedAtAsc()
                .orElseThrow(() -> new ResourceNotFoundException("No undisplayed photos available"));
        photo.setDisplayed(true);
        return photoRepository.save(photo);
    }

    public byte[] getPhotoContent(String filePath) throws IOException {
        return Files.readAllBytes(Path.of(filePath));
    }
}