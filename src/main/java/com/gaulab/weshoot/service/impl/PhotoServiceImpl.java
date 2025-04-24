package com.gaulab.weshoot.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gaulab.weshoot.model.PHOTO_STATE;
import com.gaulab.weshoot.exception.ResourceNotFoundException;
import com.gaulab.weshoot.model.Party;
import com.gaulab.weshoot.model.Photo;
import com.gaulab.weshoot.service.PhotoService;
import com.gaulab.weshoot.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private PartyServiceImpl partyService;
    @Autowired
    private Cloudinary cloudinary;
    private static final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);

    @Override
    public String uploadPhoto(String partyAccessCode, MultipartFile file){
        Party party = partyService.getPartyByAccessCode(partyAccessCode);
        logger.info("Uploading photo for party with access code: {}", partyAccessCode);
        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("secure_url");
            String publicId = (String) uploadResult.get("public_id");
            logger.info("Photo uploaded successfully. URL: {}", url);
            Photo photo = new Photo();
            photo.setUrl(url);
            photo.setPublicId(publicId);
            photo.setParty(party);
            photo.setState(PHOTO_STATE.PENDING);
            return photoRepository.save(photo).getUrl();
        }catch (IOException e) {
            throw new RuntimeException("Error uploading photo from Cloudinary", e);
        }
    }

    @Override
    public List<Photo> getPhotosByPartyAccessCode(String accessCode) {
        List <Photo> photos = photoRepository.findByParty_AccessCode(accessCode);
        return photos;
    }

    @Override
    public void deletePhoto(String photoURL) {
        Photo photo = photoRepository.findByUrl(photoURL);
        if (photo == null) {
            throw new ResourceNotFoundException("Photo not found");
        }

        try {
            // Eliminar de Cloudinary
            Map result = cloudinary.uploader().destroy(photo.getPublicId(), ObjectUtils.emptyMap());
            if (!"ok".equals(result.get("result"))) {
                throw new RuntimeException("Failed to delete photo from Cloudinary");
            }

            // Eliminar de la base de datos
            photoRepository.delete(photo);

        } catch (IOException e) {
            throw new RuntimeException("Error deleting photo from Cloudinary", e);
        }
    }
}
