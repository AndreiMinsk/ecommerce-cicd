package app.consumer;

import app.dto.OrderDto;
import app.dto.OrderEvent;
import app.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final EmailService emailService;

    public OrderConsumer(EmailService emailService) {
        this.emailService = emailService;
    }


    @KafkaListener(topics = "order-events")
    public void listen(OrderEvent dto) {
        emailService.sendEmail("danishprince@mail.ru", "order", dto.toString());
    }
}
