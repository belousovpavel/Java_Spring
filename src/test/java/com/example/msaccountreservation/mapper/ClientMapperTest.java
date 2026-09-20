package com.example.msaccountreservation.mapper;

import com.example.msaccountreservation.entity.ClientEntity;
import com.example.msaccountreservation.model.ClientRequest;
import com.example.msaccountreservation.model.ClientResponse;
import com.example.msaccountreservation.model.ClientResponseById;
import com.example.msaccountreservation.model.ClientStatus;
import com.example.msaccountreservation.model.UpdateClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ClientMapperTest {

    private ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    private UUID testId;
    private ClientEntity testEntity;
    private ClientRequest testRequest;
    private UpdateClient updateClient;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();

        testEntity = ClientEntity.builder()
                .id(testId)
                .fullName("Иван Иванов")
                .citizenship("RU")
                .clientType("INDIVIDUAL")
                .documentNumber("1234567890")
                .documentSeries("1234")
                .documentType("PASSPORT")
                .status(ClientStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testRequest = new ClientRequest();
        testRequest.setFullName("Иван Иванов");
        testRequest.setCitizenship("RU");
        testRequest.setClientType("INDIVIDUAL");
        testRequest.setDocumentNumber("1234567890");
        testRequest.setDocumentSeries("1234");
        testRequest.setDocumentType("PASSPORT");

        updateClient = new UpdateClient();
        updateClient.setFullName("Петр Петров");
        updateClient.setCitizenship("BY");
        updateClient.setClientType("INDIVIDUAL");
    }

    @Test
    @DisplayName("Маппинг ClientRequest -> ClientEntity")
    void toEntity_Success() {
        // when
        ClientEntity result = clientMapper.toEntity(testRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getFullName()).isEqualTo(testRequest.getFullName());
        assertThat(result.getCitizenship()).isEqualTo(testRequest.getCitizenship());
        assertThat(result.getClientType()).isEqualTo(testRequest.getClientType());
        assertThat(result.getDocumentNumber()).isEqualTo(testRequest.getDocumentNumber());
        assertThat(result.getDocumentSeries()).isEqualTo(testRequest.getDocumentSeries());
        assertThat(result.getDocumentType()).isEqualTo(testRequest.getDocumentType());
        assertThat(result.getStatus()).isNull();
        assertThat(result.getCreatedAt()).isNull();
        assertThat(result.getUpdatedAt()).isNull();
        assertThat(result.getAccounts()).isNull();
    }

    @Test
    @DisplayName("Маппинг ClientEntity -> ClientResponse")
    void toResponse_Success() {
        // when
        ClientResponse result = clientMapper.toResponse(testEntity);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testEntity.getId());
        assertThat(result.getFullName()).isEqualTo(testEntity.getFullName());
        assertThat(result.getCitizenship()).isEqualTo(testEntity.getCitizenship());
        assertThat(result.getClientType()).isEqualTo(testEntity.getClientType());
        assertThat(result.getStatus()).isEqualTo(testEntity.getStatus());
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Маппинг ClientEntity -> ClientResponseById")
    void toResponseById_Success() {
        // when
        ClientResponseById result = clientMapper.toResponseById(testEntity);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testEntity.getId());
        assertThat(result.getFullName()).isEqualTo(testEntity.getFullName());
        assertThat(result.getCitizenship()).isEqualTo(testEntity.getCitizenship());
        assertThat(result.getClientType()).isEqualTo(testEntity.getClientType());
        assertThat(result.getStatus()).isEqualTo(testEntity.getStatus());
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();
        assertThat(result.getHasAccounts()).isNull();
    }

    @Test
    @DisplayName("Маппинг списка ClientEntity -> ClientResponse")
    void toResponseList_Success() {
        // given
        List<ClientEntity> entities = List.of(testEntity);

        // when
        List<ClientResponse> result = clientMapper.toResponseList(entities);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(testEntity.getId());
        assertThat(result.get(0).getFullName()).isEqualTo(testEntity.getFullName());
    }

    @Test
    @DisplayName("Обновление ClientEntity из UpdateClient")
    void updateEntity_Success() {
        // given
        ClientEntity entity = ClientEntity.builder()
                .id(testId)
                .fullName("Иван Иванов")
                .citizenship("RU")
                .clientType("INDIVIDUAL")
                .documentNumber("1234567890")
                .documentSeries("1234")
                .documentType("PASSPORT")
                .status(ClientStatus.ACTIVE)
                .build();

        // when
        clientMapper.updateEntity(entity, updateClient);

        // then
        assertThat(entity.getId()).isEqualTo(testId);
        assertThat(entity.getFullName()).isEqualTo("Петр Петров");
        assertThat(entity.getCitizenship()).isEqualTo("BY");
        assertThat(entity.getClientType()).isEqualTo("INDIVIDUAL");
        // Проверяем, что поля с null не изменились
        assertThat(entity.getDocumentNumber()).isEqualTo("1234567890");
        assertThat(entity.getDocumentSeries()).isEqualTo("1234");
        assertThat(entity.getDocumentType()).isEqualTo("PASSPORT");
        assertThat(entity.getStatus()).isEqualTo(ClientStatus.ACTIVE);
    }

    @Test
    @DisplayName("Обновление ClientEntity с null значениями - игнорирование")
    void updateEntity_WithNullValues_IgnoresNulls() {
        // given
        UpdateClient emptyUpdate = new UpdateClient();
        ClientEntity entity = ClientEntity.builder()
                .id(testId)
                .fullName("Иван Иванов")
                .citizenship("RU")
                .build();

        // when
        clientMapper.updateEntity(entity, emptyUpdate);

        // then
        assertThat(entity.getFullName()).isEqualTo("Иван Иванов");
        assertThat(entity.getCitizenship()).isEqualTo("RU");
        // Проверяем, что поля не изменились
        assertThat(entity.getId()).isEqualTo(testId);
    }

    @Test
    @DisplayName("Маппинг LocalDateTime -> Instant")
    void mapToInstant_Success() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Instant expected = now.toInstant(ZoneOffset.UTC);

        // when
        Instant result = clientMapper.mapToInstant(now);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Маппинг null LocalDateTime -> null Instant")
    void mapToInstant_Null_ReturnsNull() {
        // when
        Instant result = clientMapper.mapToInstant(null);

        // then
        assertThat(result).isNull();
    }
}
