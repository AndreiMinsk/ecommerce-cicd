package app.service;

import app.dto.OrderDto;
import app.dto.ProductDto;
import app.feign.ProductClient;
import app.model.Order;
import app.repository.OrderRepository;
import jdk.jfr.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private ProductClient productClient;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Test
    public void createOrder_happyPath() {

        OrderDto orderDto = new OrderDto(1L, 1);
        ProductDto productDto = new ProductDto(1L, "test", new BigDecimal("250"), 20);
        when(productClient.getProduct(1L)).thenReturn(productDto);


        orderService.createOrder(orderDto);

        verify(orderRepository).save(any(Order.class)   );
        verify(kafkaTemplate).send(any(String.class), any(Order.class));

    }

    @Test
    public void createOrder_valid_order() {

        OrderDto orderDto = new OrderDto(1L, 1);
        ProductDto productDto = new ProductDto(1L, "test", new BigDecimal("250"), 20);
        when(productClient.getProduct(1L)).thenReturn(productDto);

        orderService.createOrder(orderDto);

        verify(orderRepository).save(orderArgumentCaptor.capture());

        Order value = orderArgumentCaptor.getValue();

        assertEquals(1L, value.getProductId() );
        assertEquals(1, value.getQuantity());
        assertEquals("CREATED", value.getStatus().name());
    }


    @Test
    public void createOrder_throwsRuntimeExc() {
        OrderDto orderDto = new OrderDto(1L, 1);
        when(productClient.getProduct(1L)).thenThrow(new RuntimeException("There is no product!!!"));


        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                orderService.createOrder(orderDto));

        assertEquals("There is no product!!!", exception.getMessage());
        verify(orderRepository, never()).save(any(Order.class));
        verify(kafkaTemplate, never()).send(any(String.class), any(Order.class));


    }

    @Test
    public void createOrder_if_product_is_null_should_throw_exception () {
        when(productClient.getProduct(anyLong())).thenReturn(null);
        OrderDto orderDto = new OrderDto(1L, 1);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                orderService.createOrder(orderDto));

        assertEquals("There is no product!!!", exception.getMessage());
        verify(orderRepository, never()).save(any(Order.class));
        verify(kafkaTemplate, never()).send(any(String.class), any(Order.class));
    }
}
