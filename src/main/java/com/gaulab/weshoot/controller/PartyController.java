package com.gaulab.weshoot.controller;

import com.gaulab.weshoot.dto.PartyDTO;
import com.gaulab.weshoot.model.Party;
import com.gaulab.weshoot.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/party")
public class PartyController {
    @Autowired
    private PartyService partyService;

    @PostMapping("/create")
    public ResponseEntity<Party> createParty(@RequestBody PartyDTO partyDto) {
        Party party = partyService.createParty(partyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(party);
    }

    @GetMapping("/accessCode/{accessCode}")
    public ResponseEntity<Party> getPartyByAccessCode(@PathVariable String accessCode) {
        Party party = partyService.getPartyByAccessCode(accessCode);
        return ResponseEntity.ok(party);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Party>> getAllParties() {
        List<Party> parties = partyService.getAllParties();
        return ResponseEntity.ok(parties);
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<Party>> getPartiesByOrganizerId(@PathVariable Long organizerId) {
        List<Party> parties = partyService.getPartiesByOrganizerId(organizerId);
        return ResponseEntity.ok(parties);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Party> updateParty(@PathVariable Long id,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String date) {
        Party party = partyService.updateParty(id, name, date);
        return ResponseEntity.ok(party);
    }

    @DeleteMapping("/delete/{partyId}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long partyId) {
        partyService.deleteParty(partyId);
        return ResponseEntity.noContent().build();
    }
}
