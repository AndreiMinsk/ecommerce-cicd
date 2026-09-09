package app.service;

import app.dto.OrderDto;
import app.dto.ProductDto;
import app.feign.ProductClient;
import app.model.Order;
import app.model.Status;
import app.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void createOrder(OrderDto dto) {
        checkAvailability(dto);
        Order order = Order.builder()
                .productId(dto.productId())
                .quantity(dto.quantity())
                .status(Status.CREATED)
                .build();
        orderRepository.save(order);
        kafkaTemplate.send("order-events", order);


    }

    private void checkAvailability(OrderDto dto) {
        ProductDto product = productClient.getProduct(dto.productId());
        System.out.println(product.id() + " " + product.name());
        if (product == null) {
            throw new RuntimeException("There is no product!!!");
        } else {
            if (dto.quantity() > product.stock()) {
                throw new RuntimeException("There is not enough product in stock");
            }
        }
    }
}
