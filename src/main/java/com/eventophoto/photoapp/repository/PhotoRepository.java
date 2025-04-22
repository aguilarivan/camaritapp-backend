package com.eventophoto.photoapp.repository;

import com.eventophoto.photoapp.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findFirstByDisplayedFalseOrderByUploadedAtAsc();
}