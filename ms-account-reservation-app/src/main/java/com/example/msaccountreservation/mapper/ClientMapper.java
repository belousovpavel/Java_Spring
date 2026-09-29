package com.example.msaccountreservation.mapper;

import com.example.msaccountreservation.entity.ClientEntity;
import com.example.msaccountreservation.model.ClientRequest;
import com.example.msaccountreservation.model.ClientResponse;
import com.example.msaccountreservation.model.ClientResponseById;
import com.example.msaccountreservation.model.UpdateClient;
import org.mapstruct.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "status",ignore = true)
    @Mapping(target = "accounts",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    ClientEntity toEntity(ClientRequest request);

    @Mapping(target = "createdAt", expression = "java(mapToInstant(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapToInstant(entity.getUpdatedAt()))")
    ClientResponse toResponse(ClientEntity entity);

    @Mapping(target = "createdAt", expression = "java(mapToInstant(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapToInstant(entity.getUpdatedAt()))")
    @Mapping(target = "hasAccounts",ignore = true)
    ClientResponseById toResponseById(ClientEntity entity);

    List<ClientResponse> toResponseList(List<ClientEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget ClientEntity entity, UpdateClient updateClient);

    default Instant mapToInstant(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toInstant(ZoneOffset.UTC);
    }


}
