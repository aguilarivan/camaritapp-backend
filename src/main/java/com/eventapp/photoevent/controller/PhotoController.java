package com.eventapp.photoevent.controller;

import com.eventapp.photoevent.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            photoService.savePhoto(file);
            return ResponseEntity.ok("Photo uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload photo.");
        }
    }

    @GetMapping("/next")
    public ResponseEntity<FileSystemResource> getNextPhoto() {
        File file = photoService.getNextPhoto();
        if (file == null) {
            return ResponseEntity.noContent().build();
        }

        FileSystemResource resource = new FileSystemResource(file);
        MediaType mediaType = file.getName().toLowerCase().endsWith(".png") ?
                MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }
}
