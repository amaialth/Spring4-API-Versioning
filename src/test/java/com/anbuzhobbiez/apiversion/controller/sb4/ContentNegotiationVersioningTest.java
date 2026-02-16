package com.anbuzhobbiez.apiversion.controller.sb4;

import com.anbuzhobbiez.apiversion.dto.ProductV1;
import com.anbuzhobbiez.apiversion.dto.ProductV2;
import com.anbuzhobbiez.apiversion.model.Product;
import com.anbuzhobbiez.apiversion.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContentNegotiationVersioningTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        Product product = new Product(1L, "Test Product", "Description", 100.0);
        when(productService.getAllProducts()).thenReturn(List.of(product));
    }

    @Test
    @Order(1)
    void getV1() throws Exception {
        mockMvc.perform(get("/api/products").accept("application/vnd.company.app-v1+json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.company.app-v1+json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").doesNotExist());
    }

    @Test
    @Order(2)
    void getV2() throws Exception {
        mockMvc.perform(get("/api/products").accept("application/vnd.company.app-v2+json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.company.app-v2+json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").value(100.0));
    }
}
