package app.consumer;

import app.dto.OrderDto;
import app.dto.OrderEvent;
import app.service.EmailService;
import com.github.benmanes.caffeine.cache.Cache;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final EmailService emailService;

    private final Cache<Long, Boolean> cache;

    private final MeterRegistry registry;

    private Counter created, failed;

    public OrderConsumer(EmailService emailService, Cache<Long, Boolean> cache, MeterRegistry registry) {
        this.emailService = emailService;
        this.cache = cache;
        this.registry = registry;
        created = Counter.builder("notifications.sent").register(registry);
        failed = Counter.builder("notifications.fail").register(registry);
    }


    @KafkaListener(topics = "order-events")
    public void listen(OrderEvent dto) {
        if (cache.getIfPresent(dto.orderId()) != null) {
            return;
        }
        emailService.sendEmail("danishprince@mail.ru", "order", dto.toString());
        cache.put(dto.orderId(), true);
        created.increment();
    }
}
