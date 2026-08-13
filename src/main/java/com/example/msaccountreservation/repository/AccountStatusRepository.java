package com.example.msaccountreservation.repository;

import com.example.msaccountreservation.entity.AccountStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatusEntity,Integer> {

    Optional<AccountStatusEntity> findByName(String name);


}
