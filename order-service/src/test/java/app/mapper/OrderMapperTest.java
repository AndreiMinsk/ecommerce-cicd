package app.mapper;

import app.dto.OrderEvent;
import app.model.Order;
import app.model.Status;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.*;

public class OrderMapperTest {
    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @Test
    void mapAllField() {
        Order order = Order.builder()
                .id(10L)
                .productId(20L)
                .quantity(3)
                .status(Status.CREATED)
                .build();
        OrderEvent event = mapper.toEvent(order);

        assertThat(event.orderId()).isEqualTo(10L);
        assertThat(event.productId()).isEqualTo(20L);
        assertThat(event.quantity()).isEqualTo(3);
        assertThat(event.status()).isEqualTo("CREATED");

    }

    @Test
    void mapNullFields() {
        Order order = Order.builder().build();
        OrderEvent event = mapper.toEvent(order);

        assertThat(event.orderId()).isNull();
        assertThat(event.productId()).isNull();
        assertThat(event.quantity()).isNull();
        assertThat(event.status()).isNull();
    }
}
