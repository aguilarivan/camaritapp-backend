package com.gaulab.weshoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;
}
