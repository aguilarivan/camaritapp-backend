package com.gaulab.weshoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PartyDTO {
    private String name;
    private LocalDate date;
    private Long organizerId;
}
