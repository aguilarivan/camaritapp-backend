package com.eventapp.photoevent.controller;

import com.eventapp.photoevent.model.Photo;
import com.eventapp.photoevent.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Photo> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            Photo photo = photoService.uploadPhoto(file);
            return ResponseEntity.ok(photo);
        } catch (IllegalArgumentException e) {
            logger.error("Upload failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            logger.error("Upload failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/next")
    public ResponseEntity<Resource> getNextPhoto() {
        try {
            Photo photo = photoService.getNextPhotoMetadata();
            Resource resource = new PathResource(photoService.getPhotoPath(photo.getFileName()));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(photo.getContentType()))
                    .body(resource);
        } catch (RuntimeException e) {
            logger.error("Failed to get next photo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}