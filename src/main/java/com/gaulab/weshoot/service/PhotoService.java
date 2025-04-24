package com.gaulab.weshoot.service;

import com.gaulab.shotandshare.persistance.models.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {
    String uploadPhoto(String partyAccessCode, MultipartFile photo);
    List<Photo> getPhotosByPartyAccessCode(String accessCode);
    void deletePhoto(String photoURL);
}
