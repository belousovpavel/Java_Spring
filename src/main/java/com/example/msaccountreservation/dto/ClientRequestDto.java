package com.example.msaccountreservation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDto {

    private String fullName;

    private String citizenship;

    private String clientType;

    private String documentNumber;

    private String documentSeries;

    private String documentType;

}
