package app.controller;

import app.dto.OrderDto;
import app.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {

    private final OrderService orderService;

    public Controller(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("orders/")
    public String createOrder(@Valid @RequestBody OrderDto dto) {
        orderService.createOrder(dto);
        return "Your order was created!!!";
    }
}
