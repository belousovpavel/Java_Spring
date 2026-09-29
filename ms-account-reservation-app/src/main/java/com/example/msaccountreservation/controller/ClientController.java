package com.example.msaccountreservation.controller;

import com.example.msaccountreservation.api.ClientsApi;
import com.example.msaccountreservation.model.*;
import com.example.msaccountreservation.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ClientController implements ClientsApi {

    private final ClientService clientService;

    @Override
    public ClientExistResponse checkClient(String contentType, String accept, UUID clientId) {
        log.info("GET /clients/{}/exists - Проверка существования", clientId);
        return clientService.checkClientExists(clientId);
    }

    @Override
    public ClientResponse createClient(String contentType, String accept, ClientRequest clientRequest) {
        log.info("POST /clients - Создание нового клиента");
        return clientService.createClient(clientRequest);
    }

    @Override
    public void deleteClientById(String contentType, String accept, UUID clientId) {
        log.info("DELETE /clients/{} - Удаление клиента", clientId);
        clientService.deleteClient(clientId);
        log.info("Клиент успешно удален: {}", clientId);
    }

    @Override
    public ClientsResponse getClients(String contentType, String accept) {
        log.info("GET /clients - Получение клиентов");
        return clientService.getClients();
    }

    @Override
    public ClientResponseById getClientsById(String contentType, String accept, UUID clientsId) {
        log.info("GET /clients/{clientId} - Получение клиента по Id");
        return clientService.getClientById(clientsId);
    }

    @Override
    public void updateClient(String contentType, String accept, UUID clientsId, UpdateClient updateClient) {
        log.info("PUT /clients/{} - Обновление клиента", clientsId);
        clientService.updateClient(clientsId, updateClient);
        log.info("Клиент обновлен: {}", clientsId);
    }
}
