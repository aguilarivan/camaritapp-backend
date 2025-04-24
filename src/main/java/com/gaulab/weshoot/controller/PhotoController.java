package com.gaulab.weshoot.controller;

import com.gaulab.shotandshare.persistance.models.Photo;
import com.gaulab.shotandshare.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("partyAccessCode") String partyAccessCode) {
        String url = photoService.uploadPhoto(partyAccessCode, file);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{partyAccessCode}")
    public ResponseEntity<List<Photo>> getPhotos(@PathVariable String partyAccessCode) {
        List<Photo> photos = photoService.getPhotosByPartyAccessCode(partyAccessCode);
        return ResponseEntity.ok(photos);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("photoURL") String photoURL) {
        photoService.deletePhoto(photoURL);
        return ResponseEntity.noContent().build();
    }
}
