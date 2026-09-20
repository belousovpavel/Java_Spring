package com.example.msaccountreservation.dao;

import com.example.msaccountreservation.entity.ClientEntity;
import com.example.msaccountreservation.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientDaoService {

    private final ClientRepository clientRepository;

    public Optional<ClientEntity> findByFullName(String fullName) {
        log.debug("Поиск клиента по fullName: {}", fullName);
        return clientRepository.findByFullName(fullName);
    }

    public Optional<ClientEntity> findById(UUID id) {
        log.debug("Поиск клиента по Id: {}", id);
        return clientRepository.findById(id);
    }

    public List<ClientEntity> findAll() {
        log.debug("Поиск всех клиентов");
        return clientRepository.findAll();
    }

    @Transactional
    public ClientEntity save(ClientEntity entity) {
        log.debug("Сохранение клиента: {}", entity);
        return clientRepository.save(entity);
    }

    public Optional<ClientEntity> findByDocumentNumberAndDocumentSeries(String documentNumber, String documentSeries) {
        log.debug("Поиск клиента по номеру и серии");
        return clientRepository.findByDocumentNumberAndDocumentSeries(documentNumber, documentSeries);
    }
}
