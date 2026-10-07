package app.service;

import app.mapper.ProductMapper;
import app.model.Product;
import app.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    public void addProductTest() {

        Product mock = mock(Product.class);
        when(mapper.toProduct(any())).thenReturn(mock);

        productService.addProduct(Mockito.any());

        Mockito.verify(productRepository).save(mock);
    }

    @Test
    public void addProduct_arg_capture() {

        Product product = new Product(1L, "test", new BigDecimal("234"), 23);
        when(mapper.toProduct(any())).thenReturn(product);

        productService.addProduct(Mockito.any());

        verify(productRepository).save(productArgumentCaptor.capture());
        Product value = productArgumentCaptor.getValue();

        assertEquals(1L, value.getId());
        assertEquals("test", value.getName());
    }






}
