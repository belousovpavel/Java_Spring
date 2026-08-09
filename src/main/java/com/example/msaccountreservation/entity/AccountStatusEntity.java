package com.example.msaccountreservation.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "account_status")
public class AccountStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false,length = 128)
    private String name;

    @Column(name = "description",length = 128)
    private String description;

    public AccountStatusEntity() {
    }

    public AccountStatusEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
