package com.eventapp.photoevent.repository;

import com.eventapp.photoevent.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    // Custom query methods can be defined here if needed
}
