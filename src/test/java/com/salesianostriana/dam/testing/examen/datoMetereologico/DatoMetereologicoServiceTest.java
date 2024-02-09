package com.salesianostriana.dam.testing.examen.datoMetereologico;

import com.salesianostriana.dam.testing.examen.exception.ResourceNotFoundException;
import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;
import com.salesianostriana.dam.testing.examen.model.DatoMeterologicoPK;
import com.salesianostriana.dam.testing.examen.repo.DatoMeteorologicoRepository;
import com.salesianostriana.dam.testing.examen.service.ServicioMeteorologico;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DatoMetereologicoServiceTest {
    @Mock
    DatoMeteorologicoRepository repository;

    @InjectMocks
    ServicioMeteorologico service;


    @ParameterizedTest
    @CsvSource({"Sevilla", "Madrid", "Cadiz", "Valencia"})
    void mediaMensualTest(String poblacion){
        if (poblacion.equals("Sevilla")){
            List<DatoMeteorologico> toReturn = List.of(DatoMeteorologico.builder()
                    .id(new DatoMeterologicoPK("Sevilla", LocalDate.now()))
                    .precipitacion(20)
                    .build(), DatoMeteorologico.builder()
                    .id(new DatoMeterologicoPK("Sevilla", LocalDate.of(2024, 2, 8)))
                    .precipitacion(10)
                    .build(), DatoMeteorologico.builder()
                    .id(new DatoMeterologicoPK("Sevilla", LocalDate.of(2024, 1, 8)))
                    .precipitacion(10)
                    .build());
            when(repository.buscarPorPoblacion(any(String.class))).thenReturn(toReturn);
            assertEquals(2, service.mediaMensual(poblacion).size());
            assertEquals(10, service.mediaMensual(poblacion).get("ENERO"));
            assertEquals(15, service.mediaMensual(poblacion).get("FEBRERO"));
        }else if(poblacion.equals("Madrid")){
            when(repository.buscarPorPoblacion(any(String.class))).thenReturn(List.of());
            Exception exception = new ResourceNotFoundException();
            //assertThrows(exception, service.mediaMensual(poblacion));
        }else if (poblacion.equals("Cadiz")){
            List<DatoMeteorologico> toReturn = List.of(DatoMeteorologico.builder()
                    .id(new DatoMeterologicoPK("Cadiz", LocalDate.now()))
                    .precipitacion(20)
                    .build(), DatoMeteorologico.builder()
                    .id(new DatoMeterologicoPK("Cadiz", LocalDate.of(2020, 2, 8)))
                    .precipitacion(10)
                    .build(), DatoMeteorologico.builder()
                    .id(new DatoMeterologicoPK("Cadiz", LocalDate.of(2024, 1, 8)))
                    .precipitacion(10)
                    .build());
            when(repository.buscarPorPoblacion(any(String.class))).thenReturn(toReturn);
            assertEquals(2, service.mediaMensual(poblacion).size());
            assertEquals(10, service.mediaMensual(poblacion).get("ENERO"));
            //Según entiendo yo el programa esto debería cumplirse ya que son de años distintos no se si a lo que se refiere es a la media mensual histórica
            //pero a mi entender el método no realiza esto correctamente ya que no hay distinción entre años
            assertEquals(10, service.mediaMensual(poblacion).get("FEBRERO"));
            assertEquals(20, service.mediaMensual(poblacion).get("FEBRERO"));
        } else if (poblacion.equals("Valencia")) {
            List<DatoMeteorologico> toReturn = List.of(DatoMeteorologico.builder()
                    .id(new DatoMeterologicoPK("Valencia", LocalDate.now()))
                    .precipitacion(-100)
                    .build(), DatoMeteorologico.builder()
                    .id(new DatoMeterologicoPK("Valencia", LocalDate.of(2024, 2, 8)))
                    .precipitacion(12)
                    .build());
            when(repository.buscarPorPoblacion(any(String.class))).thenReturn(toReturn);
            assertEquals(1, service.mediaMensual(poblacion).size());
            assertEquals(-44, service.mediaMensual(poblacion).get("FEBRERO"));
        }else{
            when(repository.buscarPorPoblacion(any(String.class))).thenReturn(List.of());
            Exception exception = new ResourceNotFoundException();
            //assertThrows(exception, service.mediaMensual(poblacion));
        }
    }
}
