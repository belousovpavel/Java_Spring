package com.example.msaccountreservation.repository;

import com.example.msaccountreservation.entity.AccountEntity;
import com.example.msaccountreservation.entity.AccountStatusEntity;
import com.example.msaccountreservation.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    List<AccountEntity> findByClient(ClientEntity client);

    List<AccountEntity> findByStatus(AccountStatusEntity status);

}
