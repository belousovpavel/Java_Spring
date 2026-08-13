package com.example.msaccountreservation.dao;

import com.example.msaccountreservation.entity.AccountEntity;
import com.example.msaccountreservation.entity.AccountStatusEntity;
import com.example.msaccountreservation.entity.ClientEntity;
import com.example.msaccountreservation.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountDao {

    private final AccountRepository accountRepository;

    public List<AccountEntity> findByClient(ClientEntity client){
        return accountRepository.findByClient(client);
    }

    public List<AccountEntity> findByStatus(AccountStatusEntity status){
        return accountRepository.findByStatus(status);
    }

}
