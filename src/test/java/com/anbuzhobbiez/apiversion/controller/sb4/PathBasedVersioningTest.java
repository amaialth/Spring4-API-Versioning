package com.anbuzhobbiez.apiversion.controller.sb4;

import com.anbuzhobbiez.apiversion.model.Product;
import com.anbuzhobbiez.apiversion.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PathBasedVersioningTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        // Mock data
        Product product = new Product(1L, "Test Product", "Description", 100.0);
        when(productService.getAllProducts()).thenReturn(List.of(product));
    }

    @Test
    @Order(1)
    void getV1() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").doesNotExist()); // V1 should NOT have price
    }

    @Test
    @Order(2)
    void getV2() throws Exception {
        mockMvc.perform(get("/api/v2/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").value(100.0)); // V2 SHOULD have price
    }
}
