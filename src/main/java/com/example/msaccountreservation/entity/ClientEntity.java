package com.example.msaccountreservation.entity;

import com.example.msaccountreservation.model.ClientStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status", nullable = false)
    private ClientStatus status = ClientStatus.ACTIVE;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountEntity> accounts = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
