package com.example.msaccountreservation.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "account_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false,length = 128)
    private String name;

    @Column(name = "description",length = 128)
    private String description;

    public AccountStatusEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
