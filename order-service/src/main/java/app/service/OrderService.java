package app.service;

import app.dto.OrderDto;
import app.dto.ProductDto;
import app.feign.ProductClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final ProductClient productClient;

    public void createOrder(OrderDto dto) {
        checkAvailability(dto);

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
