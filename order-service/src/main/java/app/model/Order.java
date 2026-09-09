package app.model;
//- Хранит: `id`, `productId`, `quantity`, `status`.

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private Long productId;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Status status;



}
