package app.consumer;

import app.dto.OrderDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @KafkaListener(topics = "order-topic")
    public void listen(OrderDto dto) {
        System.out.println(dto);
    }
}
