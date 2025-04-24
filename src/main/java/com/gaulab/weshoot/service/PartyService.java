package com.gaulab.weshoot.service;

import com.gaulab.weshoot.dto.PartyDTO;
import com.gaulab.weshoot.model.Party;

import java.util.List;

public interface PartyService {
    public Party createParty(PartyDTO partyDto);
    public Party getPartyByAccessCode(String accessCode);
    public List<Party> getAllParties();
    public List<Party> getPartiesByOrganizerId(Long organizerId);
    public Party updateParty(Long id, String name, String stringDate);
    public Party addPhotoToParty(Long partyId, Long photoId);
    public Party removePhotoFromParty(Long partyId, Long photoId);
    public void deleteParty(Long partyId);
}
