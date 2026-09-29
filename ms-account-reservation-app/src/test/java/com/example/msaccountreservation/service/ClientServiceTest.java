package com.example.msaccountreservation.service;

import com.example.msaccountreservation.dao.ClientDaoService;
import com.example.msaccountreservation.entity.ClientEntity;
import com.example.msaccountreservation.exception.ClientAlreadyExistsException;
import com.example.msaccountreservation.exception.ClientNotFoundException;
import com.example.msaccountreservation.exception.InvalidDataException;
import com.example.msaccountreservation.mapper.ClientMapper;
import com.example.msaccountreservation.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientDaoService clientDaoService;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    private UUID testId;
    private ClientEntity testEntity;
    private ClientRequest testRequest;
    private ClientResponse testResponse;
    private ClientResponseById testResponseById;
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

        testResponse = new ClientResponse();
        testResponse.setId(testId);
        testResponse.setFullName("Иван Иванов");
        testResponse.setStatus(ClientStatus.ACTIVE.toString());

        testResponseById = new ClientResponseById();
        testResponseById.setId(testId);
        testResponseById.setFullName("Иван Иванов");
        testResponseById.setStatus(ClientStatus.ACTIVE.toString());

        updateClient = new UpdateClient();
        updateClient.setFullName("Петр Петров");
    }

    @Test
    @DisplayName("Успешное создание клиента")
    void createClient_Success() {
        // given
        when(clientDaoService.findByDocumentNumberAndDocumentSeries(
                testRequest.getDocumentNumber(),
                testRequest.getDocumentSeries()))
                .thenReturn(Optional.empty());
        when(clientMapper.toEntity(testRequest)).thenReturn(testEntity);
        when(clientDaoService.save(testEntity)).thenReturn(testEntity);
        when(clientMapper.toResponse(testEntity)).thenReturn(testResponse);

        // when
        ClientResponse result = clientService.createClient(testRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testId);
        assertThat(result.getFullName()).isEqualTo("Иван Иванов");
        assertThat(result.getStatus()).isEqualTo(ClientStatus.ACTIVE);

        verify(clientDaoService).findByDocumentNumberAndDocumentSeries(
                testRequest.getDocumentNumber(),
                testRequest.getDocumentSeries());
        verify(clientMapper).toEntity(testRequest);
        verify(clientDaoService).save(testEntity);
        verify(clientMapper).toResponse(testEntity);
    }

    @Test
    @DisplayName("Создание клиента с существующими документами - исключение")
    void createClient_AlreadyExists_ThrowsException() {
        // given
        when(clientDaoService.findByDocumentNumberAndDocumentSeries(
                testRequest.getDocumentNumber(),
                testRequest.getDocumentSeries()))
                .thenReturn(Optional.of(testEntity));

        // when & then
        assertThatThrownBy(() -> clientService.createClient(testRequest))
                .isInstanceOf(ClientAlreadyExistsException.class)
                .hasMessageContaining("Клиент с документом: серия '1234', номер '1234567890' уже существует");

        verify(clientDaoService).findByDocumentNumberAndDocumentSeries(
                testRequest.getDocumentNumber(),
                testRequest.getDocumentSeries());
        verify(clientMapper, never()).toEntity(any());
        verify(clientDaoService, never()).save(any());
    }

    @Test
    @DisplayName("Получение клиента по ID - успешно")
    void getClientById_Success() {
        // given
        when(clientDaoService.findById(testId)).thenReturn(Optional.of(testEntity));
        when(clientMapper.toResponseById(testEntity)).thenReturn(testResponseById);

        // when
        ClientResponseById result = clientService.getClientById(testId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testId);
        assertThat(result.getFullName()).isEqualTo("Иван Иванов");
        assertThat(result.getStatus()).isEqualTo(ClientStatus.ACTIVE);

        verify(clientDaoService).findById(testId);
        verify(clientMapper).toResponseById(testEntity);
    }

    @Test
    @DisplayName("Получение клиента по ID - клиент не найден")
    void getClientById_NotFound_ThrowsException() {
        // given
        when(clientDaoService.findById(testId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> clientService.getClientById(testId))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessageContaining("Клиент с таким id: " + testId + " не найден");

        verify(clientDaoService).findById(testId);
        verify(clientMapper, never()).toResponseById(any());
    }

    @Test
    @DisplayName("Получение клиента по ID с null - исключение")
    void getClientById_NullId_ThrowsException() {
        // when & then
        assertThatThrownBy(() -> clientService.getClientById(null))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("ID не может быть пустым");

        verify(clientDaoService, never()).findById(any());
    }

    @Test
    @DisplayName("Получение всех клиентов - успешно")
    void getClients_Success() {
        // given
        List<ClientEntity> entities = List.of(testEntity);
        List<ClientResponse> responses = List.of(testResponse);

        when(clientDaoService.findAll()).thenReturn(entities);
        when(clientMapper.toResponseList(entities)).thenReturn(responses);

        // when
        ClientsResponse result = clientService.getClients();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(testId);
        assertThat(result.getPageable()).isNotNull();
        assertThat(result.getPageable().getTotalElements()).isEqualTo(1L);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(1);

        verify(clientDaoService).findAll();
        verify(clientMapper).toResponseList(entities);
    }

    @Test
    @DisplayName("Получение пустого списка клиентов")
    void getClients_EmptyList_Success() {
        // given
        when(clientDaoService.findAll()).thenReturn(List.of());
        when(clientMapper.toResponseList(List.of())).thenReturn(List.of());

        // when
        ClientsResponse result = clientService.getClients();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getPageable().getTotalElements()).isZero();

        verify(clientDaoService).findAll();
        verify(clientMapper).toResponseList(List.of());
    }

    @Test
    @DisplayName("Удаление клиента (soft delete) - успешно")
    void deleteClient_Success() {
        // given
        when(clientDaoService.findById(testId)).thenReturn(Optional.of(testEntity));
        when(clientDaoService.save(testEntity)).thenReturn(testEntity);

        // when
        clientService.deleteClient(testId);

        // then
        assertThat(testEntity.getStatus()).isEqualTo(ClientStatus.DELETED);
        verify(clientDaoService).findById(testId);
        verify(clientDaoService).save(testEntity);
    }

    @Test
    @DisplayName("Удаление клиента - клиент не найден")
    void deleteClient_NotFound_ThrowsException() {
        // given
        when(clientDaoService.findById(testId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> clientService.deleteClient(testId))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessageContaining("Клиент с таким id: " + testId + " не найден");

        verify(clientDaoService).findById(testId);
        verify(clientDaoService, never()).save(any());
    }

    @Test
    @DisplayName("Обновление клиента - успешно")
    void updateClient_Success() {
        // given
        when(clientDaoService.findById(testId)).thenReturn(Optional.of(testEntity));
        when(clientDaoService.save(testEntity)).thenReturn(testEntity);

        // when
        clientService.updateClient(testId, updateClient);

        // then
        verify(clientDaoService).findById(testId);
        verify(clientMapper).updateEntity(testEntity, updateClient);
        verify(clientDaoService).save(testEntity);
    }

    @Test
    @DisplayName("Обновление клиента с пустым именем - исключение")
    void updateClient_EmptyName_ThrowsException() {
        // given
        updateClient.setFullName("");
        when(clientDaoService.findById(testId)).thenReturn(Optional.of(testEntity));

        // when & then
        assertThatThrownBy(() -> clientService.updateClient(testId, updateClient))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Имя не может быть пустым");

        verify(clientDaoService).findById(testId);
        verify(clientMapper, never()).updateEntity(any(), any());
        verify(clientDaoService, never()).save(any());
    }

    @Test
    @DisplayName("Обновление клиента - клиент не найден")
    void updateClient_NotFound_ThrowsException() {
        // given
        when(clientDaoService.findById(testId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> clientService.updateClient(testId, updateClient))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessageContaining("Клиент с таким id: " + testId + " не найден");

        verify(clientDaoService).findById(testId);
        verify(clientMapper, never()).updateEntity(any(), any());
        verify(clientDaoService, never()).save(any());
    }

    @Test
    @DisplayName("Проверка существования клиента - клиент существует")
    void checkClientExists_Exists_ReturnsTrue() {
        // given
        when(clientDaoService.findById(testId)).thenReturn(Optional.of(testEntity));

        // when
        ClientExistResponse result = clientService.checkClientExists(testId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getExists()).isTrue();
        assertThat(result.getClientId()).isEqualTo(testId);
        assertThat(result.getStatus()).isEqualTo(ClientStatus.ACTIVE);

        verify(clientDaoService).findById(testId);
    }

    @Test
    @DisplayName("Проверка существования клиента - клиент не существует")
    void checkClientExists_NotExists_ReturnsFalse() {
        // given
        when(clientDaoService.findById(testId)).thenReturn(Optional.empty());

        // when
        ClientExistResponse result = clientService.checkClientExists(testId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getExists()).isFalse();
        assertThat(result.getClientId()).isNull();
        assertThat(result.getStatus()).isNull();

        verify(clientDaoService).findById(testId);
    }
}
