package app.consumer;

import app.dto.OrderDto;
import app.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @KafkaListener(topics = "order-events")
    public void listen(OrderEvent dto) {
        System.out.println(dto);
    }
}
