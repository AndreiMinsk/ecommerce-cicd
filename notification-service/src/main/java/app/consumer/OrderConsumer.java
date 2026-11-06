package app.consumer;

import app.dto.OrderDto;
import app.dto.OrderEvent;
import app.service.EmailService;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final EmailService emailService;

    private final Cache<Long, Boolean> cache;

    public OrderConsumer(EmailService emailService, Cache<Long, Boolean> cache) {
        this.emailService = emailService;
        this.cache = cache;
    }


    @KafkaListener(topics = "order-events")
    public void listen(OrderEvent dto) {
        if (cache.getIfPresent(dto.orderId()) != null) {
            return;
        }
        emailService.sendEmail("danishprince@mail.ru", "order", dto.toString());
        cache.put(dto.orderId(), true);
    }
}
