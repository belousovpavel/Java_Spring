package com.example.msaccountreservation.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "status_id",nullable = false)
    private AccountStatusEntity status;

    @ManyToOne()
    @JoinColumn(name = "client_id",nullable = false)
    private ClientEntity client;

    @Column(name = "account_type",length = 50)
    private String accountType;

    @Column(name = "currency_code",length = 50)
    private String currencyCode;

    public AccountEntity() {
    }

    public AccountEntity(AccountStatusEntity status, ClientEntity client, String accountType, String currencyCode) {
        this.status = status;
        this.client = client;
        this.accountType = accountType;
        this.currencyCode = currencyCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AccountStatusEntity getStatus() {
        return status;
    }

    public void setStatus(AccountStatusEntity status) {
        this.status = status;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
