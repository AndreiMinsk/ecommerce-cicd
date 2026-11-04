package app.service;

import app.dto.OrderDto;
import app.dto.ProductDto;
import app.feign.ProductClient;
import app.mapper.OrderMapper;
import app.model.Order;
import app.model.Status;
import app.repository.OrderRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.mapstruct.Mapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final MeterRegistry  meterRegistry;

    private final Counter ordersCreated;
    private final Counter ordersFailed;
    private final OrderMapper orderMapper;

    public OrderService(ProductClient productClient, OrderRepository orderRepository, KafkaTemplate<String, Object> kafkaTemplate, MeterRegistry meterRegistry, OrderMapper orderMapper) {

        this.productClient = productClient;
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.meterRegistry = meterRegistry;

        this.ordersCreated = Counter.builder("business.orders.created")
                .description("Total orders created")
                .register(meterRegistry);
        this.ordersFailed = Counter.builder("business.orders.failed")
                .description("Total orders failed")
                .register(meterRegistry);

        this.orderMapper = orderMapper;
    }


    public void createOrder(OrderDto dto) {
        try {
            checkAvailability(dto);
            Order order = Order.builder()
                    .productId(dto.productId())
                    .quantity(dto.quantity())
                    .status(Status.CREATED)
                    .build();
            orderRepository.save(order);
            kafkaTemplate.send("order-events", orderMapper.toEvent(order));
            ordersCreated.increment();
        } catch (Exception e) {
            ordersFailed.increment();
            throw e;
        }
    }

    private void checkAvailability(OrderDto dto) {
        ProductDto product = productClient.getProduct(dto.productId());
        if (product != null) {
            if (dto.quantity() > product.stock()) {
                throw new RuntimeException("There is not enough product in stock");
            }
        } else {
            throw new RuntimeException("There is no product!!!");

        }
    }
}
