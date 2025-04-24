package com.gaulab.weshoot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String url; // path to the photo in the storage
    @Column(nullable = false)
    private String publicId;
    @Column(nullable = false)
    private PHOTO_STATE state = PHOTO_STATE.PENDING;
    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    @JsonIgnore
    private Party party;
}
