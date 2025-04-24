package com.gaulab.weshoot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "party")
@SQLDelete(sql = "UPDATE party SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false, unique = true)
    private String accessCode; // cambiar a uuid
    @Column
    @JsonIgnore // Ignore this field in JSON serialization
    private LocalDateTime deleted_at;
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonIgnore
    private User organizer;
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;
}
