package com.example.msaccountreservation.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "account_status")
@Getter
@Setter
@NoArgsConstructor
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
