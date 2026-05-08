package com.app.quantitymeasurement.controller;

import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantity_measurement_app.controller.QuantityMeasurementController;
import com.app.quantity_measurement_app.model.QuantityDTO;
import com.app.quantity_measurement_app.model.QuantityInputDTO;
import com.app.quantity_measurement_app.model.QuantityMeasurementDTO;
import com.app.quantity_measurement_app.service.IQuantityMeasurementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = com.app.quantity_measurement_app.QuantityMeasurementApplication.class)
public class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuantityMeasurementService service;

    @Autowired
    private ObjectMapper objectMapper;

    private QuantityInputDTO quantity1;
    private QuantityMeasurementDTO measurementResult;

    @BeforeEach
    public void setUp() {
        quantity1 = new QuantityInputDTO();
        quantity1.setThisQuantityDTO(new QuantityDTO(1.0, "FEET", "LengthUnit"));
        quantity1.setThatQuantityDTO(new QuantityDTO(12.0, "INCHES", "LengthUnit"));

        measurementResult = new QuantityMeasurementDTO();
        measurementResult.setThisValue(1.0);
        measurementResult.setThisUnit("FEET");
        measurementResult.setThisMeasurementType("LengthUnit");

        measurementResult.setThatValue(12.0);
        measurementResult.setThatUnit("INCHES");
        measurementResult.setThatMeasurementType("LengthUnit");
    }

    @Test
    public void testCompareQuantities_Success() throws Exception {
        measurementResult.setOperation("COMPARE");
        measurementResult.setResultString("Equal");
        measurementResult.setError(false);

        Mockito.when(service.compare(
                Mockito.any(QuantityDTO.class),
                Mockito.any(QuantityDTO.class)
        )).thenReturn(measurementResult);

        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quantity1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("Equal"));
    }

    @Test
    public void testAddQuantities_Success() throws Exception {
        measurementResult.setOperation("ADD");
        measurementResult.setResultValue(2.0);
        measurementResult.setResultUnit("FEET");
        measurementResult.setResultMeasurementType("LengthUnit");
        measurementResult.setError(false);

        Mockito.when(service.add(
                Mockito.any(QuantityDTO.class),
                Mockito.any(QuantityDTO.class)
        )).thenReturn(measurementResult);

        mockMvc.perform(post("/api/v1/quantities/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quantity1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.0));
    }

    @Test
    public void testGetOperationHistory_Success() throws Exception {
        Mockito.when(service.getOperationHistory("COMPARE"))
                .thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/v1/quantities/history/operation/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetOperationCount_Success() throws Exception {
        Mockito.when(service.getOperationCount("COMPARE"))
                .thenReturn(0L);

        mockMvc.perform(get("/api/v1/quantities/count/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }
}