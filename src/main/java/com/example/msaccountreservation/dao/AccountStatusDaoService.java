package com.example.msaccountreservation.dao;

import com.example.msaccountreservation.entity.AccountStatusEntity;
import com.example.msaccountreservation.repository.AccountStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountStatusDaoService {

    private final AccountStatusRepository accountStatusRepository;

    public Optional<AccountStatusEntity> findByName(String name){
        return accountStatusRepository.findByName(name);
    }

}
