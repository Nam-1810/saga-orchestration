package dto;

import java.util.UUID;

import enums.OrderStatus;
import lombok.Data;

@Data
public class OrderResponseDTO {

    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private Double amount;
    private OrderStatus status;

}
