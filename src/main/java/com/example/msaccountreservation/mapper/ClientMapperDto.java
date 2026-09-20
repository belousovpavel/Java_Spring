//package com.example.msaccountreservation.mapper;
//
//import com.example.msaccountreservation.dto.ClientRequestDto;
//import com.example.msaccountreservation.dto.ClientResponseDto;
//import com.example.msaccountreservation.entity.ClientEntity;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//
//@Component
//public class ClientMapperDto {
//
//    public ClientEntity toEntity(ClientRequestDto requestDto){
//        return ClientEntity.builder()
//                .fullName(requestDto.getFullName())
//                .citizenship(requestDto.getCitizenship())
//                .clientType(requestDto.getClientType())
//                .documentNumber(requestDto.getDocumentNumber())
//                .documentSeries(requestDto.getDocumentSeries())
//                .documentType(requestDto.getDocumentType())
//                .build();
//    }
//
//    public ClientResponseDto toResponse(ClientEntity clientEntity){
//        return ClientResponseDto.builder()
//                .id(clientEntity.getId())
//                .fullName(clientEntity.getFullName())
//                .citizenship(clientEntity.getCitizenship())
//                .clientType(clientEntity.getClientType())
//                .documentNumber(clientEntity.getDocumentNumber())
//                .documentSeries(clientEntity.getDocumentSeries())
//                .documentType(clientEntity.getDocumentType())
//                .status(String.valueOf(clientEntity.getStatus()))
//                .createdAt(toInstant(clientEntity.getCreatedAt()))
//                .updatedAt(toInstant(clientEntity.getUpdatedAt()))
//                .build();
//    }
//
//    private Instant toInstant(LocalDateTime localDateTime) {
//        if (localDateTime == null) {
//            return null;
//        }
//        return localDateTime.atZone(ZoneOffset.UTC).toInstant();
//    }
//
//}
