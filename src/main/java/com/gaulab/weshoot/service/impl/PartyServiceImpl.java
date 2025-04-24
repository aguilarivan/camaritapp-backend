package com.gaulab.weshoot.service.impl;

import com.gaulab.weshoot.dto.PartyDTO;
import com.gaulab.weshoot.exception.ResourceNotFoundException;
import com.gaulab.weshoot.model.Party;
import com.gaulab.weshoot.model.Photo;
import com.gaulab.weshoot.model.User;
import com.gaulab.weshoot.repository.PartyRepository;
import com.gaulab.weshoot.service.PartyService;
import com.gaulab.weshoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PartyServiceImpl implements PartyService {
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private UserService userService;

    @Override
    public Party createParty(PartyDTO partyDto) {
        User user = userService.getUserById(partyDto.getOrganizerId());
        Party party = new Party();
        party.setName(partyDto.getName());
        party.setDate(partyDto.getDate());
        party.setAccessCode(UUID.randomUUID().toString());
        party.setOrganizer(user);
        party.setPhotos(new ArrayList<Photo>());
        return partyRepository.save(party);
    }

    @Override
    public Party getPartyByAccessCode(String accessCode) {
        Party party = partyRepository.findByAccessCode(accessCode);
        if (party == null) {
            throw new IllegalArgumentException("Party not found");
        }
        return party;
    }

    @Override
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    @Override
    public List<Party> getPartiesByOrganizerId(Long organizerId) {
        List<Party> parties = partyRepository.findByOrganizerId(organizerId);
        if (parties == null || parties.isEmpty()) {
            throw new ResourceNotFoundException("No parties found for user with ID " + organizerId);
        }
        return parties;
    }

    @Override
    public Party updateParty(Long id, String name, String stringDate) {
        Party party = partyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Party with ID " + id + " not found"));
        if (name != null && !name.isEmpty()) {
            party.setName(name);
        }
        if (stringDate != null && !stringDate.isEmpty()) {
            LocalDate date = LocalDate.parse(stringDate); // Assuming the date is in the format "yyyy-MM-dd"
            party.setDate(date);
        }
        return partyRepository.save(party);
    }

    @Override
    public Party addPhotoToParty(Long partyId, Long photoId) {
        return null;
    }

    @Override
    public Party removePhotoFromParty(Long partyId, Long photoId) {
        return null;
    }

    @Override
    public void deleteParty(Long partyId) {
        if (!partyRepository.existsById(partyId)) {
            throw new ResourceNotFoundException("Party with ID " + partyId + " not found");
        }
        partyRepository.deleteById(partyId);
    }
}
