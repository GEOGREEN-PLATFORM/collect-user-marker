package com.example.collect_user_marker.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsDTO {

    @Size(max = 12)
    private String userPhone;

    @Email
    private String userEmail;
}