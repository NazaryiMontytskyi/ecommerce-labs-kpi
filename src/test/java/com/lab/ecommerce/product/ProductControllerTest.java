package com.lab.ecommerce.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllProducts_returns200() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getProductById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/products/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProduct_returns200() throws Exception {
        String body = """
                {
                    "name": "Test Product",
                    "price": 9.99,
                    "stock": 5
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void healthEndpoint_returns200() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk());
    }
}
