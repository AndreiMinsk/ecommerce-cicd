package app.mapper;

import app.dto.OrderEvent;
import app.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    OrderEvent toEvent(Order order);
}
