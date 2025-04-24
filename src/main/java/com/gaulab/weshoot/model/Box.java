package com.gaulab.weshoot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Box {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String label;
    private String color;

    // Constructors
    public Box() {}

    public Box(String label, String color) {
        this.label = label;
        this.color = color;
    }

}
