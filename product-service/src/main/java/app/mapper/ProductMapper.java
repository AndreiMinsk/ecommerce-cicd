package app.mapper;

import app.dto.ProductDto;
import app.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductDto dto);

    ProductDto toDto(Product product);

    List<ProductDto> toDtos(List<Product> products);
}
