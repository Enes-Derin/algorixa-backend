package com.enesderin.softvra_backend.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageResponse {

    private Long id;

    private String name;

    private String email;

    private String message;

    private LocalDateTime createdAt;

    private Boolean read = false;
}
