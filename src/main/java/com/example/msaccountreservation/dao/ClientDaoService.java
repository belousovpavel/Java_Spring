package com.example.msaccountreservation.dao;

import com.example.msaccountreservation.entity.ClientEntity;
import com.example.msaccountreservation.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientDaoService {

    private final ClientRepository clientRepository;

    public Optional<ClientEntity> findByFullName(String fullName){
        return clientRepository.findByFullName(fullName);
    }

    public Optional<ClientEntity> findById(UUID id){
        return clientRepository.findById(id);
    }
}
