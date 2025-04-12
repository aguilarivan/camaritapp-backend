package com.eventapp.photoevent.service;

import com.eventapp.photoevent.model.Photo;
import com.eventapp.photoevent.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final Path uploadDir;
    private final AtomicInteger currentPhotoIndex = new AtomicInteger(0);

    public PhotoService(PhotoRepository photoRepository, @Value("${file.upload-dir}") String uploadDir) {
        this.photoRepository = photoRepository;
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        createUploadDir();
    }

    private void createUploadDir() {
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de uploads", e);
        }
    }

    public Photo uploadPhoto(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setFilePath(filePath.toString());
        photo.setUploadedAt(LocalDateTime.now());

        return photoRepository.save(photo);
    }

    public Photo getNextPhoto() {
        List<Photo> photos = photoRepository.findAll();
        if (photos.isEmpty()) {
            return null;
        }
        int index = currentPhotoIndex.getAndIncrement() % photos.size();
        return photos.get(index);
    }

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
}