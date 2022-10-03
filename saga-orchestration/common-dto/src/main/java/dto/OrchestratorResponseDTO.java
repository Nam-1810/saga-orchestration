package dto;

import java.util.UUID;

import enums.OrderStatus;
import lombok.Data;

@Data
public class OrchestratorResponseDTO {

    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Double amount;
    private OrderStatus status;

}
