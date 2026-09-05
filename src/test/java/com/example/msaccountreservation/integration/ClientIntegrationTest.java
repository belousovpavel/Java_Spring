package com.example.msaccountreservation.integration;

import com.example.msaccountreservation.model.*;
import com.example.msaccountreservation.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ClientIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Autowired
    private ClientRepository clientRepository;

    private ClientRequest testRequest;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();

        testRequest = new ClientRequest();
        testRequest.setFullName("Иван Иванов");
        testRequest.setCitizenship("RU");
        testRequest.setClientType("INDIVIDUAL");
        testRequest.setDocumentNumber("1234567890");
        testRequest.setDocumentSeries("1234");
        testRequest.setDocumentType("PASSPORT");
    }

    @Test
    @DisplayName("Создание клиента - успешно")
    void createClient_Success() throws Exception {
        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Иван Иванов"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("Создание клиента с дублирующими документами - ошибка 409")
    void createClient_DuplicateDocument_Conflict() throws Exception {
        // Создаем первого клиента
        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk());

        // Пытаемся создать второго с теми же документами
        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value("CLIENT_ALREADY_EXISTS"));
    }

    @Test
    @DisplayName("Получение несуществующего клиента - ошибка 404")
    void getClient_NotFound_Returns404() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/clients/{clientId}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("CLIENT_NOT_FOUND"));
    }
}
