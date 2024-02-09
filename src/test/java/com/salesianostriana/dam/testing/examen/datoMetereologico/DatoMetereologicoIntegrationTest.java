package com.salesianostriana.dam.testing.examen.datoMetereologico;

import com.salesianostriana.dam.testing.examen.dto.EditDatoMeteorologicoDto;
import com.salesianostriana.dam.testing.examen.dto.GetDatoMeteoDto;
import com.salesianostriana.dam.testing.examen.security.jwt.JwtProvider;
import com.salesianostriana.dam.testing.examen.security.user.model.User;
import com.salesianostriana.dam.testing.examen.security.user.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class DatoMetereologicoIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    JwtProvider jwtProvider;

    EditDatoMeteorologicoDto nuevo ;
    String adminToken;
    String userToken;

    @BeforeEach
    void setUp() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        User user = User.builder().roles(Set.of(UserRole.ADMIN)).avatar("").username("lacabra").fullName("Juan").password("123").build();
        adminToken = jwtProvider.generateToken(user);
        nuevo = new EditDatoMeteorologicoDto("Sevilla", "02-01-2022", 23.5);
    }

    @Test
    void when_add_then201() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity(headers);
        restTemplate.exchange("/meteo/add", HttpMethod.POST, GetDatoMeteoDto.class, request);
    }
}
