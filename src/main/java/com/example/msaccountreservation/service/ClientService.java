package com.example.msaccountreservation.service;

import com.example.msaccountreservation.dao.ClientDaoService;
import com.example.msaccountreservation.entity.ClientEntity;
import com.example.msaccountreservation.mapper.ClientMapper;
import com.example.msaccountreservation.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientMapper clientMapper;
    private final ClientDaoService clientDaoService;

    @Transactional
    public ClientResponse createClient(ClientRequest request){
        log.info("Создание нового клиента: {}", request.getFullName());
        ClientEntity entity = clientMapper.toEntity(request);
        ClientEntity saved = clientDaoService.save(entity);
        log.info("Клиент создан с ID: {}", saved.getId());
        return clientMapper.toResponse(saved);
    }

    public ClientResponseById getClientById(UUID id) {
        log.info("🔍 Получение клиента по id: {}", id);

        ClientEntity entity = clientDaoService.findById(id)
                .orElse(null);

        ClientResponseById response = clientMapper.toResponseById(entity);

        log.info("Клиент найден: id={}, name={}", response.getId(), response.getFullName());
        return response;
    }

    public ClientsResponse getClients() {
        log.info("Получение всех клиентов");

        List<ClientEntity> entities = clientDaoService.findAll();
        List<ClientResponse> content = clientMapper.toResponseList(entities);

        ClientsResponse response = new ClientsResponse();
        response.setContent(content);

        ClientsResponsePageable pageableInfo = new ClientsResponsePageable();
        pageableInfo.setPageNumber(1);
        pageableInfo.setPageSize(20);
        pageableInfo.setTotalPage(1);
        pageableInfo.setTotalElements((long) content.size());
        response.setPageable(pageableInfo);

        log.info("Найдено клиентов: {}", content.size());
        return response;
    }

    @Transactional
    public void deleteClient(UUID id) {
        log.info("Удаление клиента: {}", id);

        ClientEntity entity = clientDaoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент не найден с id: " + id));

        entity.setStatus(ClientStatus.DELETED);
        clientDaoService.save(entity);

        log.info("Клиент удален (soft delete): {}", id);
    }

    @Transactional
    public void updateClient(UUID id, UpdateClient updateClient) {
        log.info("Обновление клиента: {}", id);

        ClientEntity entity = clientDaoService.findById(id)
                .orElse(null);

        clientMapper.updateEntity(entity,updateClient);

        clientDaoService.save(entity);
        log.info("Клиент обновлен: {}", id);
    }

    public ClientExistResponse checkClientExists(UUID id) {
        log.info("Проверка существования клиента: {}", id);

        ClientExistResponse response = new ClientExistResponse();
        ClientEntity entity = clientDaoService.findById(id)
                .orElse(null);
        if (entity != null){
            response.setExists(true);
            response.setClientId(entity.getId());
            response.setStatus(entity.getStatus());
            log.info("Клиент с id: {} существует", id);
        }
        else {
            log.info("Клиент с id: {}  не найден", id);
        }

        return response;

    }

}
