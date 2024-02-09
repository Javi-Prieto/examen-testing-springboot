package com.salesianostriana.dam.testing.examen.datoMetereologico;

import com.salesianostriana.dam.testing.examen.repo.DatoMeteorologicoRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles({"postgres"})
@Sql(value = "classpath:import-datos-met-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DatoMetereologicoRepositoryTest {
    @Autowired
    DatoMeteorologicoRepository repository;
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("testUser")
            .withPassword("testSecret")
            .withDatabaseName("testDatabase");

    public static Stream<Arguments> getData() {
        return Stream.of(
                Arguments.of(LocalDate.now(), "Sevilla"),
                Arguments.of(LocalDate.of(2020, 2, 3), "Sevilla"),
                Arguments.of(LocalDate.now(), ""),
                Arguments.of(null, "")
        );
    }

    @ParameterizedTest
    @MethodSource({"getData"})
    void existePorFechaPoblacionTest(LocalDate fecha, String ciudad){
        if(fecha.isEqual(LocalDate.now()) && ciudad.equals("Sevilla")){
            assertTrue(repository.existePorFechaPoblacion(fecha, ciudad));
        } else{
            assertFalse(repository.existePorFechaPoblacion(fecha, ciudad));
        }
    }
}
