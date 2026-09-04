package app.controller;

import app.dto.ProductDto;
import app.model.Product;
import app.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class Controller {

    private ProductService service;

    @PostMapping("products")
    public String addProduct(@Valid @RequestBody ProductDto dto) {
        service.addProduct(dto);
        return "Your product is added!!!";
    }

    @GetMapping("products")
    public List<ProductDto> getAll() {
        return service.getAll();
    }

    @GetMapping("products/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return service.getProduct(id);
    }
}
