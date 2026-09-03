package com.example.msaccountreservation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDto {

    private UUID id;
    private String fullName;
    private String citizenship;
    private String clientType;
    private String documentNumber;
    private String documentSeries;
    private String documentType;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;

}
