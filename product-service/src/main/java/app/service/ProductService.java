package app.service;

import app.dto.ProductDto;
import app.mapper.ProductMapper;
import app.model.Product;
import app.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public void addProduct(ProductDto dto) {
        Product product = mapper.toProduct(dto);
        productRepository.save(product);
    }

    public List<ProductDto> getAll() {
        return mapper.toDtos(productRepository.findAll());
    }

    public ProductDto getProduct(Long id) {
        return mapper.toDto(productRepository.findById(id).orElseThrow());
    }
}
