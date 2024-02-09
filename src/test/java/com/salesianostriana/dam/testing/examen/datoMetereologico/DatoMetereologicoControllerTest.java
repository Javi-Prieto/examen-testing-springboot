package com.salesianostriana.dam.testing.examen.datoMetereologico;

import com.salesianostriana.dam.testing.examen.controller.ControladorMeteorologico;
import com.salesianostriana.dam.testing.examen.service.ServicioMeteorologico;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class DatoMetereologicoControllerTest {
    @Mock
    ServicioMeteorologico service;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    void when_mediaMensualPorPoblacion_then200() throws Exception {
        when(service.mediaMensual(any(String.class))).thenReturn(Map.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/meteo/media/mes/{ciudad}", "Sevilla")
                .accept("application/json")
        ).andExpect(status().isOk());
    }
}
