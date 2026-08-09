package com.example.msaccountreservation.dao;

import com.example.msaccountreservation.entity.AccountStatusEntity;
import com.example.msaccountreservation.repository.AccountRepository;
import com.example.msaccountreservation.repository.AccountStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountStatusDao {

    private final AccountStatusRepository accountStatusRepository;

    public AccountStatusDao(AccountStatusRepository accountStatusRepository) {
        this.accountStatusRepository = accountStatusRepository;
    }

    public Optional<AccountStatusEntity> findByName(String name){
        return accountStatusRepository.findByName(name);
    }

}
