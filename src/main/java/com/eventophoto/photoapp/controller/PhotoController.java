package com.eventophoto.photoapp.controller;


import com.eventophoto.photoapp.model.Photo;
import com.eventophoto.photoapp.service.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Photo> uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Photo photo = photoService.uploadPhoto(file);
        return ResponseEntity.ok(photo);
    }

    @GetMapping("/next")
    public ResponseEntity<byte[]> getNextPhoto() throws IOException {
        Photo photo = photoService.getNextPhoto();
        byte[] content = photoService.getPhotoContent(photo.getFilePath());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Ajusta según el tipo de imagen
                .body(content);
    }
}