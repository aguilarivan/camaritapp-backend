package com.gaulab.weshoot.repository;

import com.gaulab.weshoot.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    // Custom query methods can be defined here if needed
    Party findByAccessCode(String accessCode);
    List<Party> findByOrganizerId(Long organizerId);
}
