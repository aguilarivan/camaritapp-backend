package com.gaulab.weshoot.repository;

import com.gaulab.weshoot.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    // Custom query methods can be defined here if needed
    public List<Photo> findByParty_AccessCode(String accessCode);
    public Photo findByUrl(String url);
}
