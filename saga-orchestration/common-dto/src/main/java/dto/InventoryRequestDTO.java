package dto;

import java.util.UUID;

import lombok.Data;

@Data
public class InventoryRequestDTO {

    private Integer userId;
    private Integer productId;
    private UUID orderId;

}