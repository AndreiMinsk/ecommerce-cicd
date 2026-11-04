package app.dto;

public record OrderEvent(Long orderId, Long productId, Integer quantity, String status) {
}
