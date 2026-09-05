package com.example.msaccountreservation.controller;

import com.example.msaccountreservation.model.*;
import com.example.msaccountreservation.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private UUID testId;
    private ClientRequest testRequest;
    private ClientResponse testResponse;
    private ClientResponseById testResponseById;
    private UpdateClient updateClient;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();

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
        testResponseById.setHasAccounts(false);

        updateClient = new UpdateClient();
        updateClient.setFullName("Петр Петров");
    }

    @Test
    @DisplayName("Проверка существования клиента через контроллер")
    void checkClient_Success() {
        // given
        ClientExistResponse expectedResponse = new ClientExistResponse();
        expectedResponse.setExists(true);
        expectedResponse.setClientId(testId);
        expectedResponse.setStatus(ClientStatus.ACTIVE);

        when(clientService.checkClientExists(testId)).thenReturn(expectedResponse);

        // when
        ClientExistResponse result = clientController.checkClient(
                "application/json",
                "application/json",
                testId
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getExists()).isTrue();
        assertThat(result.getClientId()).isEqualTo(testId);

        verify(clientService).checkClientExists(testId);
    }

    @Test
    @DisplayName("Создание клиента через контроллер")
    void createClient_Success() {
        // given
        when(clientService.createClient(testRequest)).thenReturn(testResponse);

        // when
        ClientResponse result = clientController.createClient(
                "application/json",
                "application/json",
                testRequest
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testId);
        assertThat(result.getFullName()).isEqualTo("Иван Иванов");

        verify(clientService).createClient(testRequest);
    }

    @Test
    @DisplayName("Удаление клиента через контроллер")
    void deleteClient_Success() {
        // given
        doNothing().when(clientService).deleteClient(testId);

        // when
        clientController.deleteClientById(
                "application/json",
                "application/json",
                testId
        );

        // then
        verify(clientService).deleteClient(testId);
    }

    @Test
    @DisplayName("Получение всех клиентов через контроллер")
    void getClients_Success() {
        // given
        ClientsResponse expectedResponse = new ClientsResponse();
        when(clientService.getClients()).thenReturn(expectedResponse);

        // when
        ClientsResponse result = clientController.getClients(
                "application/json",
                "application/json"
        );

        // then
        assertThat(result).isNotNull();
        verify(clientService).getClients();
    }

    @Test
    @DisplayName("Получение клиента по ID через контроллер")
    void getClientsById_Success() {
        // given
        when(clientService.getClientById(testId)).thenReturn(testResponseById);

        // when
        ClientResponseById result = clientController.getClientsById(
                "application/json",
                "application/json",
                testId
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testId);
        assertThat(result.getFullName()).isEqualTo("Иван Иванов");

        verify(clientService).getClientById(testId);
    }

    @Test
    @DisplayName("Обновление клиента через контроллер")
    void updateClient_Success() {
        // given
        doNothing().when(clientService).updateClient(testId, updateClient);

        // when
        clientController.updateClient(
                "application/json",
                "application/json",
                testId,
                updateClient
        );

        // then
        verify(clientService).updateClient(testId, updateClient);
    }
}
