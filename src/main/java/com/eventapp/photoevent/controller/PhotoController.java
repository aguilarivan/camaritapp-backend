package com.eventapp.photoevent.controller;

import com.eventapp.photoevent.model.Photo;
import com.eventapp.photoevent.service.PhotoService;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/upload")
    public ResponseEntity<Photo> uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Photo photo = photoService.uploadPhoto(file);
        return ResponseEntity.ok(photo);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/next")
    public ResponseEntity<Resource> getNextPhoto() {
        Photo photo = photoService.getNextPhoto();
        if (photo == null) {
            return ResponseEntity.noContent().build();
        }
        Resource resource = new PathResource(Paths.get(photo.getFilePath()));
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Ajusta según el tipo de imagen
                .body(resource);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public ResponseEntity<List<Photo>> getAllPhotos() {
        List<Photo> photos = photoService.getAllPhotos();
        return ResponseEntity.ok(photos);
    }
}