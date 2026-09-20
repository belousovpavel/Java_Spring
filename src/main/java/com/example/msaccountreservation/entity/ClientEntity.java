package com.example.msaccountreservation.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "client")
public class ClientEntity {
    @Id
    @Column(name = "id", columnDefinition = "UUID",nullable = false)
    private UUID id;

    @Column(name = "full_name", nullable = false,length = 128)
    private String fullName;

    @Column(name = "citizenship",length = 128)
    private String citizenship;

    @Column(name = "client_type",length = 128)
    private String clientType;

    @Column(name = "document_number",length = 50)
    private String documentNumber;

    @Column(name = "document_series",length = 50)
    private String documentSeries;

    @Column(name = "document_type",length = 50)
    private String documentType;

    @Column(name = "mdm_code")
    private Long mdmCode;


    public ClientEntity() {
    }

    public ClientEntity(String fullName, String citizenship, String clientType, String documentNumber, String documentSeries, String documentType, Long mdmCode) {
        this.fullName = fullName;
        this.citizenship = citizenship;
        this.clientType = clientType;
        this.documentNumber = documentNumber;
        this.documentSeries = documentSeries;
        this.documentType = documentType;
        this.mdmCode = mdmCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentSeries() {
        return documentSeries;
    }

    public void setDocumentSeries(String documentSeries) {
        this.documentSeries = documentSeries;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Long getMdmCode() {
        return mdmCode;
    }

    public void setMdmCode(Long mdmCode) {
        this.mdmCode = mdmCode;
    }
}
