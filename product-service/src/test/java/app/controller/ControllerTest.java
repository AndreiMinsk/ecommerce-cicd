package app.controller;

import app.dto.ProductDto;
import app.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void shouldAddProduct() throws Exception {
        String json = """
                {
                    "id": 1,
                    "name": "test",
                    "price": 123,
                    "stock": 345
                }
                """;

        doNothing().when(productService).addProduct(any());

        mockMvc
                .perform(post("/products").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Your product is added!!!"));
    }

    @Test
    public void getAll() throws Exception {

        List<ProductDto> productDtos = List.of(new ProductDto(1L, "zuzu", new BigDecimal("234"), 234), new ProductDto(2L, "ffff", new BigDecimal("12343214"), 324324));
        when(productService.getAll()).thenReturn(productDtos);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("zuzu"))
                .andExpect(jsonPath("$[1].name").value("ffff"));


    }

    @Test
    public void getProduct() throws Exception {

        ProductDto dto  = new ProductDto(1L, "zuzu", new BigDecimal("234") , 134);
        when(productService.getProduct(1L)).thenReturn(dto);

        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("zuzu"));
    }


}
